function [ discr_func ] = discriminant(x,mean,cov, prior )
%returns the discriminant function 
%it takes as inputs:
%1.the vector of random variables x
%2.the vector of mean values mean
%3.the covariance matrices Ó
%and computes the discriminant function g(x)

%the dimension of the distribution
dim=length(x);

%we compute the vector and matrices product and the constant part 
%separately and then we sum them to 
matrixprod= ((x-mean)'*inv(cov))*(x-mean);
constpart= -(dim/2)*log(2*pi)-(1/2)*log(det(cov))+log(prior);

discr_func=-(1/2)*matrixprod +constpart;
end

