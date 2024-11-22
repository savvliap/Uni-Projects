function mutatedChildren = pid_mutate_permutation(parents ,options,...
    NVARS,FitnessFcn, state, thisScore,thisPopulation,mutationRate) 
% Here we swap two elements of the permutation 
mutatedChildren = cell(length(parents),1);
for i=1:length(parents) 
    %we get every parent 
    parent = thisPopulation{parents(i)};
    %from the parents chromosome we choose two random genes and swap them 
    p = ceil(length(parent) * rand(1,2)); 
    child = parent; 
    child(p(1))= parent(p(2)); 
    child(p(2))= parent(p(1)); 
    mutatedChildren{i} = child;
end