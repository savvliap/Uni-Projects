function crossoverKids  = cards_crossover_permutation(parents,options,...
    NVARS,FitnessFcn,thisScore,thisPopulation) 

%one child comes from 2 parents so the nuber of children will be 
%half of the selected parents number
nunmberOfKids = length(parents)/2; 
%crossoverKids will be set of new chromosomes (the kids).
crossoverKids = cell(nunmberOfKids,1); 
index = 1; 
 
for i=1:nunmberOfKids 
    %we choose one of every 2 parents
    parent = thisPopulation{parents(index)}; 
    %the index jumps to the next 2 parents and chooses one  
    index = index + 2; 
 
    %we choose 2 genes gene1 and gene2 randomly of the chosen parent
    gene1 = ceil((length(parent) -1) * rand); 
    %we should reserve that the gene 2 will be in greater place of 
    %gene1
    gene2 = gene1 + ceil((length(parent) - gene1- 1) * rand); 
    %we create the new childs choromosome which is identical to the parent
    child = parent; 
    %we perform the flip of the two genes and get the new chromosome 
    child(gene1:gene2) = fliplr(child(gene1:gene2)); 
    %the new child gets stored in the vector with all the new children
    crossoverKids{i} = child; 
end