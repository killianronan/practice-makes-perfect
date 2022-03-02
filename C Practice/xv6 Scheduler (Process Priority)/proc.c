// ONLY MODIFY CODE BELOW THE NEXT OCCURENCE OF THE FOLLOWING LINE !
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

#include "types.h"
#include "defs.h" 
#include <stdio.h>
#include "proc.h"
#include <string.h>

#define NCPU 1

struct cpu cpus[NCPU];
int ncpu;

void printstate(enum procstate pstate){ // DO NOT MODIFY
  switch(pstate) {
    case UNUSED   : printf("UNUSED");   break;
    case EMBRYO   : printf("EMBRYO");   break;
    case SLEEPING : printf("SLEEPING"); break;
    case RUNNABLE : printf("RUNNABLE"); break;
    case RUNNING  : printf("RUNNING");  break;
    case ZOMBIE   : printf("ZOMBIE");   break;
    default       : printf("????????");
  }
}

// Per-CPU process scheduler.
// Each CPU calls scheduler() after setting itself up.
// Scheduler never returns.  It loops, doing:
//  - choose a process to run
//  - swtch to start running that process
//  - eventually that process transfers control
//      via swtch back to the scheduler.

// local to scheduler in xv6, but here we need them to persist outside,
// because while xv6 runs scheduler as a "coroutine" via swtch,
// here swtch is just a regular procedure call.
int pix;
struct proc *p;
struct cpu *c = cpus;

// +++++++ ONLY MODIFY BELOW THIS LINE ++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
int procI;
int topPriority;

void scheduler(void) {
  procI = 0;
  topPriority = -1;
  int runnableFound; // DO NOT MODIFY/DELETE
  runnableFound = 1 ; // DO NOT MODIFY/DELETE/BYPASS
  c->proc = 0;
  int offsetCount = 0;
  while(runnableFound){ // DO NOT MODIFY
    runnableFound = 0; // DO NOT MODIFY
    for(pix = 0; pix < NPROC; pix++) {
      p = &ptable.proc[(offsetCount + pix) % NPROC];
      if(p->state != RUNNABLE) continue;
      runnableFound = 1; // DO NOT MODIFY/DELETE/BYPASS
      if (!(topPriority > p->prio|| topPriority<0)) continue;
      procI = (offsetCount + pix) % NPROC;
      topPriority = p->prio;
    }
    topPriority = -1;
    offsetCount = procI+1;
    c->proc = p;
    p->state = RUNNING;
    swtch(p);
    c->proc = 0;
  }
  printf("No RUNNABLE process!\n");
}
