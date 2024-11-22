clear
clc

%preparing the dataset
meas1=xlsread('iris dataset.xlsx');
meas1(:,3)=[];
class=[ones(1,50) -ones(1,50) ones(1,50)];
data=[class' meas1];

train=[data(1:30,:);data(51:80,:);data(101:130,:)];
test=[data(31:40,:);data(81:90,:);data(131:140,:)];
validat=[data(41:50,:);data(91:100,:);data(141:150,:)];

%svm linear 
linmodel=fitcsvm(train(:,2:4),train(:,1),'KernelFunction', 'linear');
svm_3d_plot(linmodel,data(:,2:4),data(:,1)); hold on;

lin_trainerr=sum(predict(linmodel,train(:,2:4))~=train(:,1))/length(train(:,1))
lin_testerr=sum(predict(linmodel,test(:,2:4))~=test(:,1))/length(test(:,1))
lin_valerr=sum(predict(linmodel,validat(:,2:4))~=validat(:,1))/length(validat(:,1))





