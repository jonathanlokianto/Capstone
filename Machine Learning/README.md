# Machine Learning Documentation
Dektes uses a Convolutional Neural Network (CNN) to analyze image inputs and produce an output value between 0 and 1. This value represents the level of stress detected, where 0 signifies 'no stress' and 1 indicates 'stress.

## Team Members
- **Jonathan Lokianto**  
  *Student ID*: M108B4KY2091  

- **Caroline Graciela Hermanto**  
  *Student ID*: M108B4KX0902  

- **Muhammad Zidan Alif Oktavian**  
  *Student ID*: M284B4KY3135
  

## Machine Learning Schedule

| Task | Week 1                        | Week 2                        | Week 3                                | Week 4                                        |
|------|-------------------------------|-------------------------------|---------------------------------------|---------------------------------------------|
| 1    | Finding image dataset         | Preprocess dataset        | Testing and validating the CNN model  | Deploy the model using Cloud Computing      |
| 2    | Evaluate dataset            | Building the CNN model        | Model deployment (by Cloud Team)      | Collecting feedback from deployment        |
| 3    | Data cleaning                 | Training and testing the CNN  | Hyperparameter tuning                | Post-deployment evaluation and analysis     |
| 4    | -                             | -                             | Convert the model into TensorFlow.js  | Refining the model based on feedback       |



## Source of Dataset
Facial Expression of Fatigues (FEF) : https://www.kaggle.com/datasets/minhngt02/facial-expression-of-fatigues-fer

### Dataset Details
- **Name:** Facial Expression of Fatigues (FEF)  
- **Platform:** Kaggle  
- **Description:** A dataset designed for detecting and analyzing facial expressions related to fatigue.  
- **Applications:** This dataset is ideal for research in machine learning, computer vision, and emotion detection tasks.


## Link to Our Model
Access our model using the link below:  
[Google Colab Notebook](https://colab.research.google.com/drive/1Ok2q0rkfGDZz-zc0-T8-HTHhG6avFKvc?usp=sharing)

### Highlights
- **Platform:** Google Colab  
- **Purpose:** Interactive exploration and usage of our model.  
- **Accessibility:** Fully hosted, minimal setup required (only dependencies).
- **Key Feature:** Easy-to-use interface for running and customizing the model directly in your browser.

### How to Use
1. Click on the link to open the Colab notebook.  
2. In the **Load Dataset** section, replace the code with your own dataset import method and file path. Ensure the dataset is accessible in your environment (e.g., uploaded to Colab, linked to your Google Drive, or fetched from an external source).  
3. Follow the instructions in the notebook to load and test the model.  
4. Customize other sections as needed and run the notebook directly in your browser.  

> **Note:** Please ensure that you upload or link your own dataset when running the Colab notebook, as the current implementation does not include a pre-configured dataset path.

Feel free to reach out if you encounter any issues or need further guidance!


## Library Used
- Tensorflow
- Numpy
- Pandas
- Matplotlib
- Widgets
- OS
- Image
- Pickle
- Drive (from google.colab)


## Model Setup

### Step 1: Setup Python Environment
-  Install the necessary dependencies using the following command:
```python
  pip install reuirements.txt
```
-  Import the required libraries to get started with the project.


### Step 2: Import the Dataset
- Use TensorFlow’s image_dataset_from_directory function to load the training and validation datasets from their respective directories.
- Resize all images to a consistent size of 48x48 pixels for uniform input to the model.
- Load the images in grayscale format (single channel) to focus on intensity rather than color.


### Step 3: Data Augmentation
- Create a generator using ImageDataGenerator to augment the data with various parameters, such as rotation, zoom, and flipping.
- Load both the training and validation datasets into their respective variables.


### Step 4: Defining a model
- Create a basic model from scratch using 4 convolutional layers designed for feature extraction
- Add additional like batch normalization, pooling, and dropout to improve performance and reduce overfitting.
- Apply L2 regularization to prevent the model from overfitting.
- The model's output is expected to be between 0 and 1, so a sigmoid activation function is used for the output layer.


### Step 4: Defining Callbacks
- The ReduceLROnPlateau callback is used to adjust the learning rate during training.
- Set limit to the min_lr parameter properly, ensuring that it doesn't fall below certain treshold, preventing the learning rate from getting too small to be effective.


### Step 5: Training the Model
- Train the model for 30 epochs, using a learning rate scheduler (reduce_lr) to adjust the learning rate dynamically.
- The callback helps the model adapt its learning rate when the progress slows, allowing for more efficient fine-tuning.
- This approach minimizes overfitting and enhances the model's ability to generalize to unseen data, ensuring better performance and stability during training.


### Step 6: Plotting the Result of Training and Validation Accuracy and Loss
- Utilize Matplotlib to plot training and validation accuracy and loss graphs for easy visualization.
- The model achieves 84.5% accuracy on the training set and 85.56% on the validation set. The training loss is 0.4030, while the validation loss is 0.3999.


### Step 7: Saving the Model to H5 Extension 
- Save the trained model with the .h5 extension to a specified path (such as Google Drive) for future use.


### Step 8: Testing the Model
- To verify the model's functionality, test it by uploading real images of human facial expressions.
- Use the ipywidgets library to create an interactive interface, allowing users to upload images directly from their devices.
- Preprocess the uploaded images by normalizing pixel values and resizing them to the required dimensions (48x48).
- Feed the preprocessed images into the trained model to predict the stress level based on the facial expressions.

### Step 9: Saving the Model to Keras Extension 
- As a precaution, save the model in Keras format to ensure compatibility with different environments.

### Step 10: Convert the Keras Model to JSON 
- Create a path where the JSON model will be saved.
- Convert the trained Keras model to JSON format and save it at the specified location for easy model sharing and deployment.
