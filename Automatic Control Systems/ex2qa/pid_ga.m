clear;
clc;

%Kd Kp Ki = 3 variables 
numberOfVriables=3;

type pid_create_permutations.m 
type pid_crossover_permutation.m 
type pid_mutate_permutation.m 
type pid_fitness.m  
FitnessFcn = @(x) pid_fitness(x); 
options = gaoptimset(); 
options = gaoptimset(  'PopulationType', 'custom',...
                       'PopInitRange',[1;numberOfVriables], ... 
                       'CreationFcn',@pid_create_permutations, ... 
                       'CrossoverFcn',@pid_crossover_permutation, ... 
                       'MutationFcn',@pid_mutate_permutation, ...  
                       'Generations',10000,'PopulationSize',10000, ... 
                       'StallGenLimit',1000,'Vectorized','on'); 
 
[x,fval,reason,output] = ga(FitnessFcn,numberOfVriables,options)

kd=x{1,1}(1)
kp=x{1,1}(2)
ki=x{1,1}(3)
poles=roots([1 -1731.3709 -298.1192 -6405.4531 -(2064.6455+...
            40395.58932*kd) (515.2034+40395.58932*kp) ki])