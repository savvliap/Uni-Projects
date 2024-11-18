//57403
# include<iostream>
# include<string>
using namespace std;

class Weapon
{
	private:
		string weaponName;
		
	public:
		Weapon(string a)
		{
			setWeaponName(a);
			cout<<"Weapon created!";
		}
		
		string getWeaponName(){return weaponName;}

		void setWeaponName(string k){weaponName=k;}

		~Weapon(){"Weapon destroyed!";}
};

class Samurai
{
	private:
	    string name;
		int numberOfWins;
		int numberOfInjuries;  
		int numberOfDuels;
		string samuraiWeapon;
		int age;
		
	public:
		Samurai( string s, int a, int m, int u, int r);
	
		string getName() {return name;}
		int getNumberOfWins() {return numberOfWins;}
		int getNumberOfInjuries() {return numberOfInjuries;}
		int getNumberOfDuels() {return numberOfDuels;}
		string getSamuraiWeapon() {return samuraiWeapon;}
		int getAge() {return age;}
	
		void setName(string s) {name=s;}
		void setNumberOfWins(int a) {numberOfWins=a;}
		void setNumberOfInjuries(int v) {numberOfInjuries=v;}
		void setNumberOfDuels(int v) {numberOfDuels=v;}
		void setSamuraiWeapon(string a) {samuraiWeapon=a;}
		void setAge(int s) {age=s;}
		
		void printSamuraiDescription(Samurai x);
		
		void pickWeapon(Weapon &a);
		
		friend void duel(Samurai &a, Samurai &b,bool winner);
		
		friend string duelWithWeapons(Samurai &a, Samurai &b);
	    
		~Samurai() {cout<< "Samurai "<<getName()<<" deleted. ";} 
};

Samurai :: Samurai( string s, int a, int m, int u, int r)
{
	setName(s);
	setNumberOfWins(a);
	setNumberOfInjuries(m);
    setNumberOfDuels(u);
    setAge(r);
    
    samuraiWeapon="no weapon";
    
    cout<<"Samurai ready for duel. ";
}

void Samurai :: printSamuraiDescription(Samurai x)
{
	cout <<"Samurai name: "<<getName()<<" Duels: "<< getNumberOfDuels()<<" Wins: "<<getNumberOfWins()<<" Injuries: "<<getNumberOfInjuries();
}

void Samurai :: pickWeapon(Weapon &a)
{
	if (age<18) samuraiWeapon="Wooden Sword";
	else samuraiWeapon=a.getWeaponName();
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

string duelForYoungSamurais(Samurai &a,Samurai &b)
{
	if (a.getAge()>18 || b.getAge()>18) cout<<"Invalid duel!";
	else if (a.getSamuraiWeapon()=="Wooden Sword" & b.getSamuraiWeapon()=="Wooden Sword") cout<<a.getName()<<" duels "<<b.getName()<<"!";
	else cout<<"Duel postponed!";
}

string duelWithWeapons(Samurai &a, Samurai &b)
{
	if (a.age<18 || b.age<18) cout<<"this duels is for adults only!";
	else if((a.samuraiWeapon !="Rock" & a.samuraiWeapon !="Scissors" & a.samuraiWeapon != "Paper" ) || (b.samuraiWeapon !="Rock" & b.samuraiWeapon !="Scissors" & b.samuraiWeapon !="Paper"))
	{
		cout<<"Strange Duel!";
	}
	else
	{
		if (a.samuraiWeapon==b.samuraiWeapon) cout<<"the duel is draw!";
		else if (a.samuraiWeapon=="Rock")
		{
			if (b.samuraiWeapon=="Scissors") cout<< a.name;
			if (b.samuraiWeapon=="Paper") cout<<b.name;
		}
		else if (a.samuraiWeapon=="Scissors")
		{
			if (b.samuraiWeapon=="Paper") cout<<a.name;
			if (b.samuraiWeapon=="Rock") cout<<b.name;
		}
		else
		{
			if (b.samuraiWeapon=="Rock") cout<<a.name;
			if (b.samuraiWeapon=="Scissors") cout<<b.name;
		}
    }
}

	int main()
{
	Samurai a("giannis", 20, 10, 10,19);
	Samurai b("giwrgis",1 ,2 ,3,20);
	
	Weapon r("Rock");
	Weapon k("Paper");
	
	a.pickWeapon(r);
	b.pickWeapon(k);
	
	duelForYoungSamurais(a , b);
	//duelWithWeapons(a, b);
}
