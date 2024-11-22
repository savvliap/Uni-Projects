clear
clc

%implementing the classes
class1=[-5.01 -5.43 1.08 0.83 -2.67 4.94 -2.51 -2.25 5.56 1.03;
        -8.12 -3.48 -5.52 -3.78 0.63 3.29 2.09 -2.13 2.86 -3.33;
        -3.68 -3.54 1.66 -4.11 7.39 2.08 -2.59 -6.94 -2.26 4.33]';

class2=[-0.91 1.30 -7.75 -5.47 6.14 3.60 5.37 7.18 -7.39 -7.50;
        -0.18 -2.06 -4.54 0.50 5.72 1.26 -4.63 1.46 1.17 -6.32;
        -0.05 -3.53 -0.95 3.92 -4.85 4.36 -3.65 -6.66 6.30 -0.31]';

class3=[5.35 5.12 -1.34 4.48 7.11 7.17 5.75 0.77 0.90 3.52;
        2.26 3.22 -5.31 3.42 2.39 4.33 3.97 0.27 -0.43 -0.36;
        8.13 -2.66 -9.87 5.19 9.21 -0.98 6.65 2.41 -8.71 6.43];

prior=[0.8 0.1 0.1];

char=[class1(:,1:3);class2(:,1:3);class3(:,1:3)];

mean1=mean(class1(:,1:3));
mean2=mean(class2(:,1:3));
mean3=mean(class3(:,1:3));

cov1=cov(class1(:,1:3));
cov2=cov(class2(:,1:3));
cov3=cov(class2(:,1:3));

% %%%%%%%%%%%%%%%%%%%%%% question 6 %%%%%%%%%%%%%%%%%%%%%%%%%
%calculatig the decosion boundary
%I use the vpa in order to get decimal results (beacuase otherwise 
%I dont think that collect does somethink here but I leave it here 
%advance
syms x1 x2 x3
assume(x3,'real')
g1=collect(vpa(discriminant([x1 x2 x3]',mean1',cov1,prior(1))))
g2=collect(vpa(discriminant([x1 x2 x3]',mean2',cov2,prior(2))))
g3=collect(vpa(discriminant([x1 x2 x3]',mean2',cov2,prior(3))))

boundary1=solve(g1==g2,x1)
boundary2=solve(g1==g2,x2)
boundary3=solve(g1==g2,x3)
         

%%%%%%% examining the error%%%%%
g=zeros(length(char),3);
class=zeros(length(char),3);
wrong=0;

for i=1:length(char)
    g(i,1)=discriminant(char(i,1:3)',mean1',cov1,prior(1));
    g(i,2)=discriminant(char(i,1:3)',mean2',cov2,prior(2));
    g(i,3)=discriminant(char(i,1:3)',mean3',cov3,prior(3));
    
    if max([g(i,:)])== g(i,1) 
        class(i,1)=1;
        if i>10
            wrong=wrong+1;
        end
    elseif max([g(i,:)])== g(i,2)
        class(i,2)=1;
        if (i<=10) && (i>20)
            wrong=wrong+1;
        end
     elseif max([g(i,:)])== g(i,3)
        class(i,3)=1;
        if i<=20
            wrong=wrong+1;
        end
    end
end

class
wrong
error=wrong/length(char)
