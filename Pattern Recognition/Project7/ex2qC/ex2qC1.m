clear 
clc

%preparing the dataset
dataset=xlsread('iris dataset.xlsx')';
target=[[ones(1,50);zeros(2,50)] [zeros(1,50);ones(1,50);zeros(1,50)] ...
        [zeros(2,50);ones(1,50)]];

%initializing the neural network 
net1=fitnet([1 1],'trainlm');
%train test validation 
[net1 tr1]=trainlm(net1,dataset,target);
train_errorC1=tr1.best_perf
validation_errorC1=tr1.best_vperf
test_errorC1=tr1.best_tperf
bestepochC1=tr1.best_epoch
plotperf(tr1); grid on; title('2 hidden layers with 1 neuron each')

