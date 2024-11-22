from io import IncrementalNewlineDecoder
import cv2
import numpy as np
from cv2 import absdiff


def of(I, J, W2=3, dY=3, dX=3):

    I=cv2.resize(I,(80,60))
    IG = cv2.cvtColor(I, cv2.COLOR_BGR2GRAY)
    cv2.imshow("I", I)
    cv2.waitKey(0) 
    cv2.destroyAllWindows() 

    J=cv2.resize(J,(80,60))
    JG = cv2.cvtColor(J, cv2.COLOR_BGR2GRAY)
    cv2.imshow("J", J)
    cv2.waitKey(0) 
    cv2.destroyAllWindows() 

    diff=absdiff(IG,JG)
    cv2.imshow("DIFFERENCE", diff)
    cv2.waitKey(0) 
    cv2.destroyAllWindows()

    vx = np.zeros(((IG.shape[0]),(IG.shape[1])))
    vy = np.zeros(((JG.shape[0]), (JG.shape[1])))

    for x in range(W2, IG.shape[0] - W2 - 1):
        for y in range(W2, IG.shape[1] - W2 - 1):
            IO = np.float32(IG[x-W2:x+W2+1, y-W2:y+W2+1])

            min_dist = None
            flox, flowy = 0, 0
            # Go through all the blocks.
            tx, ty = 0, 0

            for i in range(W2, JG.shape[0] - W2 - 1):
                for j in range(W2, JG.shape[1] - W2 - 1):
                    JO = np.float32(JG[i-W2:i+W2+1, j-W2:j+W2+1])
                    distance=np.sum(np.sqrt((np.square(JO-IO))))
                    if not min_dist or distance < min_dist:
                        min_dist = distance
                        flowx, flowy = x - i, y - j
                        # Update the flow field. Note the negative tx and the reversal of
                        # flowx and flowy. This is done to provide proper quiver plots, but
                        # should be reconsidered when using it.
                    vx[-tx,ty] = flowy
                    vy[-tx,ty] = flowx
                    ty += 1
                tx += 1
                ty = 0

    return vx,vy
    

def vis_flow(u, v, YX, name="dense optical flow"):
    
    # Creates an image filled with zero
    # intensities with the same dimensions 
    # as the frame
    hsv = np.zeros(YX, dtype=np.uint8)
    hsv[:, :, 0] = 255
    hsv[:, :, 2] = 255

    mag, ang = cv2.cartToPolar(u, v)

    # Sets image hue according to the optical flow 
    # direction
    hsv[..., 0] = ang*180/np.pi/2
    hsv[..., 1] = cv2.normalize(mag, None, 0, 255, cv2.NORM_MINMAX)
        
    # Converts HSV to RGB (BGR) color representation
    rgb = cv2.cvtColor(hsv, cv2.COLOR_HSV2BGR)
     
    # Opens a new window and displays the output frame
    cv2.imshow(name, rgb)
    cv2.waitKey(0) 
    cv2.destroyAllWindows() 

def pyramid(im, max_scale):
    images=[im]
    for k in range(1, max_scale):
        images.append(cv2.resize(images[k-1], (0,0), fx=0.5, fy=0.5))
    return images

I=cv2.imread("lab4/lab_files/I.jpg")
J=cv2.imread("lab4/lab_files/J.jpg")

W2=11
dx=11
stride=1
max_scale=3

 
IP=pyramid(I, max_scale)
JP=pyramid(I, max_scale)
#I=IP[-1]
# u,v=of(I, J, W2=3, dY=3, dX=3)
YX=(60,80,3)
# x=vis_flow(u,v,YX,"trying")

for i in range(1,max_scale,-1):
    I_new=IP[i]
    J_new=JP[i]
    u,v=of(I, J, W2=3, dY=3, dX=3)

    I = cv2.resize(I_new,(0,0), fx=2, fy=2, interpolation=cv2.INTER_LINEAR)
    J = cv2.resize(I_new,(0,0), fx=2, fy=2, interpolation=cv2.INTER_LINEAR)

x=vis_flow(u,v,YX,"trying")





