clear; 
clc; 

%randn('seed',0);
rng('default')
%defining the values of h 
h=[0.05 0.2];
%defining the number of the random distribution samples 
N=[32 256 5000];

for i=1:length(h)
    %in order to get the x axis of the plot 0 to 2 
    xaxis=[0:h(i):2];
    pdf=0.5*xaxis.^0;
    
    for j=1:length(N)
        %creating the distribution that I will use for the parzen window
        rdistr=2*rand(1,N(j));
        %running the parzen window function from eclass
        parzen=Parzen_gauss_kernel(rdistr,h(i),min(xaxis),max(xaxis));

        if (i==1)
            %the upper three plots will be for h=0.05
            subplot(2,3,j)
            plot(xaxis,parzen,xaxis,pdf)
            title(['h=',num2str(h(i)),'  N=',num2str(N(j))])
        else
            %the down three plots will be for h=0.2
            subplot(2,3,j+3)
            plot(xaxis,parzen,xaxis,pdf) 
            title(['h=',num2str(h(i)),'  N=',num2str(N(j))])
        end
    end
end