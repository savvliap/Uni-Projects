function err = multidim_err(model1, model2, model3, X, Y)

sc1=predict(model1, X);
sc2=predict(model2, X);
sc3=predict(model3, X);
     
Scores=zeros(size(sc1,1),3);

Scores(:,1)=sc1;
Scores(:,2)=sc2;
Scores(:,3)=sc3;

predicted=max(Scores,[],2);

err=sum(predicted~=Y)/length(Y);

end