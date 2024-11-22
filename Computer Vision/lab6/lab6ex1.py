import cv2
import numpy as np
import matplotlib.pyplot as plt
cap = cv2.VideoCapture("lab6/vid1_IR.avi")
while(cap.isOpened()):
    ret, frame = cap.read()
    G = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    (T, thresh) =cv2.threshold(G,50,255,cv2.THRESH_BINARY)

    #labels
    retval, labels, stats, centroids = cv2.connectedComponentsWithStats(thresh)

    if (stats.shape[0] > 1): # are there any objects
        tab = stats[1:,4] # 4 columns without first element
        pi = np.argmax( tab )# finding the index of the largest item
        pi = pi + 1 # increment because we want the index in stats, not in tab
        # drawing a bbox
        cv2.rectangle(thresh,(stats[pi,0],stats[pi,1]),(stats[pi,0]+stats[pi,2],stats[pi,1]+stats[pi,3]),(255,0,0),2)
        # print information about the field and the number of the largest element
        cv2.putText(thresh,"%f" % stats[pi,4],(stats[pi,0],stats[pi,1]),cv2.FONT_HERSHEY_SIMPLEX,0.5,(255,0,0))
        cv2.putText(thresh,"%d" %pi,(np.int(centroids[pi,0]),np.int(centroids[pi,1])),cv2.FONT_HERSHEY_SIMPLEX,1,(255,0,0))

    cv2.imshow("IR",G)
    cv2.imshow("thresh",thresh)
    

    if cv2.waitKey(1) & 0xFF == ord("q"): # break the loop when the ’q’ key is pressed
        break
cap.release()

