function [w] = MSE( Y,b )
    w=(((Y'*Y)^-1)*Y')*b;
    decision=@(x) -w(2)/w(3)*x-w(1)/w(3);
    ezplot(decision)
    axis([0 8 0 4])
end

