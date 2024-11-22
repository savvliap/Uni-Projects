clear 
clc

meas=xlsread('iris dataset.xlsx');
data=[ones(150,1) meas];
meas(51:150,:)=-meas(51:150,:);
a=[[ones(50,1); -ones(100,1)] meas];

%what I will use for the MSE and LMS
Y=[a(:,1) a(:,4) a(:,5)];
b=ones(150,1);
w=[1 0.5 1]';

%question B first part (MSE )
subplot(1,2,1);hold on;dataplot(data);
k=MSE(Y,b);
title('MSE');

%question B second part (LMS)
subplot(1,2,2);hold on;dataplot(data);
m=LMS(Y,w,b,0.001);
title('LMS');