clear
clc

%preparing the dataset
meas1=xlsread('iris dataset.xlsx');
class=[ones(1,50) -ones(1,50) ones(1,50)];
data=[meas1 class'];

train=[data(1:30,:);data(51:80,:);data(101:130,:)];
test=[data(31:40,:);data(81:90,:);data(131:140,:)];
validat=[data(41:50,:);data(91:100,:);data(141:150,:)];

%svm poly 4 dimesnsions  
polymodel=fitcsvm(train(:,1:4),train(:,5), 'KernelFunction', 'polynomial');

polytrainerr=sum(predict(polymodel,train(:,1:4))~=train(:,5))/length(train(:,5))
polytesterr=sum(predict(polymodel,test(:,1:4))~=test(:,5))/length(test(:,5))
polyvalerr=sum(predict(polymodel,validat(:,1:4))~=validat(:,5))/length(validat(:,5))

%rbf 4 dimensions 
rbfmodel=fitcsvm(train(:,1:4),train(:,5), 'KernelFunction', 'rbf');

rbftrainerr=sum(predict(rbfmodel,train(:,1:4))~=train(:,5))/length(train(:,5))
rbftesterr=sum(predict(rbfmodel,test(:,1:4))~=test(:,5))/length(test(:,5))
rbfvalerr=sum(predict(rbfmodel,validat(:,1:4))~=validat(:,5))/length(validat(:,5))