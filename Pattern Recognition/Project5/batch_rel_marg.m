function [w]=batch_rel_marg(x,w0,margin,step)
    sym r;
    w=w0;
    iter=0;
    miscl=1;
        while (miscl>0 && iter<200)
            sum=0;
            miscl=0;
            for i=1:length(x(:,1))
                if( w'*x(i,:)'<margin )               
                    miscl=miscl+1;
                    sum=sum-((w'*x(i,:)'-margin)*x(i,:))/((norm(x(i,:))^2));
                end   
            end
            w=w+step*sum';
            iter=iter+1;
        end
        bound=@(r) -w(2)/w(3)*r-w(1)/w(3);
        ezplot(bound)
        axis([0 8 0 4])       
end
