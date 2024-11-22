function number = Belong2(x_i,means)
    INF = 10000;
    min = INF;
    kk = size(means,1);
    number = 1;
    for i=1:kk
        if ~isempty(means{i})
            if norm(x_i - means{i}) < min
                min = norm(x_i - means{i});
                number = i;
            end
        end
    end
end
