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

train1=mvnrnd(mean1,cov,floor(traindata*prior));
train2=mvnrnd(mean2,cov,floor(traindata*prior));
train3=mvnrnd(mean3,cov,ceil(traindata*prior));


test=[mvnrnd(mean1,cov,floor(testdata*prior));
      mvnrnd(mean2,cov,floor(testdata*prior));
      mvnrnd(mean3,cov,ceil(testdata*prior))];
%test dataset randomized
x1=test(randperm(size(test,1)),:);

% i will use the training dataset to find the estimated means and
% covariances                         
estmean1=(1/floor(traindata*prior))*[sum(train1(:,1)) sum(train1(:,2))...
                                     sum(train1(:,3))]                            
estmean2=(1/floor(traindata*prior))*[sum(train2(:,1)) sum(train2(:,2))...
                                     sum(train2(:,3))]
estmean3=(1/ceil(traindata*prior))*[sum(train3(:,1)) sum(train3(:,2))...
                                     sum(train3(:,3))]

cov1=zeros(3,3);
cov2=zeros(3,3);
cov3=zeros(3,3);
%in order to find the common covariance matrix we will compute the mean 
%of all the three covariance matrices.
for i=1:3333
    cov1=cov1+([train1(i,:)-estmean1]'*[train1(i,:)-estmean1]);
    cov2=cov2+([train2(i,:)-estmean2]'*[train2(i,:)-estmean2]);
    cov3=cov3+([train3(i,:)-estmean3]'*[train3(i,:)-estmean3]);
end
%estimated coavariance
estcov=(1/(traindata-1))*(cov1+cov2+cov3)

%classifying the test dataset and finding the new error
mah=zeros(1000,3);
class=zeros(1000,3);
for i=1:length(x1)
    %it fills the first column
    mah(i,1)=mahalanobis([x1(i,:)]',estmean1',estcov);
    %it fills the second column
    mah(i,2)=mahalanobis([x1(i,:)]',estmean2',estcov);
    %it fills the third column 
    mah(i,3)=mahalanobis([x1(i,:)]',estmean3',estcov);
    
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
%surely right ) and by checking both ways with rng 'default' 
%the same result comes out.
error1=abs(sum(class(:,1)==1)-floor(testdata*prior))/floor(testdata*prior)
error2=abs(sum(class(:,2)==1)-floor(testdata*prior))/floor(testdata*prior)
error3=abs(sum(class(:,3)==1)-ceil(testdata*prior))/ceil(testdata*prior)

totalerror=(error1+error2+error3)
