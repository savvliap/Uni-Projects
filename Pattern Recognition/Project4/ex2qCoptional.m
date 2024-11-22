clear; 
clc; 
 
randn('seed',0);
testdata=1000;

avg=[2 1 3];
S=[0.5 1 1.2];
prior=[0.5 0.3 0.2];

[y,class]=generate_gauss_classes(avg,S,prior,testdata);

y1=y(1:500);
y2=y(501:800);
y3=y(801:1000);

%first attempt
step=0.01;
h=0.01:step:0.8;
% second attempt (I know from the first attempt that the minimum error is 
%in 0.5 so I search between this area).
% step=0.001;
% h=0.03:step:0.07;
error=inf(1,length(h));
for i=1:length(h)
    parzen1=Parzen_gauss_kernel(y1,h(i),min(y1),max(y1));
    parzen2=Parzen_gauss_kernel(y2,h(i),min(y2),max(y2));
    parzen3=Parzen_gauss_kernel(y3,h(i),min(y3),max(y3));
       
    %the mean is the point where the new pdf has its biggest price 
    parmean=[min(y1)+h(i)*find(parzen1==max(parzen1))...
             min(y2)+h(i)*find(parzen2==max(parzen2))...
             min(y3)+h(i)*find(parzen3==max(parzen3))];
    
    Spar=[std(y1) std(y2) std(y3)];

    bayes=bayes_classifier(parmean,Spar,prior,y);
    error(i)=compute_error(class,bayes);
end

plot(h,error)
minerror=min(error)
title(['error for h=0.001 to h=0.8'])    
hoptimum=h(1)+step*min(find(error==minerror))



