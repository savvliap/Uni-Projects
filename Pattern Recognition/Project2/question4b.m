clear; 
clc;
%preparing the dataset 
data1= 0.5*randn(1,1000)+2;
data2= 0.2*randn(1,2000)+1.5;
%data=dataset(randperm(length(data)));
%I chose not to schuffle the elements for my own convenience
%as the result wasnt affected.
dataset=[data1,data2];

%the prior propabilities
prior1=1/3;
prior2=2/3;

%likelihood (I have already calculated the likelihoods and i 
%choose to use these (more condesed) form. 
likelihood1=0.5642*exp(-(dataset-2).^2);
likelihood2=0.8921*exp(-2.5*(dataset-1.5).^2);

%the matrix containing the costs 
lamda=[1 2;3 1];

%introducing those arrays in order to run the bayes minimum cost
%hypothesis test
bayesdata1=zeros(1,3000); 
bayesdata2=zeros(1,3000);

%i need those array to actually which data were 
%chosen wrongly
chose2should1=0;
chose1should2=0;

%these are not the costs actually. I just wanted to make the 
%condition below more compact and used those names 
cost2= (lamda(2,1)-lamda(1,1))*likelihood2*prior2;
cost1= (lamda(1,2)-lamda(2,2))*likelihood1*prior1;

%implementing the bayes minimum cost hypothesis test
for i=1:3000
    if(cost2(i)>cost1(i))
        bayesdata1(i)=0;
        bayesdata2(i)=dataset(i);
        else 
        bayesdata1(i)=dataset(i);
        bayesdata2(i)=0;
    end
    if (i<1001) && (bayesdata2(i)~=0)
        chose2should1=chose2should1+1;
    end
    if (i>1000) && (bayesdata1(i)~=0)
        chose1should2=chose1should2+1;
    end
end

actuallcost1=(lamda(1,2)-lamda(2,2))*(chose1should2/2000)*prior2;
actuallcost2=(lamda(2,1)-lamda(1,1))*(chose2should1/1000)*prior1;

%the experiments minimum cost would be 
mincost=actuallcost1+actuallcost2