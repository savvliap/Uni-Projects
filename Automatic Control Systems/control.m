function [ u ] = control( k, x , theta, upar )
    u1=k*x;
    u2=theta(1)*(x(5)*cos(x(2))*log(abs(x(5)))+x(3)*cos(x(3))*cos(x(5)))...
       +theta(2)*(x(3)*log(abs(x(1)))*log(abs(x(5))))...
       +theta(3)*(x(2)*x(5)*cos(x(2))+x(1)*log(abs(x(3))))...
       +theta(4)*(x(4)*cos(x(1))*cos(x(4)))...
       +theta(5)*(x(4)*cos(x(5))*log(abs(x(1))))...
       +theta(6)*(x(2)*cos(x(2)));
    u3=(upar(1)*(x(2)*cos(x(2))*cos(x(3))+x(3)*cos(x(1))*log(abs(x(1))))...
        +upar(2)*((x(5)^2)*cos(x(1)))...
        +upar(3)*(cos(x(5))*log(abs(x(1)))*log(abs(x(4))))...
        +upar(4)*(x(1)*x(4)*log(abs(x(3)))+x(2)*x(5)*log(abs(x(2))))...
        +upar(5)*(cos(x(3))*cos(x(4))*log(abs(x(5))))...
        +upar(6)*(x(3)*cos(x(3))*cos(x(5))+(cos(x(1))^2)*log(abs(x(2)))))^2; 
    u=u1+u2/u3;
end

