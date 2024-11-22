clear; 
clc; 

randn('seed',0);
traindata=100;
testdata=1000;
%I have explained those commands already
avg=[2 1 3];
cvr=[0.5 1 1.2];
prior=[0.5 0.3 0.2];

[x,trainclass]=generate_gauss_classes(avg,cvr,prior,traindata);
[y,testclass]=generate_gauss_classes(avg,cvr,prior,testdata);

%the neural network that was give was not working 
%properly so I used another functions 
trainclass=ind2vec(trainclass);
net=newpnn(x,trainclass);
%I get a 3*length(y) every row represents one class
yclassification=sim(net,y);
%pnn estimation in one vector
pnnest=vec2ind(yclassification);
%computing the error
pnnerrror=compute_error(testclass,pnnest)
