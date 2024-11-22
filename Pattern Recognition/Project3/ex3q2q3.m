clear all;
close all;
format long

syms theta
u = 0:0.01:1;
y1 = u/double(int(theta,theta,0,1));
y2 = (u.^3).*((1-u).^2)/double(int(theta^3*(1-theta)^2,theta,0,1));
y3 = (u.^7).*((1-u).^3)/double(int(theta^7*(1-theta)^3,theta,0,1));

hold on;
axis([0 1 0 3]);
plot(u,y1,'r'),xlabel('\Theta'),ylabel('p(\Theta|D^n)');
plot(u,y2,'b')
plot(u,y3,'g')
grid on;
h = legend('p(\Theta|D^1)','p(\Theta|D^5)','p(\Theta|D^10)','Location','Northwest');
set(h,'Interpreter','none')
hold off

[q,w] = max(double(y3));
disp(['The max value for y3 is=' num2str(q)])
disp(['for x=' num2str((w-1)/100)])