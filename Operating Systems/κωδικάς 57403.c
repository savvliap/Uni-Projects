#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>

#define NUM_VISITORS 30
#define NUM_GUIDES 5
#define NUM_INSPECTORS 2
#define R 5

sem_t VisitorAccess;
sem_t GuideAccess;
sem_t InspectorAccess;

int VisitorsInSite=0;
int GuidesInSite=0;

void go_home()
{
	pthread_exit(NULL);
}

////////////////////////////////////////////////////////////////////////////
void guide_enter()
{
	sem_wait(&GuideAccess);
	printf("A new guide has entered the site\n");
	GuidesInSite ++; 
	for (int i=0; i<R; i++)
	{
		sem_post(&VisitorAccess);
	}
}

void guide()
{
	printf("I am guiding the visitors in the archaelogical site\n");
	sleep(10);
}

void guide_exit()
{
	printf("I want to exit the archaelogical site\n");
	if ((VisitorsInSite / (GuidesInSite --))>=R)
	{
		printf("there are enough guides for the visitors already , I am going to exit\n");	
	    sem_post(&GuideAccess);
	    GuidesInSite--;
	}
	else
	{
		printf("i cannot exit. I am returning to guide...\n");
		guide();
	}
}


void * Guide(void * arg)
{
	guide_enter();
    guide();
    guide_exit();
    go_home();
}

//////////////////////////////////////////////////////////////////////

void visitor_enter()
{
	if (((VisitorsInSite ++)/GuidesInSite)<=R)
	{
		sem_wait(&VisitorAccess);
	    printf("A new visitor entered the site\n");
		VisitorsInSite++; 
	}
	else
	{
		printf("I cant enter yet\n");
		visitor_enter();
	}
}

void learn()
{
	printf("I am learning things in the archaelogical site\n");
	sleep(10);
}

void visitor_exit()
{
	printf("I learnt everything. Now I can exit the site\n");
}


void * Visitor(void * arg)
{
	
	visitor_enter();    
	learn();
	visitor_exit();
	go_home();
}
/////////////////////////////////////////////////////////////////

void inspector_enter()
{
	sem_wait(&InspectorAccess);
	printf("I am entering the site\n");
}


void verify_compiance()
{
	if((VisitorsInSite/GuidesInSite)<=R)
	{
		printf("everything is alright");
	}
	else
	{
		printf("there is a problem over there\n");
	}
}

void inspector_exit()
{
	printf("I am exiting\n");
	sem_post(&InspectorAccess);
}


void * Inspector(void * arg)
{
	inspector_enter();
	verify_compiance();
	inspector_exit();
	go_home();
}
////////////////////////////////////////////////////////////////

void main()
{
	

	// ftiaxnw tis semaphorous 
	sem_init(&VisitorAccess,0, NUM_VISITORS);
	sem_init(&GuideAccess, 0, NUM_GUIDES);
	sem_init(&InspectorAccess, 0, NUM_INSPECTORS);

	//ftiaxnw ta nhmata 
	pthread_t Guide[NUM_GUIDES];
	pthread_t Visitor[NUM_VISITORS];
	pthread_t Inspector[NUM_INSPECTORS];

	for (int i=0; i<NUM_GUIDES; i++)
	{
		pthread_create(&Guide[i], NULL, Guide, NULL);
		printf("a guide is created\n");
	}
    
	for(int i=0; i<NUM_VISITORS; i++)
	{
		pthread_create(&Visitor[i], NULL, Visitor, NULL);
		printf("a visitor is created\n");
	}

	for (int i=0 ; i<NUM_INSPECTORS; i++)
	{
		pthread_create(&Inspector[i], NULL, Inspector, NULL);
		printf("an inspector is created\n");
	}
}