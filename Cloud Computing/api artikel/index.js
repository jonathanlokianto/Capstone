const express = require('express');
const admin = require('firebase-admin');
const app = express();
const PORT = process.env.PORT || 8080;
app.use(express.json());

// Inisialisasi Firebase Admin SDK
const serviceAccount = require('./service.json'); // Ganti dengan path ke file kunci layanan Anda

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

// Rute untuk mendapatkan artikel
app.get('/articles', async (req, res) => {
    try {
        const snapshot = await db.collection('artikel').get();
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

// Endpoint untuk mendapatkan artikel berdasarkan ID
app.get('/articles/:id', async (req, res) => {
    const articleId = req.params.id;

    try {
        const doc = await db.collection('artikel').doc(articleId).get();
        if (!doc.exists) {
            return res.status(404).json({ status: 'error', message: 'Article not found' });
        }

        res.status(200).json({ status: 'success', data: { id: doc.id, ...doc.data() } });
    } catch (err) {
        console.error('Error retrieving article:', err);
        res.status(500).json({ status: 'error', message: 'Internal Server Error' });
    }
});

// Endpoint untuk menambahkan artikel
app.post('/articles', async (req, res) => {
    const { title, content, image_url, source } = req.body;

    // Validasi sederhana
    if (!title || !content || !image_url || !source) {
        return res.status(400).json({ status: 'error', message: 'All fields are required (title, content, image_url, source)' });
    }

    try {
        // Mendapatkan ID terakhir
        const snapshot = await db.collection('artikel').orderBy('id').get();
        const lastId = snapshot.empty ? 0 : parseInt(snapshot.docs[snapshot.docs.length - 1].id);

        const newId = lastId + 1;

        // Format tanggal DD-MM-YYYY
        const currentDate = new Date();
        const formattedDate = `${String(currentDate.getDate()).padStart(2, '0')}-${String(currentDate.getMonth() + 1).padStart(2, '0')}-${currentDate.getFullYear()}`;

        const newArticle = {
            id: newId,
            title,
            content,
            image_url,
            source, // Tambahkan kolom sumber
            created_at: formattedDate
        };

        await db.collection('artikel').doc(newId.toString()).set(newArticle);
        res.status(201).json({
            status: 'success',
            message: 'Article added successfully',
            articleId: newId
        });
    } catch (err) {
        console.error('Error inserting article:', err);
        res.status(500).json({ status: 'error', message: 'Internal Server Error' });
    }
});




// Endpoint untuk mengedit artikel berdasarkan ID
app.put('/articles/:id', async (req, res) => {
    const articleId = req.params.id;
    const { title, content, image_url, source } = req.body;

    // Validasi sederhana
    if (!title || !content || !image_url || !source) {
        return res.status(400).json({ status: 'error', message: 'All fields are required (title, content, image_url, source)' });
    }

    try {
        const docRef = db.collection('artikel').doc(articleId);
        const doc = await docRef.get();

        if (!doc.exists) {
            return res.status(404).json({ status: 'error', message: 'Article not found' });
        }

        await docRef.update({ title, content, image_url, source });
        res.status(200).json({ status: 'success', message: 'Article updated successfully' });
    } catch (err) {
        console.error('Error updating article:', err);
        res.status(500).json({ status: 'error', message: 'Internal Server Error' });
    }
});

// Endpoint untuk menghapus artikel berdasarkan ID
app.delete('/articles/:id', async (req, res) => {
    const articleId = req.params.id;

    try {
        const docRef = db.collection('artikel').doc(articleId);
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