from operator import rshift
import numpy as np # id:14-14-14 
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.svm import LinearSVC
from sklearn.calibration import CalibratedClassifierCV

df = pd.read_csv("week2.csv")
X1 = df.iloc[:,0]
X2 = df.iloc[:,1]
X = np.column_stack((X1,X2))
y = df.iloc[:,2]

svc = LinearSVC(C=0.1)
clf = CalibratedClassifierCV(svc) 
svc.fit(X,y)
clf.fit(X, y)

result = clf.predict_proba(X)
X3 = result[:,0]
X4 = result[:,1]

decisionResult = svc.decision_function(X)
 
coef1, coef2 = svc.coef_.T

intercept = -svc.intercept_/coef2
slope = coef1/coef2

y[y < 0] = 0
colormap = np.array(['r', 'g'])
plt.xlabel("X_1")
plt.ylabel("X_2")
x = np.array([-1, 1])
yaxis = slope*x + intercept
plt.plot(x, yaxis, lw=0.5, ls='--')
plt.fill_between(x, yaxis, 1, color='tab:grey', alpha=0.2)
plt.fill_between(x, yaxis, -1, color='tab:pink', alpha=0.2)
plt.scatter(X1, X2, c=colormap[y])
plt.scatter(X3, X4, c='b')
plt.legend(["+ marker = green, - marker = red, predicted marker = blue"])
plt.show()