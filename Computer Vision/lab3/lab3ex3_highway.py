import cv2
import numpy as np

TP=0
TN=0
FP=0
FN=0

mog2 = cv2.createBackgroundSubtractorMOG2(history = 600,varThreshold = 50,detectShadows = 0)

for i in range(470,1700):
    k=i+1
    I = cv2.imread("lab3/highway/input/in%06d.jpg" % k)
    IG = cv2.cvtColor(I, cv2.COLOR_BGR2GRAY)

    fgmask = mog2.apply(IG,learningRate = -1)
    
    cv2.imshow("whith gaussian",fgmask)
    cv2.waitKey(10)

    
    
    #masks loading
    GTB=cv2.imread("lab3/highway/groundtruth/gt%06d.png" % i)
    GTBG=cv2.cvtColor(GTB, cv2.COLOR_BGR2GRAY)

    #calculation of true positives 
    TP_M = np.logical_and((fgmask == 255),(GTBG == 255)) # logical product of the matrix elements
    TP_S = np.sum(TP_M) # sum of the elements in the matrix
    TP = TP + TP_S # update of the global indicator
    
    #calculation of true negatives 
    TN_M = np.logical_and((fgmask == 0),(GTBG == 0)) 
    TN_S = np.sum(TN_M) 
    TN = TN + TN_S 

    #calculation of false positives 
    FP_M = np.logical_and((fgmask == 255),(GTBG == 0)) 
    FP_S = np.sum(FP_M) 
    FP = FP + FP_S 

    #calculation of false negatives
    FN_M = np.logical_and((fgmask == 0),(GTBG == 255)) 
    FN_S = np.sum(FN_M) 
    FN = FN + FN_S 



P= TP/(TP+FP)
R= TP/(TP+FN)
F1=2*(P*R)/(P+R)

print("precision : ",P)
print("recall : ",R)
print("F1 score : ",F1)