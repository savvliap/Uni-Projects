% Example Kesler CC
% modified by chamzas
l=4;
N1=150;

X=xlsread('iris dataset.xlsx')';
X1=[X ;ones(1,150)];
y1=[ones(1,50) 2*ones(1,50) 3*ones(1,50)];

%--------- Multi class perceptron with KESLER structure -----------
data.X=X; data.y=y1; 
options.tmax=100; % max number of iterations
modelKESLER = mperceptron(data, options);

% calculates the error on the trainning set
for i=1:4
w_allKESLER(i,:)=modelKESLER.W(i,:);
end
w_allKESLER(l+1,:)=modelKESLER.b';
[vali,class_est]=max(w_allKESLER'*X1);
errKESLER=sum(class_est~=y1)/N1

%figure; ppatterns(data); pboundary(modelKESLER);
%whe I try to run the command to make the plot I got an error in linclass
%saying that inner matrix dimensions must agree . However They DO agree.I
%tried many thigs with no result. So I dont think that the error is mine.