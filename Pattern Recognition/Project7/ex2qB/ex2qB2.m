clear 
clc
%preparing the dataset
dataset=xlsread('iris dataset.xlsx')';
target=[[ones(1,50);zeros(2,50)] [zeros(1,50);ones(1,50);zeros(1,50)] ...
        [zeros(2,50);ones(1,50)]];
%initializing the neural network with one neuron in the hidden layer
net2=fitnet(2,'trainlm');
%train test validation 
[net2 tr2]=trainlm(net2,dataset,target);
train_error2=tr2.best_perf
validation_error2=tr2.best_vperf
test_error2=tr2.best_tperf
bestepoch2=tr2.best_epoch
plotperf(tr2); grid on; title('2 neurons in the hidden layer')