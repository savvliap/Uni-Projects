clear;
clc;
%rng is useful in order not to lose the results (but whatever)
rng('default');
%We can change the N according to how many sample we need 
N=20;

Z1=0.5+6*rand(1,N);
for i=1:N
    if Z1(i)<=1.5
        Z1(i)=1;
    elseif Z1(i)<=2.5
        Z1(i)=2;
    elseif Z1(i)<=3.5
        Z1(i)=3;
    elseif Z1(i)<=4.5
        Z1(i)=4;
    elseif Z1(i)<=5.5
        Z1(i)=5;
    elseif Z1(i)<=6.5
        Z1(i)=6;
    end
end

%2a answer
disp(Z1);
%2b answer
 h=(histogram(Z1,'Normalization','probability'));
 h.BinWidth=0.15;