import numpy as np
from PIL import Image

# Assume padding = 0 and stride = 1
def convolution(nArray, kKernal): 
    k = kKernal.shape[0]
    n = nArray.shape[0]
    print("K = ",k)
    print("N = ",n)
    resultSize = n - k + 1
    output = np.zeros((resultSize, resultSize))

    for i in range(resultSize):
        for j in range (resultSize):
            toConvolute = nArray[i: (i + k), j: (j + k)]
            convoluted = toConvolute * kKernal
            output[i][j] = convoluted.sum()
    return output

# testInput = [10,10,10,0,0,0],[10,10,10,0,0,0],[10,10,10,0,0,0],[10,10,10,0,0,0],[10,10,10,0,0,0],[10,10,10,0,0,0]
# testKernel = [1,0,-1],[1,0,-1],[1,0,-1]
# nArray = np.array(testInput)
# kKernel = np.array(testKernel)
# convolved = convolve2D(nArray, kKernel)
# print(convolved)

# testInput = [0,0,0,10,10,10],[0,0,0,10,10,10],[0,0,0,10,10,10],[0,0,0,10,10,10],[0,0,0,10,10,10],[0,0,0,10,10,10]
# nArray = np.array(testInput)
# kKernel = np.array(testKernel)
# convolved = convolve2D(nArray, kKernel)
# print(convolved)

im = Image.open('test.jpg')
im = im.resize((200,200))
rgb = np.array(im.convert('RGB'))
r = rgb[:,:,0]
# testKernel = [-1,-1,-1],[-1,8,-1],[-1,-1,-1]
# kKernel = np.array(testKernel)
# output = convolution(r, kKernel)
# Image.fromarray(np.uint8(output)).show()

testKernel = [0,-1,0],[-1,8,-1],[0,-1,0]
kKernel = np.array(testKernel)
output = convolution(r, kKernel)
Image.fromarray(np.uint8(r)).show()