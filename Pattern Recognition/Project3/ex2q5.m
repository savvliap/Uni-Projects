clear
clc

%implementing the classes
%the prior of the third class is 0 so the third class should 
%not exist at all for this question
class1=[-5.01 -5.43 1.08 0.83 -2.67 4.94 -2.51 -2.25 5.56 1.03;
        -8.12 -3.48 -5.52 -3.78 0.63 3.29 2.09 -2.13 2.86 -3.33;
        -3.68 -3.54 1.66 -4.11 7.39 2.08 -2.59 -6.94 -2.26 4.33]';

class2=[-0.91 1.30 -7.75 -5.47 6.14 3.60 5.37 7.18 -7.39 -7.50;
        -0.18 -2.06 -4.54 0.50 5.72 1.26 -4.63 1.46 1.17 -6.32;
        -0.05 -3.53 -0.95 3.92 -4.85 4.36 -3.65 -6.66 6.30 -0.31]';

prior=1/2;
char=[class1(:,1:2);class2(:,1:2)];

mean1=[mean(class1(:,1:2))];
mean2=[mean(class2(:,1:2))];

cov1=[cov(class1(:,1:2))];
cov2=[cov(class2(:,1:2))];

%%%%%%%%%%%%%%%%%%%%%% question 3 %%%%%%%%%%%%%%%%%%%%%%%%%
%calculatig the decosion boundary
%I use the vpa in order to get decimal results (beacuase otherwise 
%i get fractions 
%I use the collect in order to get the g1 and g2 as polyonyms
syms x1 x2
assume(x2,'real')
g1=collect(vpa(discriminant([x1 x2]',mean1',cov1,prior)))
g2=collect(vpa(discriminant([x1 x2]',mean2',cov2,prior)))
boundary1=collect(solve(g1==g2,x1))
boundary2=collect(solve(g1==g2,x2))

%%%%%%% examining the error%%%%%
g=zeros(length(char),2);
class=zeros(length(char),2);
wrong=0;

for i=1:length(char)
    g(i,1)=discriminant(char(i,1:2)',mean1',cov1,prior);
    g(i,2)=discriminant(char(i,1:2)',mean2',cov2,prior);
    
    if max([g(i,:)])== g(i,1) 
        class(i,1)=1;
        if i>10
            wrong=wrong+1;
        end
    elseif max([g(i,:)])== g(i,2)
        class(i,2)=1;
        if i<=10
            wrong=wrong+1;
        end
    end
end

class
wrong
error=wrong/length(char)
