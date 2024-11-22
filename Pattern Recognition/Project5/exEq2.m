clear
clc

meas=xlsread('iris dataset.xlsx');
data=[ones(150,1) meas];

% setosa last 3 characteristics
b=[ones(50,1) ;-ones(100,1)];
w1=MSE([data(:,1) data(:,3) data(:,4) data(:,5)],b);

% versicolor last 3 characteristics
b=[-ones(50,1); ones(50,1);-ones(50,1)];
w2=MSE([data(:,1) data(:,3) data(:,4) data(:,5)],b);

% virginica last 3 characteristics
b=[-ones(100,1); ones(50,1)];
w3=MSE([data(:,1) data(:,3) data(:,4) data(:,5)],b);

%making the plots over here because I loose the titles otherivise
subplot(1,3,1);
plot3(data(1:50,2:4),data(51:100,2:4),data(101:150,2:4),'*')
hold on;boundsurface(w1);title('setosa 2,3,4');shading interp;

subplot(1,3,2);
plot3(data(1:50,2:4),data(51:100,2:4),data(101:150,2:4),'*')
hold on;boundsurface(w2);title('versicolor 2,3,4');shading interp;

subplot(1,3,3);
plot3(data(1:50,2:4),data(51:100,2:4),data(101:150,2:4),'*')
hold on;boundsurface(w3);title('virginica 2,3,4');shading interp;
