function [w]=ho_kash(x,w0,step,b)
    Max_iter=1000;
    b_min=0.5;
    step= 0.01;
    w=w0;
    e  = 1e3;
    k=0;
    while ((sum(abs(e) > b_min)>0) && (k < Max_iter))
        k = k+1;
        e = (x*w)- b;
        e_plus  = 0.5*(e + abs(e));
        b = b + 2*step*e_plus;
        w = ((x'*x)^-1)*x'*b;
    end  

    f=@(x) -w(2)/w(3)*x-w(1)/w(3);
    ezplot(f)  
    axis([-1 8 -1 4])
end