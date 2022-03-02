import numpy as np
from numpy.core.fromnumeric import size
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
from sklearn.neighbors import KNeighborsClassifier
from sklearn.dummy import DummyClassifier
from sklearn import linear_model
from sklearn.model_selection import KFold
from sklearn.metrics import mean_squared_error
from sklearn.linear_model import LogisticRegression
from sklearn.linear_model import PoissonRegressor
from sklearn.preprocessing import PolynomialFeatures

def createFeatures():
    dublinBikesData = pd.read_csv("dublinbikes_20200101_20200401.csv")
    avondaleData = dublinBikesData.loc[dublinBikesData['NAME'] == 'AVONDALE ROAD']
    pearseData = dublinBikesData.loc[dublinBikesData['NAME'] == 'PEARSE STREET']
    # Avondale Features
    time = avondaleData.iloc[:,1]
    bikes = avondaleData.iloc[:,6]
    # Pearse Features
    # time = pearseData.iloc[:,1]
    # bikes = pearseData.iloc[:,6]
    convertedTimes = pd.to_datetime(time)
    datasetSize = size(convertedTimes)
    # plt.scatter(convertedTimes, bikes, s=0.5)
    # plt.legend()
    # plt.show()

    print(type(convertedTimes))
    minute = []; hour = []; day = []; month = []; hourBeforeBikes = []
    previousBike1 = []; previousBike2 = []; previousBike3 = []; noBikes = []; weekBeforeBikes = []; dayBeforeBikes = []; combinedTime = []
    print(convertedTimes.iloc[0])
    for i in range(0,datasetSize):
        noBikes.append(bikes.iloc[i])
        minute.append(convertedTimes.iloc[i].minute)
        hour.append(convertedTimes.iloc[i].hour)
        day.append(convertedTimes.iloc[i].day)
        combinedTime.append(int(str(convertedTimes.iloc[i].hour)+str(convertedTimes.iloc[i].minute)))
        month.append(convertedTimes.iloc[i].month)
        if(i > 2):
            previousBike1.append(bikes.iloc[i-1])
            previousBike2.append(bikes.iloc[i-2])
            previousBike3.append(bikes.iloc[i-3])
        else:
            previousBike1.append(0)
            previousBike2.append(0)
            previousBike3.append(0)  
        if(i > 2015):
            weekBeforeBikes.append(bikes.iloc[i-2016]) 
        else:
            weekBeforeBikes.append(0) 
        if(i > 287):
            dayBeforeBikes.append(bikes.iloc[i-288]) 
        else:
            dayBeforeBikes.append(0) 
        if(i > 11):
            hourBeforeBikes.append(bikes.iloc[i-12]) 
        else:
            hourBeforeBikes.append(0) 
        
    return np.column_stack((combinedTime, previousBike1, previousBike2, previousBike3, weekBeforeBikes, dayBeforeBikes, hourBeforeBikes)), noBikes

def calculateLassoPerformance(linearOutput, predictions, accuracy_scores):
    hits = 0; misses = 0
    roundedPredictions = [round(element) for element in predictions]
    for i in range(0,len(roundedPredictions)):
        if(roundedPredictions[i]==linearOutput[i]):
            hits+=1
        else:
            misses+=1
    # print(hits/(hits+misses))
    accuracy = hits/(hits+misses)
    accuracy_scores.append(accuracy)

def calculateKNNPerformance(linearOutput, predictions, accuracy_scores):
    hits = 0; misses = 0
    for i in range(0,len(linearOutput)):
        if(predictions[i]==linearOutput[i]):
            hits+=1
        else:
            misses+=1
    # print(hits/(hits+misses))
    accuracy = hits/(hits+misses)
    accuracy_scores.append(accuracy)

def KLassoReg(features, output): 
    Ci_range = [0.001, 0.01, 0.1, 1, 10, 20]
    mean_error=[]; std_error=[]
    arrayOut = np.array(output)
    accuracy_mean = []; accuracy_std_dev = []
    for C in Ci_range:
        model = linear_model.Lasso(alpha=1/(2*C)).fit(features, output)
        temp = []; accuracy_scores = []
        kf = KFold(n_splits=5)
        for train, test in kf.split(features):
            model.fit(features[train], arrayOut[train])
            predictions = model.predict(np.array(features[test]))
            calculateLassoPerformance(arrayOut[test], predictions, accuracy_scores)
            temp.append(mean_squared_error(arrayOut[test],predictions))        
        mean_error.append(np.array(temp).mean())
        std_error.append(np.array(temp).std())
        accuracy_mean.append(np.array(accuracy_scores).mean())
        accuracy_std_dev.append(np.array(accuracy_scores).std())
    graphErrorBar(Ci_range, mean_error, std_error, "Lasso - Mean Squared Error", "Mean Squared", "Ci")
    graphErrorBar(Ci_range, accuracy_mean, accuracy_std_dev, "Lasso - Accuracy Score", "Accuracy", "Ci")

def Kknn(features, output): 
    Ki_range = [1, 2, 3, 4, 5, 6]
    mean_error=[]; std_error=[]
    arrayOut = np.array(output)
    accuracy_mean = []; accuracy_std_dev = []
    for n_neighbours in Ki_range:
        model = KNeighborsClassifier(n_neighbours, weights='uniform')
        temp = []; accuracy_scores = []
        kf = KFold(n_splits=5)
        for train, test in kf.split(features):
            model.fit(features[train], arrayOut[train])
            predictions = model.predict(np.array(features[test]))
            calculateKNNPerformance(arrayOut[test], predictions, accuracy_scores)
            temp.append(mean_squared_error(arrayOut[test],predictions))        
        mean_error.append(np.array(temp).mean())
        std_error.append(np.array(temp).std())
        accuracy_mean.append(np.array(accuracy_scores).mean())
        accuracy_std_dev.append(np.array(accuracy_scores).std())
    graphErrorBar(Ki_range, mean_error, std_error, "KNN - Mean Squared Error", "Mean Squared", "Ki")
    graphErrorBar(Ki_range, accuracy_mean, accuracy_std_dev, "KNN - Accuracy Score", "Accuracy", "Ki")

def getModel(features, output, type):
    if(type=="Lasso"):
        model = linear_model.Lasso(alpha=1/(2*1), max_iter=100).fit(features, output)
    elif(type=="Ridge"):
        model = linear_model.Ridge(alpha=1/(2*40)).fit(features, output)
    elif(type=="Logistic"):
        model = LogisticRegression(C = 1, penalty="l2", max_iter=100).fit(features, output)
    elif(type=="Linear"):
        model = LinearRegression().fit(features, output)
    elif(type=="Poisson"):
        model = PoissonRegressor(max_iter=100, alpha=0.5).fit(features,output)
    elif(type=="KNN"):
        model = KNeighborsClassifier(5, weights='uniform').fit(features, output)
    elif(type=="Baseline"):
        model = DummyClassifier(strategy="uniform").fit(features, output)
    return model

def qStepModel(step_size, features, output):
    rowIndex = 2016; hits = 0; misses = 0; difference = 0
    while rowIndex < 20300:
        x_train = features[rowIndex-2016:rowIndex]
        y_train = output[rowIndex-2016:rowIndex]
        x_test = features[rowIndex+1:rowIndex+step_size+1]
        expected_result = output[rowIndex+1:rowIndex+step_size+1]
        # poly = PolynomialFeatures(3)
        # xTrainPoly = poly.fit_transform(x_train)
        model = getModel(x_train, y_train, "Lasso")
        # xTestPoly = poly.fit_transform(x_test)
        print("Row: ", rowIndex)
        predictions = [0, 0, 0]
        index = 0
        for element in x_test:
            if(len(predictions)>3):
                predictions.pop(0)
            if((index+1)==len(x_test)):
                if(index==1):
                    element[4] = predictions[2]
                else:
                    if(predictions[0]<100000 and predictions[0]>-10):
                        element[1] = predictions[0]
                    if(predictions[1]<100000 and predictions[1]>-10):
                        element[2] = predictions[1]
                    if(predictions[2]<100000 and predictions[2]>-10):
                        element[3] = predictions[2]
                t = model.predict([element])
                targetPrediction = 0
                if(t[0]<100000  and t[0]>-10):
                    targetPrediction = round(t[0])
                if(targetPrediction==expected_result[step_size-1]):
                    hits+=1

                else:
                    diff = abs(targetPrediction-expected_result[step_size-1])
                    if(diff>-10 and diff<1000000):
                        difference+=diff
                    misses+=1
            else: 
                if(index!=0):
                    if(predictions[2]<100000 and predictions[2]>-10):
                        if(index==1):
                            element[3] = predictions[2]
                        elif(index==2):
                            element[2] = predictions[1]
                            element[3] = predictions[2]
                        else:
                            element[1] = predictions[0]
                            element[2] = predictions[1]
                            element[3] = predictions[2]
                        t = model.predict([element])
                        feedbackPrediction = round(t[0])
                        predictions.append(feedbackPrediction)
                else:
                    t = model.predict([element])
                    feedbackPrediction = 0
                    if(t[0]<100000 and t[0]>-10):
                        feedbackPrediction = round(t[0])
                    predictions.append(feedbackPrediction)

            index+=1
        rowIndex += 1
    print("Hits: ", hits)
    print("Misses: ", misses)
    print("Average Hits: ", hits/(hits+misses))
    print("Average Miss Size: ", difference/(hits+misses))

def graphErrorBar(Ci_range, mean_error, std_error, title, label, xLabel):
    plt.title(title)
    plt.plot(Ci_range, mean_error)
    plt.errorbar(Ci_range, mean_error, yerr=std_error, fmt ='ro', label=label)
    plt.xlabel(xLabel) 
    plt.ylabel("Accuracy")
    plt.legend()
    plt.show()

features, output = createFeatures()

# 5 mins every row
# 10 mins = 2 rows
# 30 mins = 6 rows
# 60 mins = 12 rows
qStepModel(2, features, output)
# qStepModel(6, features, output)
# qStepModel(12, features, output)

# K Fold Cross Validation
# KLassoReg(features, output)
# Kknn(features, output)