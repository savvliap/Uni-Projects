clear
clc
%time counter
tic

%initializing the time scope and the time step
dt=0.001;
%how many values will I check 
iter=10000;
%itterations of the simulation
simiter=2000;
t=0:simiter; 

pid=-1000+2000*rand(1,3);

bestperf=10000;
bestpid=pid;
bestx=zeros(5,simiter+1);
bestu=zeros(1,simiter);
besty=zeros(1,simiter);

for j=1:iter
    %initializing the x u y arrays
    x=[0.1*ones(5,1) zeros(5,simiter)];
    u=zeros(1,simiter);
    y=[x(1,1) zeros(1,simiter-1)];
    xdot=zeros(1,5);
    %in order to compute ydot
    yprev=0;
    currentperf=0;

    for i=1:simiter
        if (abs(currentperf)>2e+5)
            break
        end
        %computing the x1-x5dot
        xdot=[x(2,i) x(3,i) x(4,i) x(5,i) 0];
        xdot(5)=18*(x(5,i)*cos(x(2,i))*log(abs(x(5,i)))+x(3,i)*cos(x(3,i))...
            *cos(x(5,i)))...
            +7*(x(3,i)*log(abs(x(1,i)))*log(abs(x(5,i))))...
            +16*(x(2,i)*x(5,i)*cos(x(2,i))+x(1,i)*log(abs(x(3,i))))...
            +(x(4,i)*cos(x(1,i))*cos(x(4,i)))...
            +9*(x(4,i)*cos(x(5,i))*log(abs(x(1,i))))...
            +11*(x(2,i)*cos(x(2,i))...
            +(18*(x(2,i)*cos(x(2,i))*cos(x(3,i))+x(3,i)*cos(x(1,i))*log(abs(x(1,i))))...
            +((x(5,i)^2)*cos(x(1,i)))...
            +2*(cos(x(5,i))*log(abs(x(1,i)))*log(abs(x(4,i))))...
            +2*(x(1,i)*x(4,i)*log(abs(x(3,i)))+x(2,i)*x(5,i)*log(abs(x(2,i))))...
            +(cos(x(3,i))*cos(x(4,i))*log(abs(x(5,i))))...
            +18*(x(3,i)*cos(x(3,i))*cos(x(5,i))+(cos(x(1,i))^2)*log(abs(x(2,i)))))^2)*u(i);

        %giving the next values for x1-x5
         for z=1:5
             x(z,i+1)=x(z,i)+xdot(z)*dt;
         end

        %computing the next output
        y(i+1)=x(1,i)+dt*x(1,i);
        ydot=(y(i)-yprev)/dt;
        %computing the control
        u(i)=pid(1)*y(i)+pid(2)*y(i+1)+pid(3)*ydot;
        %the present y will be the previous y for the next loop
        yprev=y(i); 
        
        currentperf=currentperf+abs(y(i));
    end
    if (currentperf<bestpid)
        bestpid=pid
        j
        bestx=zeros(5,simiter+1);
        bestu=zeros(1,simiter);
        besty=zeros(1,simiter);
    end
    pid(1)=bestpid(1)-1+2*rand;
    pid(2)=bestpid(2)-1+2*rand;
    pid(3)=bestpid(3)-1+2*rand;     
end

kp=bestpid(1)
ki=bestpid(2)
kd=bestpid(3)

%making the plots
figure(1)
for z=1:5
    subplot(2,3,z);plot(t,x(z,:));title(['x',num2str(z)]);
end
subplot(2,3,6);plot(t(1:simiter),u);title('u');
figure(2);plot(t,y);title('y');
