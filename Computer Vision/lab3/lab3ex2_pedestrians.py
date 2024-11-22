import cv2
import numpy as np

TP=0
TN=0
FP=0
FN=0

a=0.01
prev=cv2.imread("lab2/pedestrian/input/in000001.jpg")
prev = cv2.cvtColor(prev, cv2.COLOR_BGR2GRAY)

for i in range(301,1099):
    k=i+1
    I = cv2.imread("lab2/pedestrian/input/in%06d.jpg" % k)
    I_VIS = I # copy of the input image
    IG = cv2.cvtColor(I_VIS, cv2.COLOR_BGR2GRAY)


    #sigma delta approximation for mean
    # BG=a*IG+(1-a)*prev
    # BG=BG.astype(dtype=np.uint8)

    #sigma delta aprroximation for median
    if (np.all(prev)<np.all(IG)):
        BG=prev+1
    elif (np.all(prev)>np.all(IG)):
        BG=prev-1
    else:
        BG=prev

    BG=BG.astype(dtype=np.uint8)
    DIFF = cv2.absdiff(IG, BG)
    prev=BG
    (T, thresh) = cv2.threshold(DIFF,80,255,cv2.THRESH_BINARY)

    # cv2.imshow("whith mean",thresh)
    # cv2.waitKey(10)

    cv2.imshow("whith median",thresh)
    cv2.waitKey(10)
    
    #masks loading
    GTB=cv2.imread("lab2/pedestrian/groundtruth/gt%06d.png" % i)
    GTBG=cv2.cvtColor(GTB, cv2.COLOR_BGR2GRAY)

    #calculation of true positives 
    TP_M = np.logical_and((thresh == 255),(GTBG == 255)) # logical product of the matrix elements
    TP_S = np.sum(TP_M) # sum of the elements in the matrix
    TP = TP + TP_S # update of the global indicator
    
    #calculation of true negatives 
    TN_M = np.logical_and((thresh == 0),(GTBG == 0)) 
    TN_S = np.sum(TN_M) 
    TN = TN + TN_S 

    #calculation of false positives 
    FP_M = np.logical_and((thresh == 255),(GTBG == 0)) 
    FP_S = np.sum(FP_M) 
    FP = FP + FP_S 

    #calculation of false negatives
    FN_M = np.logical_and((thresh == 0),(GTBG == 255)) 
    FN_S = np.sum(FN_M) 
    FN = FN + FN_S 



P= TP/(TP+FP)
R= TP/(TP+FN)
F1=2*(P*R)/(P+R)

print("precision : ",P)
print("recall : ",R)
print("F1 score : ",F1)