function A_del = DeleteRow(A,r)
    n = size(A,1);
    if r == 1
        A_del = A(2:n,:);
    elseif r == n
        A_del = A(1:n-1,:);
    else
        A_del = [A(1:r-1,:);A(r+1:n,:)];
    end
end