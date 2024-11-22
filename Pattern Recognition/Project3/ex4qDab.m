clear 
clc 

%rng('default')
traindata=10000;
testdata=1000;

prior1=1/6;
prior2=1/6;
prior3=2/3;

mean1=[0 0 0]';
mean2=[0 2 2]';
mean3=[3 3 4]';

cov1=[0.8 0.2 0.1;0.2 0.8 0.2;0.1 0.2 0.8];
cov2=[0.6 0.2 0.01;0.2 0.8 0.01;0.01 0.01 0.6];
cov3=[0.6 0.1 0.1;0.1 0.6 0.1;0.1 0.1 0.6];


train=[mvnrnd(mean1,cov1,floor(traindata*prior1));
        mvnrnd(mean2,cov2,floor(traindata*prior2));
        mvnrnd(mean3,cov3,ceil(traindata*prior3))];
%train dataset 
x=train(randperm(size(train,1)),:);


test=[mvnrnd(mean1,cov1,floor(testdata*prior1));
      mvnrnd(mean2,cov2,floor(testdata*prior2));
      mvnrnd(mean3,cov3,ceil(testdata*prior3))];
%test dataset
x1=test(randperm(size(test,1)),:);

%classifying the test dataset and finding the new error
g=zeros(1000,3);
class=zeros(1000,3);
%now I have the random test sample
for i=1:length(x1)
    %it fills the first column
    g(i,1)=discriminant([x1(i,:)]',mean1,cov1,prior1);
    %it fills the second column
    g(i,2)=discriminant([x1(i,:)]',mean2,cov2,prior2);
    %it fills the third column 
    g(i,3)=discriminant([x1(i,:)]',mean3,cov3,prior3);
    
    if max([g(i,:)])== g(i,1)
        %the first column is for class 1 etc 
        class(i,1)=1;
    elseif max([g(i,:)])== g(i,2)
        class(i,2)=1;
    else 
        class(i,3)=1;
    end
end

%it seems that this checking proccess is right. I have checked without
%shuffling the elements in the test array (in wich situation it would be
%surely right ) and with rng 'default' the same result comes out.
error1=abs(sum(class(:,1)==1)-floor(testdata*prior1))/floor(testdata*prior1)
error2=abs(sum(class(:,2)==1)-floor(testdata*prior2))/floor(testdata*prior2)
error3=abs(sum(class(:,3)==1)-ceil(testdata*prior3))/ceil(testdata*prior3)

totalerror=(error1+error2+error3)
