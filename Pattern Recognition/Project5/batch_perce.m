function [w,iter]=batch_perce(a,w0,step)
    sym r;
    w=w0;
    iter=0;
    norm1=1;

    while (norm1>0)
        iter=iter+1;
        w1=w;
        sum=0;
        for i=1:length(a(:,1))
            if (w'*a(i,:)'>0 )
                sum=sum-sign(w'*a(i,:)')*a(i,:);
            end
        end
        w=w+sum'*step; 
        norm1=abs(w1-w);
    end
    bound=@(r) -w(2)/w(3)*r-w(1)/w(3);
    ezplot(bound)
    axis([0 8 0 4])      
end