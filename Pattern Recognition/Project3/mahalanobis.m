function [ mah_dist ] = mahalanobis(x,mean,cov )
%computes the mahalanobis distance of a point to a distribution 
%it takes as inputs the vectors of the point we want to examine
%the mean and the covariance matrix and computes the mahalanobis distance

mah_dist=sqrt(((x-mean)'*inv(cov))*(x-mean));
end

