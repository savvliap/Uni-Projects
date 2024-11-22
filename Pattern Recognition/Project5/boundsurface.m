function [ ] = boundsurface(w)
    sym('x1');
    sym('x2');
    sym('x3');
    plot3(-5:0.2:10,-5:0.2:10,-5:0.2:10);
    hold on 
    bound=@(x1,x2) -(w(3)*x1 + w(4)*x2 +w(1))/w(2);
    ezsurf(bound)
end

