#define NO_OF_STONES 5
mtype stones[NO_OF_STONES];
mtype = { FROG1, FROG2, FROG3, FROG4, EMPTY }
active proctype printState(){
	int i;
	for (i : 0 .. 4){
		if
		:: (stones[i]==EMPTY) -> printf(" EMPTY %d",i);
		:: (stones[i]==FROG1) -> printf(", FROG1 AT %d",i );
		:: (stones[i]==FROG2) -> printf(", FROG2 AT %d",i );
		:: (stones[i]==FROG3) -> printf(", FROG3 AT %d",i );
		:: (stones[i]==FROG4) -> printf(", FROG4 AT %d",i );
		fi
	}
}
active proctype frogOne (int i) {
end:do
	:: atomic{//check for one space jump
			(i<=NO_OF_STONES-2) && 
			(stones[i+1]==EMPTY) -> 
			printf("FROG1 FROM %d",i);
			printf("TO %d",i+1);
			stones[i] = EMPTY; 
			stones[i+1] = FROG1;
			run printState();
		}
		i++;
	:: atomic {//check for two space jump
			(i <= NO_OF_STONES-3) && 
			(stones[i+2] == EMPTY) &&
			(stones[i+1] != EMPTY)  -> 
			printf("FROG1 FROM %d",i);
			printf("TO %d",i+2);
			stones[i] = EMPTY; 
			stones[i+2] = FROG1;
			run printState();
		}
		i = i + 2;
	od
}
active proctype frogTwo (int i) {
end:do
	:: atomic{
			(i <= NO_OF_STONES-2) && 
			(stones[i+1] == EMPTY) -> 
			printf("FROG2 FROM %d",i);
			printf("TO %d",i+1);
			stones[i+1] = FROG2;
			stones[i] = EMPTY; 
			run printState();
		}
		i++;
	:: atomic {
			(i <= NO_OF_STONES-3) && 
			(stones[i+2] == EMPTY) &&
			(stones[i+1] != EMPTY)  -> 
			printf("FROG2 FROM %d",i);
			printf("TO %d",i+2);
			stones[i+2] = FROG2;
			stones[i] = EMPTY; 
			run printState();
		}
		i = i + 2;

	od
}
active proctype frogThree (int i) {
end:do
	:: atomic {
			(i >= 1) && 
			(stones[i-1] == EMPTY) -> 
			printf("FROG3 FROM %d",i);
			printf("TO %d",i-1);
			stones[i-1] = FROG3;
			stones[i] = EMPTY;			
			run printState();
		}
		i--;
	:: atomic {
			(i >= 2) && 
			(stones[i-2] == EMPTY) &&
			(stones[i-1] != EMPTY)  ->
			printf("FROG3 FROM %d",i);
			printf("TO %d",i-2);
			stones[i-2] = FROG3;
			stones[i] = EMPTY; 
			run printState();
		}
		i = i - 2;
	od
}
active proctype frogFour (int i) {
end:do
	:: atomic {
			(i >= 1) && 
			(stones[i-1] == EMPTY) -> 
			printf("FROG4 FROM %d",i);
			printf("TO %d",i-1);
			stones[i-1] = FROG4;
			stones[i] = EMPTY; 
			run printState();
		}
		i--;

	:: atomic {
			(i >= 2) && 
			(stones[i-2] == EMPTY) &&
			(stones[i-1] != EMPTY)  ->
			printf("FROG4 FROM %d",i);
			printf("TO %d",i-2); 
			stones[i-2] = FROG4;
			stones[i] = EMPTY; 
			run printState();
		}
		i = i - 2;
	od
}
#define correct (\
	(stones[0]==FROG4) && \
	(stones[1]==FROG3) && \
	(stones[3]==FROG2) && \
	(stones[4]==FROG1) && \
	)
init {
	atomic {
		int run2;
		stones[NO_OF_STONES/2] = EMPTY;//initialize empty stone
		stones[0] = FROG1;
		stones[1] = FROG2;
		stones[3] = FROG3;
		stones[4] = FROG4;
		int i = 0;
        do
        :: i == NO_OF_STONES/2 -> break;
   		:: else -> 
			 run2 = run frogOne(i);
			 run2 = run frogTwo(i);
			 run2 = run frogThree(NO_OF_STONES-i-1);
			 run2 = run frogFour(NO_OF_STONES-i-1);
             i++;
        od
	}
}