import cv2
import numpy as np
from cv2 import absdiff

I=cv2.imread("lab4/lab_files/cm1.png")
I=cv2.resize(I,(80,60))
IG = cv2.cvtColor(I, cv2.COLOR_BGR2GRAY)
cv2.imshow("I", I)
cv2.waitKey(0) 
cv2.destroyAllWindows() 

J=cv2.imread("lab4/lab_files/cm2.png")
J=cv2.resize(J,(80,60))
JG = cv2.cvtColor(J, cv2.COLOR_BGR2GRAY)
cv2.imshow("J", J)
cv2.waitKey(0) 
cv2.destroyAllWindows() 

diff=absdiff(IG,JG)
cv2.imshow("DIFFERENCE", diff)
cv2.waitKey(0) 
cv2.destroyAllWindows() 

window_size=7
W2=3
dx=3
stride=1

vx = np.zeros((60,80))
vy = np.zeros((60,80))

# Creates an image filled with zero
# intensities with the same dimensions 
# as the frame
#hsv = np.zeros_like(I)
hsv = np.zeros((60, 80, 3), dtype=np.uint8)
hsv[:, :, 0] = 255
hsv[:, :, 1] = 255


for x in range(W2, IG.shape[0] - W2 - 1):
    for y in range(W2, IG.shape[1] - W2 - 1):
        IO = np.float32(IG[x-W2:x+W2+1, y-W2:y+W2+1])

        min_dist = None
        flowx = 0
        flowy= 0
        # Go through all the blocks.
       
        tx=0
        ty=0

        for i in range(W2, JG.shape[0] - W2 - 1):
            for j in range(W2, JG.shape[1] - W2 - 1):
                JO = np.float32(JG[i-W2:i+W2+1, j-W2:j+W2+1])
                distance=np.sum(np.sqrt((np.square(JO-IO))))
                if not min_dist or distance < min_dist:
                    min_dist = distance
                    flowx=x - i
                    flowy=y - j
                    # Update the flow field. Note the negative tx and the reversal of
                    # flowx and flowy. This is done to provide proper quiver plots, but
                    # should be reconsidered when using it.
                vx[tx,ty] = flowx
                vy[tx,ty] = flowy 
                ty += 1
            tx += 1
            ty = 0

mag, ang = cv2.cartToPolar(vy, vx)

hsv[..., 0] = ang*180/np.pi/2
hsv[..., 2] = cv2.normalize(mag, None, 0, 255, cv2.NORM_MINMAX)


# Converts HSV to RGB (BGR) color representation
rgb = cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)
     
# Opens a new window and displays the output frame
cv2.imshow("dense optical flow", rgb)
cv2.waitKey(0) 
cv2.destroyAllWindows() 