clear 
clc

%preparing the dataset
dataset=xlsread('iris dataset.xlsx')';
target=[[ones(1,50);zeros(2,50)] [zeros(1,50);ones(1,50);zeros(1,50)] ...
        [zeros(2,50);ones(1,50)]];

%initializing the neural network 
net2=fitnet([5 10],'trainlm');
%train test validation 
[net2 tr2]=trainlm(net2,dataset,target);
train_errorC2=tr2.best_perf
validation_errorC2=tr2.best_vperf
test_errorC2=tr2.best_tperf
bestepochC2=tr2.best_epoch
plotperf(tr2); grid on; title('2 hidden layers with 5 neurons each')