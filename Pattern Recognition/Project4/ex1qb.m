clear; 
clc; 

rng('default')
%defining the number of nearest neighboors be examined
k=[32 64 256];
%defining the number of the random distribution points 
N=5000;
%the step that the samples will be examined
step=0.1;
xaxis=0:step:2;
pdf=0.5*xaxis.^0;
%creating the distribution that I will use for knn
rdistr=2*rand(1,N);

for i=1:length(k)
    
    %running the knn density function from eclass
    knnest=knn_density_estimate(rdistr,k(i),min(xaxis),max(xaxis),step);

    subplot(1,3,i)
    plot(xaxis,knnest,xaxis,pdf)
    title(['k=',num2str(k(i)),'  N=',num2str(N)])    
end
