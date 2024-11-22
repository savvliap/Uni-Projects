clear
clc

%question D here I am not using plot because there are 4 dimensions
meas=xlsread('iris dataset.xlsx');
data=[ones(150,1), meas];

% setosa
b=[ones(50,1) ;-ones(100,1)];
a_setosa=MSE(data,b)

% versicolor
b=[-ones(50,1);ones(50,1);-ones(50,1)];
a_versicolor=MSE(data,b)

% virginica 
b=[-ones(100,1); ones(50,1)];
a_virginica=MSE(data,b)
