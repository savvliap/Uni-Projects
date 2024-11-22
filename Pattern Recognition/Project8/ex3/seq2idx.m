function [row col] = seq2idx(id,n)
    if mod(id,n)==0
        row = n;
        col = id/n;
    else
        row = mod(id,n);
        col = ceil(id/n);
    end
end