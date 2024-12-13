# Documentation Model Stress Detector

This model was created to detect stress levels based on user face detection. This model was implemented using TensorFlow/Keras and deployed to Google Cloud Platform (GCP) using Docker and Cloud Run.

## Cloud Architecture
![alt text](https://github.com/jonathanlokianto/Capstone/blob/main/Cloud%20Computing/CArchitecture.png?raw=true)


## Tools
![alt text](https://github.com/jonathanlokianto/Capstone/blob/main/Cloud%20Computing/CImplementation.png?raw=true)


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
### 3. Build and Push the Docker Image
  #### 1. Build the Docker Image
  ```
docker build -t stress-detector .
```
  #### 2. Tag the Image for DockerHub Replace [DOCKER_USERNAME] with your DockerHub username.
  ```
docker tag stress-detector [DOCKER_USERNAME]/stress-detector
```
  #### 3. Push the Image to DockerHub
```
docker push [DOCKER_USERNAME]/stress-detector
```
  #### 4. Push the Image to GCP Container Registry (Optional) If you prefer using GCP Container Registry, tag the image for GCR and push it:
```
docker tag stress-detector gcr.io/[PROJECT-ID]/stress-detector
docker push gcr.io/[PROJECT-ID]/stress-detector
```
### 4. Deploy to GCP Services
1: Deploy with Cloud Run
  #### 1. Deploy the Container to Cloud Run Replace [PROJECT-ID] and [REGION] with your GCP project ID and desired region.
  ```
gcloud run deploy stress-detector \
    --image [DOCKER_USERNAME]/stress-detector \
    --platform managed \
    --region [REGION] \
    --allow-unauthenticated
```
#### 2. Note the URL After deployment, note the URL provided by Cloud Run. This is where your API will be accessible

### 5. Use Cloud Storage for Static Files
#### 1. Upload Files to Cloud Storage
```
gsutil cp <FILE_PATH> gs://[BUCKET_NAME]/
```
#### 2. Access Files Use the public URL or configure permissions for controlled access to your static files.
