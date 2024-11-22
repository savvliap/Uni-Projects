%THIS FILE IS OPTIONAL
card=[1:15];

numberOfVriables=length(card);

type cards_create_permutations.m 
type cards_crossover_permutation.m 
type cards_mutate_permutation.m 
type cards_fitness.m  
FitnessFcn = @(x) cards_fitness(x); 
options = gaoptimset(); 
options = gaoptimset(  'PopulationType', 'custom',...
                       'PopInitRange',[1;numberOfVriables], ... 
                       'CreationFcn',@cards_create_permutations, ... 
                       'CrossoverFcn',@cards_crossover_permutation, ... 
                       'MutationFcn',@cards_mutate_permutation, ...  
                       'Generations',5000,'PopulationSize',60, ... 
                       'StallGenLimit',900,'Vectorized','on'); 
 
[x,fval,reason,output] = ga(FitnessFcn,numberOfVriables,options)