clear;
clc;

% vector N question 2c(comment all the other N vectors
% need to be commented)
N=[10 20 50 100 500 1000];

%vectors N for question 2e uncomment only one 
%at a time 
% N=10*ones(1,5);
% N=20*ones(1,5);
% N=50*ones(1,5);
% N=100*ones(1,5);
% N=500*ones(1,5);
% N=1000*ones(1,5);

Average=zeros(1,length(N));
Variance=zeros(1,length(N));
Skewness=zeros(1,length(N));
Kurtosis=zeros(1,length(N));

for j=1:length(N)
    Z1=0.5+6*rand(1,N(j));
    for i=1:N(j)
        if Z1(i)<=1.5
            Z1(i)=1;
        elseif Z1(i)<=2.5
            Z1(i)=2;
        elseif Z1(i)<=3.5
            Z1(i)=3;
        elseif Z1(i)<=4.5
            Z1(i)=4;
        elseif Z1(i)<=5.5
            Z1(i)=5;
        elseif Z1(i)<=6.5
            Z1(i)=6;
        end
    end
    
 Average(j)=mean(Z1);
 Variance(j)=var(Z1);
 Skewness(j)=skewness(Z1);
 Kurtosis(j)=kurtosis(Z1);
end
 
 Average
 Variance
 Skewness
 Kurtosis