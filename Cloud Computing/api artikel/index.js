const express = require('express');
const admin = require('firebase-admin');
const { Storage } = require('@google-cloud/storage');
const multer = require('multer');

const app = express();
const PORT = process.env.PORT || 8080;

// Middleware JSON
app.use(express.json());

// Inisialisasi Firebase Admin SDK
const serviceAccount = require('./service.json'); // Path ke file kunci layanan Firebase
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
});

const db = admin.firestore();

// Inisialisasi Google Cloud Storage
const storage = new Storage({
    keyFilename: './service.json' // Path ke file kunci layanan GCS
});

const bucketName = 'project-capstone-bucket-as24'; // Ganti dengan nama bucket Anda
const bucket = storage.bucket(bucketName);

// Konfigurasi multer untuk menangani file upload sementara
const upload = multer({
    storage: multer.memoryStorage(), // Simpan file di memory sebelum diunggah ke GCS
    limits: { fileSize: 5 * 1024 * 1024 } // Batas ukuran file 5MB
});

// Rute untuk mendapatkan semua artikel
app.get('/articles', async (req, res) => {
    try {
        const snapshot = await db.collection('articles').get();
        const articles = snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));

        // Mengurutkan artikel berdasarkan ID secara numerik
        articles.sort((a, b) => parseInt(a.id) - parseInt(b.id));

        res.status(200).json({
            status: 'success',
            message: 'Data retrieved successfully',
            data: articles
        });
    } catch (err) {
        console.error('Error fetching articles:', err);
        res.status(500).json({ status: 'error', message: 'Internal Server Error' });
    }
});

// Rute untuk mendapatkan artikel berdasarkan ID
app.get('/articles/:id', async (req, res) => {
    const articleId = req.params.id;

    try {
        const doc = await db.collection('articles').doc(articleId).get();
        if (!doc.exists) {
            return res.status(404).json({ status: 'error', message: 'Article not found' });
        }

        res.status(200).json({ status: 'success', data: { id: doc.id, ...doc.data() } });
    } catch (err) {
        console.error('Error retrieving article:', err);
        res.status(500).json({ status: 'error', message: 'Internal Server Error' });
    }
});

// Rute untuk menambahkan artikel
app.post('/articles', upload.single('image'), async (req, res) => {
    const { title, content, source } = req.body;

    // Validasi sederhana
    if (!title || !content || !req.file || !source) {
        return res.status(400).json({ status: 'error', message: 'All fields are required (title, content, image, source)' });
    }

    try {
        // Upload gambar ke Google Cloud Storage
        const fileName = `fotoartikel/${req.file.originalname}`;
        const file = bucket.file(fileName);

        const stream = file.createWriteStream({
            metadata: { contentType: req.file.mimetype }
        });

        stream.on('error', (err) => {
            console.error('Error uploading file:', err);
            res.status(500).json({ status: 'error', message: 'Failed to upload image' });
        });

        stream.on('finish', async () => {
            await file.makePublic();
            const imageUrl = `https://storage.googleapis.com/${bucketName}/${fileName}`;

            // Ambil ID terakhir
            const snapshot = await db.collection('articles').orderBy('id').get();
            const lastId = snapshot.empty ? 0 : parseInt(snapshot.docs[snapshot.docs.length - 1].id);
            const newId = lastId + 1;

            // Format tanggal DD-MM-YYYY
            const currentDate = new Date();
            const formattedDate = `${String(currentDate.getDate()).padStart(2, '0')}-${String(currentDate.getMonth() + 1).padStart(2, '0')}-${currentDate.getFullYear()}`;

            const newArticle = {
                id: newId,
                title,
                content,
                image_url: imageUrl,
                source,
                created_at: formattedDate
            };

            await db.collection('articles').doc(newId.toString()).set(newArticle);
            res.status(201).json({
                status: 'success',
                message: 'Article added successfully',
                articleId: newId
            });
        });

        stream.end(req.file.buffer);
    } catch (err) {
        console.error('Error inserting article:', err);
        res.status(500).json({ status: 'error', message: 'Internal Server Error' });
    }
});

// Endpoint untuk mengedit artikel berdasarkan ID
app.put('/articles/:id', upload.single('image'), async (req, res) => {
    const articleId = req.params.id;
    const { title, content, source } = req.body;
    const image = req.file; // File image jika ada

    // Validasi sederhana untuk fields lain selain gambar
    if (!title || !content || !source) {
        return res.status(400).json({ status: 'error', message: 'Title, content, and source are required.' });
    }

    try {
        const docRef = db.collection('articles').doc(articleId);
        const doc = await docRef.get();

        if (!doc.exists) {
            return res.status(404).json({ status: 'error', message: 'Article not found' });
        }

        let imageUrl = doc.data().image_url; // Pertahankan URL gambar lama jika gambar baru tidak diunggah

        if (image) {
            // Jika gambar baru diunggah, unggah gambar ke Google Cloud Storage
            const fileName = `fotoartikel/${image.originalname}`;
            const file = bucket.file(fileName);

            const stream = file.createWriteStream({
                metadata: { contentType: image.mimetype }
            });

            stream.on('error', (err) => {
                console.error('Error uploading file:', err);
                return res.status(500).json({ status: 'error', message: 'Failed to upload image' });
            });

            stream.on('finish', async () => {
                await file.makePublic();
                imageUrl = `https://storage.googleapis.com/${bucketName}/${fileName}`; // Update URL gambar

                // Update artikel dengan informasi baru (termasuk gambar baru)
                await docRef.update({
                    title,
                    content,
                    source,
                    image_url: imageUrl // Update gambar jika ada
                });

                res.status(200).json({ status: 'success', message: 'Article updated successfully' });
            });

            stream.end(image.buffer);
        } else {
            // Jika gambar tidak diunggah, update artikel tanpa mengganti gambar
            await docRef.update({
                title,
                content,
                source
            });

            res.status(200).json({ status: 'success', message: 'Article updated successfully' });
        }

    } catch (err) {
        console.error('Error updating article:', err);
        res.status(500).json({ status: 'error', message: 'Internal Server Error' });
    }
});


// Rute untuk menghapus artikel
app.delete('/articles/:id', async (req, res) => {
    const articleId = req.params.id;

    try {
        const docRef = db.collection('articles').doc(articleId);
        const doc = await docRef.get();

        if (!doc.exists) {
            return res.status(404).json({ status: 'error', message: 'Article not found' });
        }

        await docRef.delete();
        res.status(200).json({ status: 'success', message: 'Article deleted successfully' });
    } catch (err) {
        console.error('Error deleting article:', err);
        res.status(500).json({ status: 'error', message: 'Internal Server Error' });
    }
});

// Rute untuk root
app.get('/', (req, res) => {
    res.send('CAPSTONE ARTICLES API!!!');
});

// Mulai server
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
