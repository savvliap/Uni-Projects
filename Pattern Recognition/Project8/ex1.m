clear 
clc

load fisheriris
class=[3*ones(1,50) ones(1,50) 2*ones(1,50)];
error=0;

rng(1);
[cidx3,cmeans3] = kmeans(meas,3,'dist','sqeuclidean');
[silh2,h] = silhouette(meas,cidx3,'sqeuclidean');

for i=1:length(cidx3)
    if cidx3(i)~=class(i)
        error=error+1/150;
    end
end

error

ptsymb = {'bs','r^','md','go','c+'};
for i = 1:3
    clust = find(cidx3==i);
    plot3(meas(clust,1),meas(clust,2),meas(clust,3),ptsymb{i});
    hold on
end
plot3(cmeans3(:,1),cmeans3(:,2),cmeans3(:,3),'ko')
plot3(cmeans3(:,1),cmeans3(:,2),cmeans3(:,3),'kx')

xlabel('Sepal Length');
ylabel('Sepal Width');
zlabel('Petal Length');
view(-137,10);
grid on

