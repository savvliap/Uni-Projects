clear; 
clc; 
 
randn('seed',0);
traindata=100;
testdata=1000;
%the vectors containin the mean covariance and prior
avg=[2 1 3];
S=[0.5 1 1.2];
prior=[0.5 0.3 0.2];

%making the gauss distributions according to the data given 
[x,trainclass]=generate_gauss_classes(avg,S,prior,traindata);
[y,testclass]=generate_gauss_classes(avg,S,prior,testdata);

%checking the errors by giving k values 1:50
optimumerror=inf(1,100);
for k=1:100;
    knnest=k_nn_classifier(x,trainclass,k,y);
    optimumerror(k)=compute_error(testclass,knnest);
end

%ploting the errors (using red so the 0.5 error will be more visible)
plot(optimumerror,'r')
title(['errors for k=1 to k=100'])
%showing the mimimum error and the optimum number of neighboors k 
minerror=min(optimumerror)
koptimum=find(optimumerror==minerror)
