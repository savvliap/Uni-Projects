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

%svm polynomial
polymodel=fitcsvm(train(:,2:4),train(:,1),'KernelFunction', 'polynomial');
svm_3d_plot(polymodel,data(:,2:4),data(:,1));title('polynomial Kernel');


poly_trainerr=sum(predict(polymodel,train(:,2:4))~=train(:,1))/length(train(:,1))
poly_testerr=sum(predict(polymodel,test(:,2:4))~=test(:,1))/length(test(:,1))
poly_valerr=sum(predict(polymodel,validat(:,2:4))~=validat(:,1))/length(validat(:,1))

%svm rbf
rbfmodel=fitcsvm(train(:,2:4),train(:,1),'KernelFunction', 'rbf');
svm_3d_plot(rbfmodel,data(:,2:4),data(:,1)); title('rbf Kernel')

rbf_trainerr=sum(predict(rbfmodel,train(:,2:4))~=train(:,1))/length(train(:,1))
rbf_testerr=sum(predict(rbfmodel,test(:,2:4))~=test(:,1))/length(test(:,1))
rbf_valerr=sum(predict(rbfmodel,validat(:,2:4))~=validat(:,1))/length(validat(:,1))

