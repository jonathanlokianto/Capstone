# API Documentation: Articles

This API allows users to manage articles, including creating, reading, updating, and deleting articles. It is built using Node.js, Express, and MySQL.

## Tools

- **Node.js**  
- **Firestore** (Requires a Service Account)  
- **Express** (API Framework)  
- **Nodemon**  

## Usage Instructions

1. Run `npm install` to install dependencies.
2. Update the Service Account path in `index.js`.
3. Create a Firestore collection named `articles`.

## Endpoint List

### 1. Get All Articles

- **URL**: `/articles`  
- **Method**: `GET`  
- **Description**: Retrieves all articles from the database.  
- **Response**:  
  - **200 OK**: Returns a list of articles.  
  - **500 Internal Server Error**: If a server error occurs.  

#### Example Response
```json
{
    "status": "success",
    "data": [
        {
            "id": 1,
            "title": "Article Title 1",
            "content": "Content of article 1.",
            "image_url": "http://example.com/image1.jpg",
            "source": "http://example.com/article1.jpg",
            "create_at": "DD-MM-YYYY"
        },
        {
            "id": 2,
            "title": "Article Title 2",
            "content": "Content of article 2.",
            "image_url": "http://example.com/image2.jpg",
            "source": "http://example.com/article2.jpg",
            "create_at": "DD-MM-YYYY"
        },
        {
            "id": 3,
            "title": "Article Title 3",
            "content": "Content of article 3.",
            "image_url": "http://example.com/image3.jpg",
            "source": "http://example.com/article3.jpg",
            "create_at": "DD-MM-YYYY"
        }
    ]
}
```

### 2. Get Article by ID

- **URL**: `/articles/:ID`  
- **Method**: `GET`  
- **Description**: Retrieves an article from the database based on its ID.  
- **Response**:  
  - **200 OK**: Returns the article.  
  - **404 Not Found**: No article found with the specified ID.  

#### Example Response for ID 1
```json
{
    "status": "success",
    "data": [
        {
            "id": 1,
            "title": "Article Title 1",
            "content": "Content of article 1.",
            "image_url": "http://example.com/image1.jpg",
            "source": "http://example.com/article1.jpg",
            "create_at": "DD-MM-YYYY"
        }
    ]
}
