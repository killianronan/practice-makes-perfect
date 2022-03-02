import numpy as np # id:14-14-14 
import pandas as pd
import matplotlib.pyplot as plt
df = pd.read_csv("week2.csv")
print(df.head())
X1 = df.iloc[:,0]
X2 = df.iloc[:,1]
X = np.column_stack((X1,X2))
y = df.iloc[:,2]
y[y < 0] = 0
colormap = np.array(['r', 'g'])
plt.xlabel("X_1")
plt.ylabel("X_2")
plt.scatter(X1, X2, c=colormap[y])
plt.legend(["+ marker = green, - marker = red"])
plt.show()


