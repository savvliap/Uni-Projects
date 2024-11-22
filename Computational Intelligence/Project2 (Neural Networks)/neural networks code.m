data= xlsread('neural networks.xlsx')';
SetosaData=data(:,1:50);
VersicolorData= data(:,51:100);
VirginicaData= data(:,101:150);
inputData= [SetosaData(:,1:30),VersicolorData(:,1:30),...
    VirginicaData(:,1:30)];

IrisSetosa=ones(1,50);
IrisVersicolor=2*ones(1,50);
IrisVirginica=3*ones(1,50);
outputData=[IrisSetosa(:,1:30),IrisVersicolor(:,1:30),...
    IrisVirginica(:,1:30)];

PR=minmax(inputData);
net=newff(PR,[70,70,1], {'tansig','tansig','purelin'},'traingd'); 
net.trainParam.show = 50; 
net.trainParam.lr = 0.01; 
net.trainParam.epochs =1000; 
net.trainParam.goal = 1e-5;

trainOutput = train(net, inputData, outputData);

testInputData=[SetosaData(:,31:50),VersicolorData(:,31:50),...
    VirginicaData(:,31:50)];

expectedOutput=[IrisSetosa(:,31:50),IrisVersicolor(:,31:50),...
    IrisVirginica(:,31:50)];

testOutput= sim(trainOutput,testInputData);

MeanSquareError = sum((expectedOutput-testOutput).^2)/(20);
fprintf('Mean square Error = %.4f\n', MeanSquareError)

hold on;
plot(expectedOutput,'-o'); 
plot(testOutput,'-+');
legend(' expected test output','test output')
hold off;