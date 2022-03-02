// t2Test.cpp : Defines the entry point for the console application.
//
#include "sample.h"
#include <iostream> // for using std::cout
#include <algorithm> // for using std::max
#include <time.h> 	

int current_depth;
int proc_calls;
int max_reg_window_depth;
int max_reg_window_underflows;
int max_reg_window_overflows;
int reg_windows;
int active_windows;

void raise_depth(bool active2) {
	if (active2)
		max_reg_window_underflows += 1;
	else
		active_windows -= 1;
	current_depth--;
}
void next_deep() {
	current_depth += 1;
	if (reg_windows == active_windows)
		max_reg_window_overflows += 1;
	else
		active_windows++;
	if (max_reg_window_depth < current_depth)
		max_reg_window_depth = current_depth;
}

int compute_pascal(int row, int position) {
	next_deep();
	if (position == 1) {
		proc_calls = 1;
		raise_depth(active_windows == 2);
		return proc_calls;
	}
	else if (position == row) {
		proc_calls = 1;
		raise_depth(active_windows == 2);
		return proc_calls;
	}
	else {
		proc_calls = compute_pascal(row-1, position) + compute_pascal(row-1, position-1);
		raise_depth(active_windows == 2);
		return proc_calls;
	}

}

int compute_pascal_original(int row, int position) {
	if (position == 1) {
		return 1;
	}
	else if (position == row) {
		return 1;
	}
	else {
		return compute_pascal_original(row - 1, position) + compute_pascal_original(row - 1, position - 1);
	}
}

int main()
{
	std::cout << "Compute pascal: " << "\n";

	//reg_windows = 6;
	current_depth = 0;
	proc_calls = 0;
	max_reg_window_depth = 0;
	max_reg_window_underflows = 0;
	max_reg_window_overflows = 0;
	active_windows = 2;
	reg_windows = 6;
	clock_t timer;
	timer = clock();
	int result = compute_pascal(30, 20);
	timer = clock() - timer;
	double time_taken = ((double)timer) / CLOCKS_PER_SEC; // in seconds 
	printf("reg_windows : %d \n", reg_windows);
	printf("proc_calls : %d \n", result);
	printf("max_reg_window_depth : %d \n", max_reg_window_depth);
	printf("max_reg_window_overflows : %d \n", max_reg_window_overflows);
	printf("max_reg_window_underflows : %d \n", max_reg_window_underflows);
	printf("active_windows : %d \n", active_windows);
	printf("time_taken : %f seconds \n", time_taken);
	printf("\n");

	//reg_windows = 8;
	current_depth = 0;
	proc_calls = 0;
	max_reg_window_depth = 0;
	max_reg_window_underflows = 0;
	max_reg_window_overflows = 0;
	active_windows = 2;
	reg_windows = 8;
	timer = clock();
	result = compute_pascal(30, 20);
	timer = clock() - timer;
	time_taken = ((double)timer) / CLOCKS_PER_SEC; // in seconds 
	printf("reg_windows : %d \n", reg_windows);
	printf("proc_calls : %d \n", result);
	printf("max_reg_window_depth : %d \n", max_reg_window_depth);
	printf("max_reg_window_overflows : %d \n", max_reg_window_overflows);
	printf("max_reg_window_underflows : %d \n", max_reg_window_underflows);
	printf("active_windows : %d \n", active_windows);
	printf("time_taken : %f seconds \n", time_taken);
	printf("\n");

	//reg_windows = 16;
	current_depth = 0;
	proc_calls = 0;
	max_reg_window_depth = 0;
	max_reg_window_underflows = 0;
	max_reg_window_overflows = 0;
	active_windows = 2;
	reg_windows = 16;
	timer = clock();
	result = compute_pascal(30, 20);
	timer = clock() - timer;
	time_taken = ((double)timer) / CLOCKS_PER_SEC; // in seconds 
	printf("reg_windows : %d \n", reg_windows);
	printf("proc_calls : %d \n", result);
	printf("max_reg_window_depth : %d \n", max_reg_window_depth);
	printf("max_reg_window_overflows : %d \n", max_reg_window_overflows);
	printf("max_reg_window_underflows : %d \n", max_reg_window_underflows);
	printf("active_windows : %d \n", active_windows);
	printf("time_taken : %f seconds \n", time_taken);
	printf("\n");

	return 0;
}
