function InitialPopulation = pid_create_permutations(NVARS,FitnessFcn,...
    options) 
%the total population will be determined in the options 
totalPopulationSize = sum(options.PopulationSize); 
%the NVARS will be determinde by the options and is the number of the 
%the variables of the problem (genes)
n=NVARS; 
%the initial population is a set of as many chromosomes as
%the population size 
InitialPopulation = cell(totalPopulationSize,1); 
for i = 1:totalPopulationSize 
    %random values for Kd Kp Ki between -1000 and 1000
    atempt=-1000+(2000).*rand(1,n);
    InitialPopulation{i} = atempt;  
end