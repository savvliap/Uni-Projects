clear 
clc
%preparing the dataset
dataset=xlsread('iris dataset.xlsx')';
target=[[ones(1,50);zeros(2,50)] [zeros(1,50);ones(1,50);zeros(1,50)] ...
        [zeros(2,50);ones(1,50)]];
%initializing the neural network with one neuron in the hidden layer
net1=fitnet(1,'trainlm');
%train test validation 
[net1 tr1]=trainlm(net1,dataset,target);
best_train_error1=tr1.best_perf
best_validation_error1=tr1.best_vperf
best_test_error1=tr1.best_tperf
bestepoch1=tr1.best_epoch
plotperf(tr1); grid on; title('1 neuron in the hidden layer')