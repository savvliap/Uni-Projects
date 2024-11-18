//57403
# include<iostream>
# include<string>
using namespace std;

class Weapon
{
	protected:
		string weaponName;
		
	public:
		explicit Weapon() {}
		
		string getWeaponName(){return weaponName;}

		void setWeaponName(string k){weaponName=k;}

		~Weapon() {}
};

class ExoticWeapon : public Weapon
{
	private:
		string origin;
		
	public:
		ExoticWeapon() : Weapon(){}
		
		string getOrigin() {return origin;}
		
		void setOrigin(string a) {origin=a;}
};

class Samurai
{
	protected:
	    string name;
		int numberOfWins;
		int numberOfInjuries;  
		int numberOfDuels;
		string samuraiWeapon;
		int age;
		
	public:
		explicit Samurai(){}
    
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
		
		void printSamuraiDescription(Samurai x)
		{
	        cout <<"Samurai name: "<<getName()<<" Duels: "<< getNumberOfDuels()<<" Wins: "<<getNumberOfWins()<<" Injuries: "<<getNumberOfInjuries();
		}
		
		void pickWeapon(Weapon &a)
		{
			if (age<18) samuraiWeapon="Wooden Sword";
	        else samuraiWeapon=a.getWeaponName();
		}
		
		friend void duel(Samurai &a, Samurai &b,bool winner);
		
		friend string duelWithWeapons(Samurai &a, Samurai &b);
	    
		~Samurai() {}
};

class Ninja : public Samurai
{
	private:
		int grade;
	public:
		Ninja() : Samurai() {}
		
		int getGrade(){return grade;}
		
		void setGrade(int k) {grade=k;}
		
		void pickExoticWeapon(ExoticWeapon &a){a.setOrigin(a.getWeaponName());}
};

class NinjaSchool
{
	private:
		Ninja *ninjaClass;
		int numberOfStudents; 
		
	public:
		NinjaSchool()
		{
			ninjaClass= new Ninja[10];
			numberOfStudents=0;
		}
			
		NinjaSchool operator ++() 
		{ 
		    ++numberOfStudents;
		    return *this;
		}
		NinjaSchool operator --() 
		{
		    --numberOfStudents;
		    return *this;
		}
		NinjaSchool operator ++(int notused) 
		{
			NinjaSchool temp= *this;
		    numberOfStudents++;
		    return temp;
		}
		NinjaSchool operator --(int notused) 
		{
			NinjaSchool temp= *this;
		    numberOfStudents--;
		    return temp;
		}
		
		Ninja startTrainingNinja(Ninja &b)
	    {
	    	b.setName(b.getName());
	    	b.setAge(b.getAge());
	    	b.setGrade(0);
		}
		
	     void addNinjaToNinjaClass(Ninja &a)
	    {
			if (a.getAge()<18) cout<<"This class is for grownups!";
			else 
			{
			    *(ninjaClass + numberOfStudents)=  a;
			    numberOfStudents++;
		    }
        }

        friend string brawlWithNinjas(Ninja *ninjaClass);
    	
		~NinjaSchool() {}
} ;

string brawlWithNinjas (Ninja *ninjaClass)
{
	int  bestgrade= ninjaClass->getGrade();
	string name= ninjaClass->getName();
	for(int i=1;i<=9;i++)
	{
		if ((ninjaClass+i)->getGrade() >bestgrade) 
		{
			bestgrade= (ninjaClass+i)->getGrade();
			name= (ninjaClass+i)->getName();
		}
	} 
	return name;
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
	else if((a.samuraiWeapon !="Rock" & a.samuraiWeapon !="Scissors" &a.samuraiWeapon != "Paper")  || (b.samuraiWeapon !="Rock" & b.samuraiWeapon !="Scissors"& b.samuraiWeapon !="Paper"))
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

