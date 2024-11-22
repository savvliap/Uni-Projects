function [w]=LMS(x,w0,b,step)
w=w0;
stop=10e-7;
i=1;
while i<2000
        t=2*step*(x(i,:)'*(x(i,:)*w-b(i)));
        w=w-2*step*(x(i,:)'*(x(i,:)*w-b(i)));
        if abs(t)<stop
            bound=@(x) -w(2)/w(3)*x-w(1)/w(3);
            ezplot(bound)
            axis([0 8 0 4])
            return
        end
        i=i+1;
        if i==151
            i=1;
        end
end
    
end

