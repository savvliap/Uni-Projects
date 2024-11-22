clear 
clc

%preparing the dataset
dataset=xlsread('iris dataset.xlsx')';
traindata=[dataset(:,1:40) dataset(:,51:90) dataset(:,101:140)];
testdata=[dataset(:,41:50) dataset(:,91:100) dataset(:,141:150)];

target=[[ones(1,40);zeros(2,40)] [zeros(1,40);ones(1,40);zeros(1,40)] ...
        [zeros(2,40);ones(1,40)]];
testtarget=[[ones(1,10);zeros(2,10)] [zeros(1,10);ones(1,10);zeros(1,10)] ...
        [zeros(2,10);ones(1,10)]];
    
%initializing the neural network 
net=newp(minmax(dataset),3); 
net.trainParam.epochs =200; 
net.trainParam.goal = 5e-2;
net.iw{1,1}=rand(3,4);
net.b{1,1}=rand(3,1);

%training the neural network 
[neur tr]=train(net,traindata,target);

plotperf(tr)
mintrainerror=min(tr.perf)
optimum=find(tr.perf==mintrainerror)

%testin the neural network 
test=sim(neur,testdata);
testerror=compute_error(testtarget,test)