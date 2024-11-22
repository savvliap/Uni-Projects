import numpy as np
import cv2

TP=0
TN=0
FP=0
FN=0

############3.1##############
YY=240
XX=360
N=60
iN=0
BUF = np.zeros((YY,XX,N),np.uint8)

k=0
#fisrt batch filling the buffer
for r in range(1000,1059):
    first_batch=cv2.imread("lab3/pedestrian/input/in%06d.jpg" % r)
    fbg=cv2.cvtColor(first_batch, cv2.COLOR_BGR2GRAY)
    BUF[:,:,k] = fbg
    k+=1

#now getting the images 
for i in range(300,1099):
    k=i+1
    I = cv2.imread("lab3/pedestrian/input/in%06d.jpg" % i)
    I_VIS = I # copy of the input image

    IG = cv2.cvtColor(I_VIS, cv2.COLOR_BGR2GRAY)
    
    BUF[:,:,iN] = IG
    mean= np.mean(BUF,2,dtype= np.uint8)
    median=np.median(BUF,2).astype(dtype=np.uint8)

    iN+=1
    if (iN==N):
        iN=0

    MEAN_DIFF = cv2.absdiff(IG,mean)
    MEDIAN_DIFF= cv2.absdiff(IG, median)
    prev=IG

    (T, thresh1) = cv2.threshold(MEAN_DIFF,150,255,cv2.THRESH_BINARY)
    (T, thresh2) = cv2.threshold(MEDIAN_DIFF,17,255,cv2.THRESH_BINARY)

    # cv2.imshow("whith mean",thresh1)
    # cv2.waitKey(10)

    cv2.imshow("with median",thresh2)
    cv2.waitKey(10)

    #masks loading
    GTB=cv2.imread("lab3/pedestrian/groundtruth/gt%06d.png" % i)
    GTBG=cv2.cvtColor(GTB, cv2.COLOR_BGR2GRAY)

     
    #calculation of true positives 
    TP_M = np.logical_and((thresh2 == 255),(GTBG == 255)) # logical product of the matrix elements
    TP_S = np.sum(TP_M) # sum of the elements in the matrix
    TP = TP + TP_S # update of the global indicator
    
    #calculation of true negatives 
    TN_M = np.logical_and((thresh2 == 0),(GTBG == 0)) 
    TN_S = np.sum(TN_M) 
    TN = TN + TN_S 

    #calculation of false positives 
    FP_M = np.logical_and((thresh2 == 255),(GTBG == 0)) 
    FP_S = np.sum(FP_M) 
    FP = FP + FP_S 

    #calculation of false negatives
    FN_M = np.logical_and((thresh2 == 0),(GTBG == 255)) 
    FN_S = np.sum(FN_M) 
    FN = FN + FN_S 


P= TP/(TP+FP)
R= TP/(TP+FN)
F1=2*(P*R)/(P+R)

print("precision : ",P)
print("recall : ",R)
print("F1 score : ",F1)