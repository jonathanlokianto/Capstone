# Machine Learning Documentation

## Machine Learning Schedule

| Task     | Week 1                 | Week 2                   | Week 3                               | Week 4                                  |
| -------- | ---------------------- | ------------------------ | ------------------------------------ | --------------------------------------- |
| 1   | Finding image dataset  | Pre-processing dataset   | Testing and validating the CNN model | Collecting feedback from deployment     |
| 2   | Dataset evaluation     | Building the CNN model   | Model deployment (by Cloud Team)     | Post-deployment evaluation and analysis |
| 3   | Data cleaning          | Training and testing CNN | Hyperparameter tuning                | Refining the model based on feedback    |


## Model Description
Dektes uses Convolutional Neural Network which accepts Image input and returns a result between 0 to 1 from no stress detected to stress.



## Source of Dataset
Facial Expression of Fatigues (FEF) : https://www.kaggle.com/datasets/minhngt02/facial-expression-of-fatigues-fer



## Import Library
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



### Step 3: Data Augmentation
- Create a generator using ImageDataGenerator to augment the data with various parameters, such as rotation, zoom, and flipping.
- Load both the training and validation datasets into their respective variables.


### Step 4: Defining a model
- Create a basic model from scratch using 4 convolutional layers designed for feature extraction
- Add additional like batch normalization, pooling, and dropout to improve performance and reduce overfitting.
- Apply L2 regularization to prevent the model from overfitting.
- The model's output is expected to be between 0 and 1, so a sigmoid activation function is used for the output layer.


### Step : Training the Model
- Train the model for 30 epochs, using a learning rate scheduler (reduce_lr) to adjust the learning rate dynamically.


### Plotting the Result of Training and Validation Accuracy and Loss
- Utilize Matplotlib to plot training and validation accuracy and loss graphs for easy visualization.
- The model achieves 84.5% accuracy on the training set and 85.56% on the validation set. The training loss is 0.4030, while the validation loss is 0.3999.


### Saving the Model to H5 Extension 
- Save the trained model with the .h5 extension to a specified path (such as Google Drive) for future use.

### Testing the Model
- To verify the model's functionality, test it by uploading real images of human facial expressions.
- Use the ipywidgets library to create an interactive interface, allowing users to upload images directly from their devices.
- Preprocess the uploaded images by normalizing pixel values and resizing them to the required dimensions (48x48).
- Feed the preprocessed images into the trained model to predict the stress level based on the facial expressions.

### Saving the Model to Keras Extension 
- As a precaution, save the model in Keras format to ensure compatibility with different environments.

### Convert the Keras Model to JSON 
- Create a path where the JSON model will be saved.
- Convert the trained Keras model to JSON format and save it at the specified location for easy model sharing and deployment.
