# Dokumentasi API Artikel

API ini memungkinkan pengguna untuk mengelola artikel, termasuk membuat, membaca, memperbarui, dan menghapus artikel. API ini dibangun menggunakan Node.js, Express, dan MySQL.

## Tools

- Node.js
- Firestore (Membutuhkan Service Account)
- Express (Framework API)
- Nodemon

## Cara Penggunaan

- NPM Install
- Mengganti Path Service Account di index.js
- Membuat Collections Firestore Yang Bernama artikel

## Daftar Endpoint

### 1. Mendapatkan Semua Artikel

- **URL**: `/articles`
- **Metode**: `GET`
- **Deskripsi**: Mengambil semua artikel dari database.
- **Respons**:
  - **200 OK**: Mengembalikan daftar artikel.
  - **500 Internal Server Error**: Jika terjadi kesalahan pada server.

```json
{
    "status": "success",
    "data": [
        {
            "id": 1,
            "title": "Judul Artikel 1",
            "content": "Isi artikel 1.",
            "image_url": "http://example.com/image1.jpg",
            "source" : "http://example.com/article1.jpg",
            "create_at" : "DD-MM-YYYY"
        },
        {
            "id": 2,
            "title": "Judul Artikel 2",
            "content": "Isi artikel 2.",
            "image_url": "http://example.com/image2.jpg",
            "source" : "http://example.com/article2.jpg",
            "create_at" : "DD-MM-YYYY"
        },
        {
            "id": 3,
            "title": "Judul Artikel 3",
            "content": "Isi artikel 3.",
            "image_url": "http://example.com/image3.jpg",
            "source" : "http://example.com/article3.jpg",
            "create_at" : "DD-MM-YYYY"
        }
    ]
}
```

### 2. Mendapatkan Semua Artikel dari ID

- **URL**: `/articles/:ID`
- **Metode**: `GET`
- **Deskripsi**: Mengambil semua artikel dari database berdasarkan id.
- **Respons**:
  - **200 OK**: Mengembalikan daftar artikel.
  - **404 Not Found**: Tidak ada artikel dengan id tersebut.

#### Contoh Respons Parameter ID 1
```json
{
    "status": "success",
    "data": [
        {
            "id": 1,
            "title": "Judul Artikel 1",
            "content": "Isi artikel 1.",
            "image_url": "http://example.com/image1.jpg",
            "source" : "http://example.com/article1.jpg",
            "create_at" : "DD-MM-YYYY"
        }
    ]
}
```