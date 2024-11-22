clear
clc
%time counter
tic
%initializing the time scope and the time step
dt=0.001;
%how many searches for K 
iter=15000;
%itterations of the simulation
simiter=50000;
t=0:simiter; 

%k-parameters
k=zeros(1,5);
%performance tracking
bestperf=2e+5;
bestk=k;
bestx=zeros(5,simiter+1);
bestu=zeros(1,simiter);
besty=zeros(1,simiter);
xdot=zeros(1,5);

counter=0;

for j=1:iter
   
    counter=counter+1;
    %initializing the x u y arrays
    x=[0.1*ones(5,1) zeros(5,simiter)];
    u=zeros(1,simiter);
    y=[x(1,1) zeros(1,simiter-1)];
    currentperf=0;
    
    for i=1:simiter
        if (abs(currentperf)>bestperf)
            break
        end
        %computing the control
        u(i)=[k(1) k(2) k(3) k(4) k(5)]*x(:,i);
    
        %computing the x1-x5dot
        xdot=[x(2,i) x(3,i) x(4,i) x(5,i) 0];
        
        %this is my linear system
        xdot(5)=1/1000*(18*(x(5,i)*cos(x(2,i))*log(abs(x(5,i)))+x(3,i)*cos(x(3,i))*cos(x(5,i)))...
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
            +18*(x(3,i)*cos(x(3,i))*cos(x(5,i))+(cos(x(1,i))^2)*log(abs(x(2,i)))))^2)*u(i)); 
        
        %giving the next values for x1-x5
         for z=1:5
             x(z,i+1)=x(z,i)+xdot(z)*dt;
         end


         %computing the next output
         y(i+1)=x(1,i+1);

         currentperf=currentperf + abs(y(i));
    end  
   
    if (abs(currentperf) < bestperf) 
        bestperf=currentperf
        j
        bestk=k;
        bestx=x;
        bestu=u;
        besty=y;
    end
    
    for z=1:5
        k(z)=bestk(z)-1+2*rand;
    end
    
    if counter==1000
        j
        counter=0;
    end
end

bestsol=bestperf
best_controller=bestk'
lasty=besty(simiter)

% simulation_plots( performance,x1,x2,x3,x4,x5,u,y,t)
figure(1);
for z=1:5
    subplot(2,3,z);plot(t,bestx(z,:));title(['x',num2str(z)]);
end
subplot(2,3,6);plot(t(1:simiter),bestu);title('u');
figure(2);plot(t,besty);title('y');
elapsedtime=toc