import numpy as np # id:12-12-12 
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.preprocessing import PolynomialFeatures
from sklearn import linear_model
from sklearn.model_selection import KFold
from sklearn.metrics import mean_squared_error

df = pd.read_csv("week3.csv")
X1 = df.iloc[:,0]
X2 = df.iloc[:,1]
X = np.column_stack((X1,X2))
y = df.iloc[:,2]


def LReg(polyX, y, X):
    for C in [0.1, 1,10, 100, 1000, 4000, 5000]:
        model = linear_model.Lasso(alpha=(1/(2*C)))
        model.fit(polyX, y)
        print("C = ", C)
        print("Intercept = ", model.intercept_)
        print("Coefficient = ", model.coef_)
        print("\n")
        Xtest1, Xtest2, predictions = generatePredictions(model.intercept_, model.coef_)
        title = "C = " + str(C)
        displayPredictedGraph(Xtest1, Xtest2, predictions, X, y, title)

def RReg(polyX, y, X):
    for C in [0.1, 1, 10, 100, 1000, 4000, 5000]:
        model = linear_model.Ridge(alpha=1/(2*C))
        model.fit(polyX, y)
        print("C =", C)
        print("Intercept = ", model.intercept_)
        print("Coefficient = ", model.coef_)
        print("\n")
        title = "C = " + str(C)
        Xtest1, Xtest2, predictions = generatePredictions(model.intercept_, model.coef_)
        displayPredictedGraph(Xtest1, Xtest2, predictions, X, y, title)

def generatePredictions(intercept_, coef_):
    Xtest1 = []
    Xtest2 = []
    Ytest = []
    grid = np.linspace(-5, 5)
    poly = PolynomialFeatures(5)
    for i in grid:
        YPredictions = []
        i1 = []
        j1 = []
        for j in grid:
            result = poly.fit_transform(np.column_stack((i, j)))
            YPredictions.append(sum(coef_ * result[0, :]) + intercept_)
            i1.append(i)
            j1.append(j)
        Ytest.append(YPredictions)
        Xtest1.append(i1)
        Xtest2.append(j1)
    Xtest1 = np.array(Xtest1)
    Xtest2 = np.array(Xtest2)
    Ytest = np.array(Ytest)
    return Xtest1, Xtest2, Ytest

def displayPredictedGraph(Xtest1, Xtest2, predictedY, X, y, title):
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')
    ax.scatter(X[:, 0], X[:, 1], y, c = 'g')
    ax.plot_surface(predictedY, Xtest1, Xtest2, antialiased=False, alpha=0.2)
    plt.xlabel("Feature 1")
    plt.ylabel("Feature 2")
    ax.set_zlabel("y")
    plt.title(title)
    plt.legend(["Training Data"])
    plt.show()

def crossVal5FoldLReg(X):
    mean_error=[]; std_error=[]
    Ci_range = [0.1, 0.5, 1, 5, 10, 50, 100]
    for c in Ci_range:
        model = linear_model.Lasso(alpha=1/(2*c))
        temp = []
        kf = KFold(n_splits=5)
        for train, test in kf.split(X):
            model.fit(X[train], y[train])
            ypred = model.predict(X[test])
            temp.append(mean_squared_error(y[test],ypred))
        mean_error.append(np.array(temp).mean())
        std_error.append(np.array(temp).std())
    print("Mean Error = ", mean_error)
    print("\n")
    print("Standard Deviation Error = ", std_error)
    print("\n")
    plt.title("5-fold cross-validation, Lasso Mean + Standard Deviation Error Vs C")
    plt.plot(Ci_range, mean_error)
    plt.errorbar(Ci_range, mean_error, yerr=std_error, fmt ='ro', label="Standard Deviation")
    plt.xlabel("Ci") 
    plt.ylabel("Mean square error")
    plt.legend()
    plt.show()

def crossVal5FoldRReg(X):
    mean_error=[]; std_error=[]
    Ci_range = [0.1, 0.5, 1, 5, 10, 50, 100]
    for c in Ci_range:
        model = linear_model.Ridge(alpha=1/(2*c))
        temp = []
        kf = KFold(n_splits=5)
        for train, test in kf.split(X):
            model.fit(X[train], y[train])
            ypred = model.predict(X[test])
            temp.append(mean_squared_error(y[test],ypred))
        mean_error.append(np.array(temp).mean())
        std_error.append(np.array(temp).std())
    print("Mean Error = ", mean_error)
    print("\n")
    print("Standard Deviation Error = ", std_error)
    print("\n")
    plt.title("5-fold cross-validation, Ridge Mean + Standard Deviation Error Vs C")
    plt.plot(Ci_range, mean_error)
    plt.errorbar(Ci_range, mean_error, yerr=std_error, fmt ='ro', label="Standard Deviation")
    plt.xlabel("Ci") 
    plt.xlim((0,50))
    plt.ylabel("Mean square error")
    plt.legend()
    plt.show()

def scatterPlot(features, output):
    fig = plt.figure()
    ax = fig.add_subplot(111 , projection = '3d')
    ax.scatter(features[:,0], features[:,1], output)
    plt.xlabel("Feature 1")
    plt.ylabel("Feature 2")
    plt.legend(["Training data"])
    plt.show()

poly = PolynomialFeatures(degree=5)
result = poly.fit_transform(X)
scatterPlot(X, y)
LReg(result, y, X)
RReg(result, y, X)
crossVal5FoldLReg(X)
crossVal5FoldRReg(X)