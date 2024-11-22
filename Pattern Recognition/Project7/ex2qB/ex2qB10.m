clear 
clc
%preparing the dataset
dataset=xlsread('iris dataset.xlsx')';
target=[[ones(1,50);zeros(2,50)] [zeros(1,50);ones(1,50);zeros(1,50)] ...
        [zeros(2,50);ones(1,50)]];
%initializing the neural network with one neuron in the hidden layer
net10=fitnet(10,'trainlm');
%train test validation 
[net10 tr10]=trainlm(net10,dataset,target);
train_error10=tr10.best_perf
validation_error10=tr10.best_vperf
test_error10=tr10.best_tperf
bestepoch10=tr10.best_epoch
plotperf(tr10); grid on; title('10 neurons in the hidden layer')