########## libraries #########
import cv2
import matplotlib
import matplotlib.pyplot as plt
import numpy as np


########## 1.3.1 ##########
# I=cv2.imread("mandrill.jpg")
# cv2.imshow("Mandril",I)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imwrite("mandrill_cv2.png",I)

# print(I.shape) # dimensions /rows, columns, depth/
# print(I.size) # number of bytes
# print(I.dtype) # data type

########## 1.3.2 #########
# import matplotlib.pyplot as plt
# from matplotlib.patches import Rectangle 

# I = plt.imread("mandrill.jpg")

# plt.figure(1) # stworzenie figury
# plt.imshow(I) # dodanie do niej obrazka
# plt.title("Mandril") # dodanie tytulu
# plt.axis("off") # wylaczenie wyswietlania ukladu wspolrzednych

# x = [ 100, 150, 200, 250]
# y = [ 50, 100, 150, 200]
# plt.plot(x,y,"r.",markersize=10)

# fig,ax = plt.subplots(1) # instead of plt.figure(1)
# rect = Rectangle((50,50),50,100,fill=False, ec="r"); # ec - edge colour
# ax.add_patch(rect) # display
# plt.show() # wyswietlnie calosci

# plt.imsave("mandril_plt.png",I)

########## 1.4.1 ##########
# I=cv2.imread("mandrill.jpg")
# IG = cv2.cvtColor(I, cv2.COLOR_BGR2GRAY)
# IHSV = cv2.cvtColor(I, cv2.COLOR_BGR2HSV)

# cv2.imshow("BGR2GRAY",IG)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imwrite("grayscale_mandrill.png",IG)

# cv2.imshow("BGR2HSV",IHSV)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imwrite("ihsv_mandrill.png",IG)

########## 1.4.2 ##########
# I=cv2.imread("mandrill.jpg")
# def rgb2gray(I):
#     return 0.299*I[:,:,0] + 0.587*I[:,:,1] + 0.114*I[:,:,2]

# i=rgb2gray(I)
# plt.imshow(i)
# plt.gray()
# plt.show()

# _HSV = matplotlib.colors.rgb_to_hsv(I)
# plt.imshow(I)
# plt.show()
 
########## 1.5 ##########
# I=cv2.imread("mandrill.jpg")

# height, width =I.shape[:2] # retrieving elements 1 and 2, i.e. the corresponding height and width
# scale = 1.75 # scale factor
# Ix2 = cv2.resize(I,(int(scale*height),int(scale*width)))
# cv2.imshow("Big Mandrill",Ix2)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imwrite("rescaled_mandrill.png",Ix2)

##########1.6##########
# I=cv2.imread("mandrill.jpg")
# IG = cv2.cvtColor(I, cv2.COLOR_BGR2GRAY)

# L=cv2.imread("lena.png")
# cv2.imshow("LENA",L)
# cv2.waitKey(0)
# cv2.destroyAllWindows()

# LG = cv2.cvtColor(L, cv2.COLOR_BGR2GRAY)
# cv2.imshow("BGR2GRAY LENA",LG)
# cv2.waitKey(0)
# cv2.destroyAllWindows()

# LG = cv2.resize(LG,(IG.shape[1],IG.shape[0]))

# #addition
# ADD=np.add(IG,LG)
# cv2.imshow("mandril+lenny",ADD)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imwrite("addition.png",ADD)

# #substraction
# SUB=np.subtract(IG,LG)
# cv2.imshow("mandril+lenny",SUB)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imwrite("substraction.png",SUB)

# #multiplication
# MUL=np.multiply(IG,LG)
# cv2.imshow("mandril+lenny",MUL)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imwrite("multiplication.png",MUL)


# #linear combination
# LIN =cv2.addWeighted(LG,0.4,IG,0.6,0)
# cv2.imshow('linear combination',LIN)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imwrite("linear_combination.png",LIN)

# #absolute difference
# DIFF = cv2.absdiff(IG, LG)
# cv2.imshow('absdiff result',DIFF)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imwrite("absolute_defference.png",DIFF)


#########1.7#########

# I=cv2.imread("mandrill.jpg")
# IG = cv2.cvtColor(I, cv2.COLOR_BGR2GRAY)

# def hist(img):
#     h=np.zeros((256,1), np.float32) # creates and zeros single-column arrays
#     height, width =img.shape[:2] # shape - we take the first 2 values
#     for y in range(height):
#         for j in range(width):
#             h[img[y, j]] +=1
#     return h

# pic=hist(IG)
# plt.hist(pic)
# plt.show()
# plt.imsave("my_histogramm.png",pic)


# hist = cv2.calcHist([IG],[0],None,[256],[0,256])
# plt.hist(hist)
# plt.show()
# plt.imsave("cv2_histogramm.png",pic)

########## 1.8 ##########
# I=cv2.imread("mandrill.jpg")
# IG = cv2.cvtColor(I, cv2.COLOR_BGR2GRAY)

# IGE = cv2.equalizeHist(IG)

# cv2.imshow("histogramm equalization",IGE)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imwrite("histogramm_equalization.png",IGE)

# clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8,8))
# # clipLimit - maximum height of the histogram bar - values above are distributed among neighbours
# # tileGridSize - size of a single image block (local method, operates onseparate image blocks)
# I_CLAHE = clahe.apply(IG)

# cv2.imshow("clache",I_CLAHE)
# cv2.waitKey(0)
# cv2.destroyAllWindows()
# cv2.imwrite("clache.png",I_CLAHE)

########## 1.9 ##########
# I=cv2.imread("lena.png")
# IG = cv2.cvtColor(I, cv2.COLOR_BGR2GRAY)
# #Gausian filter
# GB = cv2.GaussianBlur(I,(5,5),cv2.BORDER_DEFAULT)
 
# cv2.imshow("Gaussian Smoothing",np.hstack((I, GB)))
# cv2.waitKey(0) 
# cv2.destroyAllWindows() 
# cv2.imwrite("gaussian_blur.png",GB)

# #sobel in the x axis
# SBx = cv2.Sobel(IG,cv2.CV_64F,1,0,ksize=5)
# cv2.imshow("sobelx",SBx)
# cv2.waitKey(0) 
# cv2.destroyAllWindows() 
# cv2.imwrite("sobel_x_axis.png",SBx)

# #sobel in the y axis 
# SBy = cv2.Sobel(IG,cv2.CV_64F,0,1,ksize=5)
# cv2.imshow("sobely",SBy)
# cv2.waitKey(0) 
# cv2.destroyAllWindows() 
# cv2.imwrite("sobel_y_axis.png",SBy)

# # Laplacian
# LPC = cv2.Laplacian(IG,cv2.CV_64F)
# cv2.imshow("laplacian",LPC)
# cv2.waitKey(0) 
# cv2.destroyAllWindows() 
# cv2.imwrite("laplacian.png",LPC)

# #median blur
# MDB = cv2.medianBlur(I,15,cv2.BORDER_DEFAULT)
# cv2.imshow("median Blur",MDB)
# cv2.waitKey(0) 
# cv2.destroyAllWindows() 
# cv2.imwrite("median_blur.png",MDB)
