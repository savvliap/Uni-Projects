import cv2
import numpy as np

##########2.1##########

# for i in range(300,1100):
#     I = cv2.imread("lab2/pedestrian/input/in%06d.jpg" % i)
#     GTB=cv2.imread("lab2/pedestrian/groundtruth/gt%06d.png" % i)

#     cv2.imshow("video",I)
#     cv2.imshow("ground truth",GTB)
#     #nhe displayed image will not be refreshed without the waitkey
#     cv2.waitKey(10)

##########2.2-2.3-2.4-2.5##########
TP=0
TN=0
FP=0
FN=0

prev=cv2.imread("lab2/pedestrian/input/in000001.jpg")
prev = cv2.cvtColor(prev, cv2.COLOR_BGR2GRAY)
#prev = prev.astype("int")

for i in range(301,1099):
    k=i+1
    I = cv2.imread("lab2/pedestrian/input/in%06d.jpg" % k)
    I_VIS = I # copy of the input image
    IG = cv2.cvtColor(I_VIS, cv2.COLOR_BGR2GRAY)


    DIFF = cv2.absdiff(IG, prev)
    prev=IG
    (T, thresh) = cv2.threshold(DIFF,17,255,cv2.THRESH_BINARY)

    #filters 
    MDB = cv2.medianBlur(thresh,7,cv2.BORDER_DEFAULT)

    dkernel = np.ones((5,5), np.uint8)
    dilated = cv2.dilate(MDB,dkernel,iterations = 1)

    MDB = cv2.medianBlur(dilated,21,cv2.BORDER_DEFAULT)

    dkernel = np.ones((5,5), np.uint8)
    dilated = cv2.dilate(MDB,dkernel,iterations = 1)
    
    ekernel = np.ones((7, 7), np.uint8)
    final=cv2.erode(dilated, ekernel) 

    #labels
    retval, labels, stats, centroids = cv2.connectedComponentsWithStats(final)

    if (stats.shape[0] > 1): # are there any objects
        tab = stats[1:,4] # 4 columns without first element
        pi = np.argmax( tab )# finding the index of the largest item
        pi = pi + 1 # increment because we want the index in stats, not in tab
        # drawing a bbox
        cv2.rectangle(I_VIS,(stats[pi,0],stats[pi,1]),(stats[pi,0]+stats[pi,2],stats[pi,1]+stats[pi,3]),(255,0,0),2)
        # print information about the field and the number of the largest element
        cv2.putText(I_VIS,"%f" % stats[pi,4],(stats[pi,0],stats[pi,1]),cv2.FONT_HERSHEY_SIMPLEX,0.5,(255,0,0))
        cv2.putText(I_VIS,"%d" %pi,(np.int(centroids[pi,0]),np.int(centroids[pi,1])),cv2.FONT_HERSHEY_SIMPLEX,1,(255,0,0))


    #cv2.imshow("black and white",final)
    cv2.imshow("Labels", np.uint8(labels / retval * 255))
    cv2.imshow("labeled",I_VIS)
    cv2.waitKey(10)

    #masks loading
    GTB=cv2.imread("lab2/pedestrian/groundtruth/gt%06d.png" % i)
    GTBG=cv2.cvtColor(GTB, cv2.COLOR_BGR2GRAY)

    #calculation of true positives 
    TP_M = np.logical_and((final == 255),(GTBG == 255)) # logical product of the matrix elements
    TP_S = np.sum(TP_M) # sum of the elements in the matrix
    TP = TP + TP_S # update of the global indicator
    
    #calculation of true negatives 
    TN_M = np.logical_and((final == 0),(GTBG == 0)) 
    TN_S = np.sum(TN_M) 
    TN = TN + TN_S 

    #calculation of false positives 
    FP_M = np.logical_and((final == 255),(GTBG == 0)) 
    FP_S = np.sum(FP_M) 
    FP = FP + FP_S 

    #calculation of false negatives
    FN_M = np.logical_and((final == 0),(GTBG == 255)) 
    FN_S = np.sum(FN_M) 
    FN = FN + FN_S 


# print("true positive : ",TP)
# print("true negative : ",TN)
# print("false positive : ",FP)
# print("false negative : ",FN)

P= TP/(TP+FP)
R= TP/(TP+FN)
F1=2*(P*R)/(P+R)

print("precision : ",P)
print("recall : ",R)
print("F1 score : ",F1)