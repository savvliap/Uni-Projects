clear;
clc;
N=1000;

counter1=zeros(1,6);
counter2=zeros(1,6);
joint_pdf=zeros(1,6);


die_roll1=0.5+6*rand(1,N);
die_roll2=0.5+6*rand(1,N);

for i=1:N
   if die_roll1(i)<=1.5
        die_roll1(i)=1;
        counter1(1)=counter1(1)+1;
    elseif die_roll1(i)<=2.5
        die_roll1(i)=2;
        counter1(2)=counter1(2)+1;
    elseif die_roll1(i)<=3.5
        die_roll1(i)=3;
        counter1(3)=counter1(3)+1;
    elseif die_roll1(i)<=4.5
        die_roll1(i)=4;
        counter1(4)=counter1(4)+1;
    elseif die_roll1(i)<=5.5
        die_roll1(i)=5;
        counter1(5)=counter1(5)+1;
    elseif die_roll1(i)<=6.5
        die_roll1(i)=6;
        counter1(6)=counter1(6)+1;
   end
   
   if die_roll2(i)<=1.5
        die_roll2(i)=1;
        counter2(1)=counter2(1)+1;
    elseif die_roll2(i)<=2.5
        die_roll2(i)=2;
        counter2(2)=counter2(2)+1;
    elseif die_roll2(i)<=3.5
        die_roll2(i)=3;
        counter2(3)=counter2(3)+1;
    elseif die_roll2(i)<=4.5
        die_roll2(i)=4;
        counter2(4)=counter2(4)+1;
    elseif die_roll2(i)<=5.5
        die_roll2(i)=5;
        counter2(5)=counter2(5)+1;
    elseif die_roll2(i)<=6.5
        die_roll2(i)=6;
        counter2(6)=counter2(6)+1;
    end
end

%pdf for z1
pdf1=counter1/1000;
%pdf for z2
pdf2=counter2/1000;

for i=1:6
    joint_pdf(i)=pdf1(i)*pdf2(i)';
end

%answer to 3a 
joint_pdf
%bar2 need to be commented in order to show the bar1 
bar1=bar(joint_pdf,0.4)


%answer to 3b
y=conv(pdf1,pdf2)
bar2=bar(y,0.4)


