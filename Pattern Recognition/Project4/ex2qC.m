clear; 
clc; 
 
randn('seed',0);
testdata=1000;

avg=[2 1 3];
S=[0.5 1 1.2];
prior=[0.5 0.3 0.2];

[y,class]=generate_gauss_classes(avg,S,prior,testdata);

 h=[0.05 0.08 0.1 0.3];
 for i=1:length(h)
    x=-5:h(i):10;
    %we supposedly dont have the information about the averages and the
    %covariances so what is known to us it the total pdf 
    pdfx=prior(1)*(1/sqrt(2*pi*(S(1)^(0.5)))*exp(-((x-avg(1)).^2)/2*(S(1))))...
         +prior(2)*(1/sqrt(2*pi*(S(2)^(0.5)))*exp(-((x-avg(2)).^2)/2*(S(2))))...
         +prior(3)*(1/sqrt(2*pi*(S(3)^(0.5)))*exp(-((x-avg(3)).^2)/2*(S(3))));

    parzen=Parzen_gauss_kernel(y,h(i),min(x),max(x));
    
    subplot(2,2,i)
    plot(x,pdfx); hold on ;
    plot(x,parzen);
    title(['h=',num2str(h(i))])
 end
