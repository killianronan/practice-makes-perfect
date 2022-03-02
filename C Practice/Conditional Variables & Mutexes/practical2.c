
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include "cond.c"


struct timespec tsP, tsC;
int pnum;			// number updated when producer runs.
int csum;			// sum computed using pnum when consumer runs.
int (*pred) (int);		// predicate indicating number to be consumed
pthread_mutex_t mutex;
pthread_cond_t cond;
int buffer[10];
int size, consumeIndex, produceIndex;

// We want threads to be "working" for less than a second
int work (int ms, struct timespec *ts)
{
  ts->tv_sec = 0;
  ts->tv_nsec = 1000 * ms;
  nanosleep (ts, NULL);
  return 0;
}

int produceT()
{
  pthread_mutex_lock (&mutex);
  while (size > 9)
    {
      pthread_cond_wait (&cond, &mutex);	//mutex is released + reaquired after cond(C) is met
    }
  scanf ("%d", &pnum);		// read a number from stdin
  buffer[produceIndex++] = pnum;
  size++;
  printf ("produceIndex1 : %d", produceIndex);
  produceIndex %= 10;		//keep index 0->10 for buffer access
  printf (" produceIndex2 : %d", produceIndex);
  printf ("\n");
  pthread_mutex_unlock (&mutex);
  pthread_cond_signal (&cond);
  return pnum;
}

void *Produce(void *a)
{
  int p;
  p = 1;
  while (p)
    {
      printf ("@P-WORK\n");
      //work(100,&tsP); // "work" for 100ms
      printf ("@P-READY\n");
      p = produceT ();
      printf ("@PRODUCED %d\n", p);
    }
  printf ("@P-EXIT\n");
  pthread_exit(NULL);
}

int consumeT()
{
  pthread_mutex_lock (&mutex);
  while (size == 0)
    {
      pthread_cond_wait (&cond, &mutex);	//mutex is released reaquired after cond(C) is met
    }
  pnum = buffer[consumeIndex++];
  printf ("consumeIndex1 : %d", consumeIndex);
  consumeIndex %= 10;
  printf (" consumeIndex2 : %d", consumeIndex);
  printf ("\n");
  size--;
  if (pred (pnum))
    {
      csum += pnum;
    }
  pthread_mutex_unlock (&mutex);
  pthread_cond_signal (&cond);
  return pnum;
}

void *Consume(void *a)
{
  int p;
  p = 1;
  while (p)
    {
      printf ("@C-WORK\n");
      // work(50+100*(rand()%3),&tsC); // "work" for 50ms, 150ms, or 250ms
      printf ("@C-READY\n");
      p = consumeT ();
      printf ("@CONSUMED %d\n", csum);
    }
  printf ("@C-EXIT\n");
  pthread_exit (NULL);
}

int main (int argc, const char *argv[])
{
  consumeIndex = 0;
  produceIndex = 0;
  size = 0;
  // the current number predicate
  static pthread_t prod, cons;
  long rc;

  pthread_mutex_init (&mutex, NULL);
  pthread_cond_init (&cond, NULL);
  pred = &cond1;
  if (argc>1) {
    if      (!strncmp(argv[1],"2",10)) { pred = &cond2; }
    else if (!strncmp(argv[1],"3",10)) { pred = &cond3; }
  }


  pnum = 999;
  csum=0;
  srand(time(0));

	if (pthread_mutex_init(&mutex, NULL) != 0) { 
        printf("\n mutex init has failed\n"); 
        return 1; 
    } 

  printf("@P-CREATE\n");
 	rc = pthread_create(&prod,NULL,Produce,(void *)0);
	if (rc) {
			printf("@P-ERROR %ld\n",rc);
			exit(-1);
		}
  printf("@C-CREATE\n");
 	rc = pthread_create(&cons,NULL,Consume,(void *)0);
	if (rc) {
			printf("@C-ERROR %ld\n",rc);
			exit(-1);
		}

  printf("@P-JOIN\n");
  pthread_join( prod, NULL);
  printf("@C-JOIN\n");
  pthread_join( cons, NULL);


  printf("@CSUM=%d.\n",csum);
  pthread_mutex_destroy (&mutex);
  return 0;
}
