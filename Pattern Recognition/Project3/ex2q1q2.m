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
%the characteristic we examine is in the first column of every class
char=[class1(:,1);class2(:,1)];
mean=[mean(class1(:,1)) mean(class2(:,1))];
cov=[cov(class1(:,1)) cov(class2(:,1))];

%%%%%%%%%%%%%%%%%%%%%% question 1 %%%%%%%%%%%%%%%%%%%%%%%%%
%calculatig the decision boundary
%I use the vpa in order to get decimal results (beacuase otherwise 
%i get fractions 
%I use the collect in order to get the g1 and g2 as polyonyms
syms x1 real;
g1=collect(vpa(discriminant(x1,mean(1),cov(1),prior)))
g2=collect(vpa(discriminant(x1,mean(2),cov(2),prior)))
boundary=solve(g1==g2,x1)

%%%%%%%%%%%%%%%%%%%%%%%% question 2 %%%%%%%%%%%%%%%%%%%%%%%%%
g=zeros(length(char),2);
class=zeros(length(char),2);
wrong=0;

for i=1:length(char)
    g(i,1)=discriminant(char(i),mean(1),cov(1),prior);
    g(i,2)=discriminant(char(i),mean(2),cov(2),prior);
    
    %the data examined belongs to the class with the biggest
    %discrimination function
    if max([g(i,:)])== g(i,1)
        %the first column is for class 1 etc 
        class(i,1)=1;
        %I know that the elements 1:10 belong to class1
        if i>10
            wrong=wrong+1;
        end
        %the first column is for class 1 etc 
    elseif max([g(i,:)])== g(i,2)
        class(i,2)=1;
        %I know that the elements 10:20 belong to class2
        if i<=10
            wrong=wrong+1;
        end
    end
end

class
wrong
error=wrong/length(char)
