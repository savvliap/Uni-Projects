% Example 2.5.1
% "Introduction to Pattern Recognition: A MATLAB Approach"
% S. Theodoridis, A. Pikrakis, K. Koutroumbas, D. Cavouras
% Modified for the AND and XOR examples by Chamzas 10/6/2012

close('all');
clear
clc

format compact
global figt4

%===================================================
% Generate the training set X1 for the XOR case
X1 = [0  0; 0 1; 1 0; 1 1]';
y1 = [-1 1 1 -1];
l=2;
N=4;  % Dimensionality and Number of vectors

% Plot the training set X1
figure(1), plot(X1(1,y1==1),X1(2,y1==1),'ro',X1(1,y1==-1),X1(2,y1==-1),'bo')
figure(1), axis equal

% %%%%%%%%%%%%%%% POLYNOMIAL KERNEL for the XOR %%%%%%%%%%%%%%%%%%%%%%%
figt4=2;
fprintf('\n\n');

kernel = 'poly';
kpar1=1; 
kpar2=2; 
C=4;
tol=0.001;
steps=100000;
eps=10^(-10);
method=1;
[alpha, b, w, evals, stp, glob] = SMO2(X1', y1', kernel, kpar1, kpar2, C, tol, steps, eps, method);
alpha';

mag=0.1;
xaxis=1;
yaxis=2;
input = zeros(1,size(X1',2));
bias=-b;  
aspect=0;
svcplot_book(X1',y1',kernel,kpar1,kpar2,alpha,bias,aspect,mag,xaxis,yaxis,input);

figure(figt4), axis equal

X_sup=X1(:,alpha'~=0);
alpha_sup=alpha(alpha~=0)';
y_sup=y1(alpha~=0);

% Classification of the training set
for i=1:N
    t=sum((alpha_sup.*y_sup).*CalcKernel(X_sup',X1(:,i)',kernel,kpar1,kpar2)')-b;
    if(t>0)
        out_train(i)=1;
    else
        out_train(i)=-1;
    end
end

% Computing the training error
Pe_train = sum(out_train.*y1<0)/length(y1)

