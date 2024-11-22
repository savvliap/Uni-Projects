clear; 
clc; 
 
randn('seed',0);
traindata=100;
testdata=1000;
%the vectors containing the mean covariance and prior
avg=[2 1 3];
S=[0.5 1 1.2];
prior=[0.5 0.3 0.2];

%making the gauss distributions according to the data given 
[x,trainclass]=generate_gauss_classes(avg,S,prior,traindata);
[y,testclass]=generate_gauss_classes(avg,S,prior,testdata);

%computing the knn error for the 3 k's 
knnerror=inf(1,3);
for k=1:3;
    knnest=k_nn_classifier(x,trainclass,k,y);
    knnerror(k)=compute_error(testclass,knnest);
end

errork1=knnerror(1)
errork2=knnerror(2)
errork3=knnerror(3)

%computing the bayes error
bayesest=bayes_classifier(avg,S,prior,y);
bayeserror=compute_error(testclass,bayesest)


