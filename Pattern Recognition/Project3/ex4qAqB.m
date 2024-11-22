clear 
clc 

%rng('default')
traindata=10000;
testdata=1000;

prior=1/3;
mean1=[0 0 0]';
mean2=[0 2 2]';
mean3=[3 3 4]';

cov=[0.8 0.2 0.1;
     0.2 0.8 0.2;
     0.1 0.2 0.8];


train=[mvnrnd(mean1,cov,floor(traindata*prior));
        mvnrnd(mean2,cov,floor(traindata*prior));
        mvnrnd(mean3,cov,ceil(traindata*prior))];
%train dataset 
x=train(randperm(size(train,1)),:);

%we use mvrnd to generate multivariabe data that follow 
%gaussian distribution
test=[mvnrnd(mean1,cov,floor(testdata*prior));
      mvnrnd(mean2,cov,floor(testdata*prior));
      mvnrnd(mean3,cov,ceil(testdata*prior))];
%test dataset
x1=test(randperm(size(test,1)),:);

%classifiing the elements from the test dataset and finding the error
mah=zeros(1000,3);
class=zeros(1000,3);
%now I have the random test sample
for i=1:length(x1)
    %it fills the first column
    mah(i,1)=mahalanobis([x1(i,:)]',mean1,cov);
    %it fills the second column
    mah(i,2)=mahalanobis([x1(i,:)]',mean2,cov);
    %it fills the third column 
    mah(i,3)=mahalanobis([x1(i,:)]',mean3,cov);
    
    if min([mah(i,:)])== mah(i,1)
        %the first column is for class 1 etc 
        class(i,1)=1;
    elseif min([mah(i,:)])== mah(i,2)
        class(i,2)=1;
    else 
        class(i,3)=1;
    end
end

%it seems that this checking proccess is right. I have checked without
%shuffling the elements in the test array (in wich situation it would be
%surely right ) and with rng 'default' the same result comes out.
error1=abs(sum(class(:,1)==1)-floor(testdata*prior))
error2=abs(sum(class(:,2)==1)-floor(testdata*prior))
error3=abs(sum(class(:,3)==1)-ceil(testdata*prior))

totalerror=(1/testdata)*(error1+error2+error3)
