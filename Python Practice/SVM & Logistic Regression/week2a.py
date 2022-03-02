import numpy as np # id:14-14-14 
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import classification_report, confusion_matrix

df = pd.read_csv("week2.csv")
# print(df.head())
X1 = df.iloc[:,0]
X2 = df.iloc[:,1]
X = np.column_stack((X1,X2))
y = df.iloc[:,2]

clf = LogisticRegression(random_state=0).fit(X, y)
clf.predict(X)
# Matrix of actual predictions
print(clf.predict(X))

print("\n", X[0])
print("\n X111", X1)



# Matrix of probabilities of predictions
result = clf.predict_proba(X)
# Column 1 is probability output is 0 (1-p(x))
# Column 2 is probability output is 1 (p(x))
print(clf.predict_proba(X))  

# result.item

X3 = result[:,0]
X4 = result[:,1]
print("\n result1: ", X3)
print("\n result2: ", X4)

print("\n")

# Score for accuracy of model
clf.score(X, y)
print(clf.score(X, y))

print("\n")

# Classification classes
print("Classes: ",clf.classes_)

# Slope
print("Slope: ",clf.coef_)

# Intercept
print("Intercept: ",clf.intercept_)

coef1, coef2 = clf.coef_.T
# Calculate the intercept and gradient of the decision boundary.
intercept = -clf.intercept_/coef2
slope = coef1/coef2

# Confusion matrix
cm = confusion_matrix(y, clf.predict(X))

colormap = np.array(['r', 'g'])
plt.xlabel("X_1")
plt.ylabel("X_2")
y[y < 0] = 0

x = np.array([-1, 1])
yaxis = slope*x + intercept
plt.plot(x, yaxis, lw=0.5, ls='--')
plt.fill_between(x, yaxis, 1, color='tab:grey', alpha=0.2)
plt.fill_between(x, yaxis, -1, color='tab:pink', alpha=0.2)

plt.scatter(X1, X2, c=colormap[y])

plt.scatter(X3, X4, c='b')
plt.legend(["+ marker = green, - marker = red, predicted marker = blue"])
plt.show()

# Confusion matrix plot
# fig, ax = plt.subplots(figsize=(10, 10))
# ax.imshow(cm)
# ax.grid(False)
# ax.xaxis.set(ticks=(0, 1), ticklabels=('Predicted -1s', 'Predicted 1s'))
# ax.yaxis.set(ticks=(0, 1), ticklabels=('Actual -1s', 'Actual 1s'))
# ax.set_ylim(1.5, -0.5)
# for i in range(2):
#     for j in range(2):
#         ax.text(j, i, cm[i, j], ha='center', va='center', color='red')
# plt.show()


# y[y < 0] = 0
# colormap = np.array(['r', 'g'])
# plt.xlabel("X_1")
# plt.ylabel("X_2")
# plt.scatter(X1, X2, c=colormap[y])
# plt.legend(["+ marker = green, - marker = red"])
# plt.show()


