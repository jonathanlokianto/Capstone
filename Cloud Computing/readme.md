# Dokumentasi Model Stress Detector

This model was created to detect stress levels based on user face detection. This model was implemented using TensorFlow/Keras and deployed to Google Cloud Platform (GCP) using Docker and Cloud Run.

## Tools

- Python: Programming language for running the model.
- Docker: Containerization platform.
- DockerHub: Repository for storing and distributing Docker images.
-  Google Cloud Platform (GCP): Cloud platform for deployment.
## Tools GCP Service
- Cloud Run: Service for deploying and managing containerized applications as APIs.
- Cloud Storage: For storing static files such as images or other large binary objects.
- Cloud Firestore: NoSQL database for dynamic and real-time data storage.
- App Engine: Used to deploy and manage the primary backend application.

## How to Deploy Models
### 1. Prerequisites
Before proceeding, ensure you have the following installed and configured:
- Python 3.8 or higher
- Docker
- GCP SDK (authenticated to your account)
- DockerHub account for pushing and managing Docker images

### 2. Prepare the Environment

  #### 1. Install Dependencies
  ```
pip install -r requirements.txt
```
 #### 2. Test the Application Locally Run the API locally to verify its functionality.
 ```
python app.py
```
By default, the API should be accessible at http://127.0.0.1:5000.
