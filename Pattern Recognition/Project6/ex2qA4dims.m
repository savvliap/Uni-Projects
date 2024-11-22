clear
clc

%preparing the dataset
meas1=xlsread('iris dataset.xlsx');
class=[ones(1,50) -ones(1,50) ones(1,50)];
data=[meas1 class'];

train=[data(1:30,:);data(51:80,:);data(101:130,:)];
test=[data(31:40,:);data(81:90,:);data(131:140,:)];
validat=[data(41:50,:);data(91:100,:);data(141:150,:)];

%svm linear 
linmodel=fitcsvm(train(:,1:4),train(:,5), 'KernelFunction', 'linear');

lin_trainerr=sum(predict(linmodel,train(:,1:4))~=train(:,5))/length(train(:,5))
lin_testerr=sum(predict(linmodel,test(:,1:4))~=test(:,5))/length(test(:,5))
lin_valerr=sum(predict(linmodel,validat(:,1:4))~=validat(:,5))/length(validat(:,5))
