function fitnessValue = cards_fitness(x) 

%the fitness Values of each chromosome is contained into the vector fitness
%Value wich is empty in the begining.
fitnessValue=zeros(size(x,1),1);

%the loop runs as many times as the population of the chromosomes
for i=1:size(x,1)
    %we check every chromosome of the population
    CurrentChromosome = x{i};  
    %finding the sum of the first 5, the second 5 and the third 5 genes
    sum49=sum(CurrentChromosome(1:5));
    sum33=sum(CurrentChromosome(6:10));
    prod12600=prod(CurrentChromosome(11:15));

    %cheking how close is the sum of the first 5 numbers to the number 49 
    score1=abs(49-sum49);
    %cheking how close is the sum the second 5 numbers to the number 33
    score2=abs(33-sum33);
    %cheking how close is the product of the last five numbers to the 12600
    score3=abs(12600-prod12600);
    
    %fitness Value of each chromosome is the sum of the 3 scores
    fitnessValue(i)= score1+score2+score3;
end 