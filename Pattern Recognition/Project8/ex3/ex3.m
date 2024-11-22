clear all
close all
clc
load fisheriris.mat;
Xdata=meas;
classes=[2*ones(1,50) 3*ones(1,50) ones(1,50)];
ON=10; % threshold number of elements for the elimination of a cluster.
OC=0.1; % threshold distance for the union of clusters.
OS=0.2;  % deviation typical threshold for the division of a cluster.
k=4;   % number (maximum) cluster.
L=1;   % maximum number of clusters that can be mixed in a single iteration.
I=100;  % maximum number of iterations allowed.
NO=1;  % extra parameter to automatically answer no to the request of cambial any parameter.
min_dist=10; % Minimum distance a point must be in each center. If you want to delete any point
        % Give a high value.

[Z, Xcluster, A, cluster] = isodata(Xdata, k, L, I, ON, OC, OS, NO, min_dist);

%Presentation of results to the screen.
fprintf('Number of Clusters: %d\n',A);

%compute error
error=classes==cluster;
error(error==0) = [];
error=1-length(error)/150


clear n;clear i;clear p;clear colr;clear NO;