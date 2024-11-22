clear
clc

meas=xlsread('iris dataset.xlsx');
data=[ones(100,1), meas(51:150,:)];
meas(100:150,:)=-meas(100:150,:);
a=[[ones(50,1); -ones(50,1)], meas(51:150,:)];

%what I will use for the MSE and Ho-Kashyap 
Y=[a(:,1) a(:,4) a(:,5)];
b=ones(100,1);
w=[1 0.5 1]';

%question C first part (MSE)
subplot(1,2,1);hold on;dataplot(data);
MSE(Y,b);
title('MSE Versicolor/Virginica');

%question C second part (Ho-Kashyap)
subplot(1,2,2);hold on;dataplot(data);
w=ho_kash(Y,w,b,0.5);
title('Ho-Kashyap Versicolor/Virginica')