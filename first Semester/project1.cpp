//57403
#include<iostream>
#include<cstring>

using namespace std;

struct Samurai
{
	char name[40];
	int numberOfCompletedTasks;
	bool experienced;
	char sex;
	bool ninja;
};

void checkExperience(Samurai *samurai)
{
	if (samurai->numberOfCompletedTasks<=15) samurai->experienced==false;
	else samurai->experienced==true ;
}

void showSuitabilityForTask (char sex, bool ninja)
{
	if (sex=='m' && ninja==true ) cout<<"Suitable for sabotage";
	else if (sex=='m' && ninja ==false) cout<<"Suitable for field battle";
	else if(sex=='f' && ninja==true) cout<<"Suitable for infiltration";
	else if (sex=='f' && ninja==false) cout<<"Suitable for diplomacy";
	else if (sex != 'f' && sex != 'm') cout<< "Char not accepted. Accepted are only the values ‘m’ for male and ‘f’ for female."; 	
}

bool showExperiencedSamurai(Samurai samurai)
{
	if (samurai.numberOfCompletedTasks<=15) cout<<"\nInexperienced";
	else cout<<"\nExperienced";
	
}
/*
int main ()
{
	Samurai samurai;
	cout<< "dwse to onoma ";
	cin>>samurai.name;
	
	cout<<"dwse to number of completed tasks ";
	cin>> samurai.numberOfCompletedTasks;
	
	cout<<"dwse to sex ";
	cin>> samurai.sex;
	
	cout<<"einai ninja? 0 oxi 1 nai ";
	cin>> samurai.ninja ;
	
	checkExperience(&samurai);
	showSuitabilityForTask (samurai.sex, samurai.ninja);
	showExperiencedSamurai (samurai);
}*/
