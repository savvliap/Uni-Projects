clear;
clc;
%where the x values will be stored 
x=zeros(1,1000);
%determining the first element
x(1)=0;
%the factor I need 
n=0.0001;
%counts the itterations 
reps=0;
%when i can stop the itterations 
e=0.0000001;

for i=1:1000
    %counter increments in each itteration 
    reps=reps+1;
    
    %gradient descent (cpmment lines 21-22 for 4b)
    x(i+1)=x(i)-n*(4*((x(i)-5)^3)+3);
  
    %Newtons Method
     x(i+1)= x(i)-((12*(x(i)-5)^2)^-1)*(4*((x(i)-5)^3)+3);
    
    %stoping criterion
    if (abs(x(i+1)-x(i))<e)
        break
    end
end

reps
xmin=x(reps)
fmin=((xmin-5)^4)+3*xmin


         
   