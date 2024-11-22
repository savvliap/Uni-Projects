function fitnessValue = pid_fitness(x) 
%the fitness Values of each chromosome is contained into the vector fitness
%Value wich is empty in the begining.
fitnessValue=zeros(size(x,1),1);
%the loop runs as many times as the population of the chromosomes
for i=1:size(x,1)
    %we check every chromosome of the population
    CurrentChromosome = x{i}; 
    %I want to check the roots 
    r=roots([1 -1731.3709 -298.1192 -6405.4531 ...
            -(2064.6455+ 40395.58932*CurrentChromosome(1)) ...
            (515.2043+40395.58932*CurrentChromosome(2)) ...
            40395.58932*CurrentChromosome(3)]);
    %I am only intrested about the real part
    r=r(imag(r)==0);
    maxroot=max(r);
    %the biggest the maxroot the smaller the value of the chromosome 
    fitnessValue(i)=maxroot;
end