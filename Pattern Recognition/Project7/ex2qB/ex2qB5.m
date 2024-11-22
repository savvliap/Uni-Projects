clear 
clc
%preparing the dataset
dataset=xlsread('iris dataset.xlsx')';
target=[[ones(1,50);zeros(2,50)] [zeros(1,50);ones(1,50);zeros(1,50)] ...
        [zeros(2,50);ones(1,50)]];
%initializing the neural network with one neuron in the hidden layer
net5=fitnet(5,'trainlm');
%train test validation 
[net5 tr5]=trainlm(net5,dataset,target);
train_error5=tr5.best_perf
validation_error5=tr5.best_vperf
test_error5=tr5.best_tperf
bestepoch5=tr5.best_epoch
plotperf(tr5); grid on; title('5 neurons in the hidden layer')