function [] = dataplot(data)
    plot(data(1:50,4),data(1:50,5),'*')
    plot(data(51:100,4),data(51:100,5),'*')
    if (length(data)>100)
        plot(data(101:150,4),data(101:150,5),'*')
    end    
end

