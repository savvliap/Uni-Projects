//57403
# include<iostream>
# include<string>
using namespace std;


class Samurai
{
	private:
	    string name;
		int numberOfWins;
		int numberOfInjuries;  
		int numberOfDuels;
		
	public:
		Samurai( string s, int a, int v, int e);
	
		string getName() {return name;}
		int getNumberOfWins() {return numberOfWins;}
		int getNumberOfInjuries() {return numberOfInjuries;}
		int getNumberOfDuels() {return numberOfDuels;}
	
		void setName(string m) {name=m;}
		void setNumberOfWins(int i) {numberOfWins=i;}
		void setNumberOfInjuries(int l) {numberOfInjuries=l;}
		void setNumberOfDuels(int o) {numberOfDuels=o;}
		
		void printSamuraiDescription();
		
		friend void duel(Samurai &a, Samurai &b,bool winner);
	    
		~Samurai() {cout<< "Samurai "<<getName()<<" deleted ";} 
};

Samurai :: Samurai( string s, int a, int v, int e)
{
	setName(s);
	setNumberOfWins(a);
	setNumberOfInjuries(v);
    setNumberOfDuels(e);
    cout<<"Samurai ready for duel ";
}

void Samurai :: printSamuraiDescription()
{
	cout <<"Samurai name: "<<getName()<<" Duels: "<< getNumberOfDuels()<<" Wins: "<<getNumberOfWins()<<" Injuries: "<<getNumberOfInjuries();
}

 void duel(Samurai &a, Samurai &b, bool winner)
{
	a.numberOfDuels=++a.numberOfDuels;
	b.numberOfDuels=++b.numberOfDuels;

	a.numberOfWins= winner==1? ++a.numberOfWins:a.numberOfWins;
	b.numberOfInjuries= winner==1? ++b.numberOfInjuries:b.numberOfInjuries;
	b.numberOfWins= winner==0? ++b.numberOfWins:b.numberOfWins;
	a.numberOfInjuries=winner==0? ++a.numberOfInjuries:a.numberOfInjuries;	

	if (a.numberOfWins>10) cout<<"Samurai "<<a.name<<" is duelist";
	if (b.numberOfWins>10) cout<<"Samurai "<<b.name<<" is duelist";
}

/*int main()
{
	Samurai a("giannis", 20, 10, 10);
	Samurai b("giwrgis",1 ,2 ,3);
	bool winner=1;
	
	a.printSamuraiDescription();
	b.printSamuraiDescription();
	duel(a, b, winner);	
	a.printSamuraiDescription();
	b.printSamuraiDescription();
}*/
