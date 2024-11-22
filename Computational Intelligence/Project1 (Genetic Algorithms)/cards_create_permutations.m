function InitialPopulation = cards_create_permutations(NVARS,FitnessFcn,...
    options) 
%the total population will be determined in the options 
totalPopulationSize = sum(options.PopulationSize); 
%the NVARS will be determinde by the options and is the number of the 
%the varieables of the problem (genes)
n=NVARS; 
%the initial population is a set of as many chromosomes as
%the population size 
InitialPopulation = cell(totalPopulationSize,1); 
for i = 1:totalPopulationSize 
    %with the randperm function we ensure that every number 1-15 will be 
    %used one and only time.
    InitialPopulation{i} = randperm(n);  
end