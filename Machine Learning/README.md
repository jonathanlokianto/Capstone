# Machine Learning Documentation

## Machine Learning Schedule

| Task     | Week 1                 | Week 2                   | Week 3                               | Week 4                                  |
| -------- | ---------------------- | ------------------------ | ------------------------------------ | --------------------------------------- |
| Task 1   | Finding image dataset  | Pre-processing dataset   | Testing and validating the CNN model | Collecting feedback from deployment     |
| Task 2   | Dataset evaluation     | Building the CNN model   | Model deployment (by Cloud Team)     | Post-deployment evaluation and analysis |
| Task 3   | Data cleaning          | Training and testing CNN | Hyperparameter tuning                | Refining the model based on feedback    |


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


### <Step 2>

## Data Augmentation
- Creating a generator using ImageDataGenerator to augment the data with several parameters
- Loaded all the datasets (training and validation) into the datasets


## Defining a model
- Creating basic model from scratch using 4 convolutional layers designed for feature extractions, with additional components such as batch normalization, pooling, and dropout for better performance.
- Using L2 regularization to reduce overfitting.
- The output that we expected is between 0 and 1, thus using sigmoid activation

## Training the Model
- We train our model with 30 epochs, using reduce_lr callback (which has been initialized earlier)

## Plotting the Result of Training and Validation Accuracy and Loss
- Using Matplotlib, we are able to plot our training and validation accuracy to a simply understanding graphic
- The accuracy result of our model is 84,5% of training accuracy and 85,56% of validation accuracy. While the loss is 0.4030 for the training loss and 0.3999 for the validation loss.

## Saving the Model to H5 Extension 
- We save the model in our specific file path (in our Google Drive), with the .h5 extension.

## Testing the Model
- To see if our model works properly as it's trained, we test our model by uploading real images of human facial expressions
- By leveraging the widgets library, we created an interactive interface that allows users to upload images directly from their devices, and preprocess them by normalizing the pixel value, as well as rescaling them to the required dimensions (48, 48).
- Then, we feed the preprocessed images into a saved model to predict the stress level of the uploaded images

## Saving the Model to Keras Extension 
- As prevention, we also save our model in keras extension

## Convert the Keras Model to JSON 
- First, we created the path where we want our JSON model to be saved into
- Convert the Keras model to JSON and save it to the intended path 
