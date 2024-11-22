clear;
clc;

%question A first part (batch perceptron with margin )
%meas is for measurements 
meas=xlsread('iris dataset.xlsx');
data=[ones(150,1) meas];
meas(51:150,:)=-meas(51:150,:);
a=[[ones(50,1); -ones(100,1)] meas];
w=[1 0.5 1]';

%batch perceptron
subplot(1,2,1); hold on; dataplot(data);
A=[a(:,1) a(:,4) a(:,5)];
w1=batch_perce(A,w,0.1);
title('batch perceptron');

%batch perceptron with margin 
subplot(1,2,2);hold on; dataplot(data);
A=[a(:,1) a(:,4) a(:,5)];
w2=batch_rel_marg(A,w,5,0.05);
title('batch relaxation margin');
