clear 
clc 


x=[-2 1 0 -2 2 2];
y=[1 2 0 -1 0 1];
coef=[x;y];
class=[0 0 1 1 1 1];

net=perceptron;
net.trainParam.epochs = 10;
net=train(net,coef,class);
w=net.iw{1,1}
b=net.b{1}

plotpv(coef,class)
hold on
plotpc(w,b)