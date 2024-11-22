function [ eucl_dist ] = euclidean(point1,point2)
%euclidean distance 
%it gets as inputs the vector x of the point to wich we want
%compute the distance and returns the euclidian distance 
eucl_dist=sqrt(((point1-point2)')*(point1-point2));
end

