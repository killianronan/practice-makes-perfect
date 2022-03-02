/*
 * Concurrent Systems I Lab: The Travelling Salesperson
 * Team: Thomas Dixon 17341291, Killian Ronan 18328687, David Nugent 18328560
 * 
 * Compile command: gcc mytour.c sales.c -o main -lm -fopenmp
 * Note: we did not make any changes to the original sales.c file
 * 
 * Specification:
 * We took the simple_find_tour function that was provided and tried to optimize
 * each section of the code.
 * 
 * We made improvements algorithmically and using parallelization & vectorization
 *
 * Algorithm Optimization:
 * 	In the provided simple_tour function, the 'i' for loop and the 'j' for
 * 	loop both make a full pass through the array. This leads to N^2 total
 * 	iterations of the inner 'j' loop.
 * 	We do not need to always check if every city has been visited. If we keep
 * 	track of which cities remain unvisited, the inner 'j' loop only needs to
 * 	make an iteration for each unvisited city. This results in our code only
 * 	making N*(N/2) total iterations of the 'j' loop.
 *
 * Parallelization:
 *  * 	We used an OpenMP parallel-for on the inner 'j' loop to split the running
 * 	of the for loop between each processor. We only use the parallelization if
 * 	the number of unvisited cities is above a certain threshold (found
 * 	empirically).
 *  *	We also parallelize the initialization of our unvisited cities array.
 *  *	We used critical blocks to ensure variable integrity.
 *
 * Vectorization:
 * 	Instead of calculating the distance betweeen the current point and the
 * 	unvisited points one-by-one, we do them 4 at a time by using 128 bit
 * 	SSE vector arithmetic. We then find the minimum of these 4 distances and
 * 	compare it to the current minimum.
 *
 */

#include <alloca.h>
#include <stdio.h>
#include <math.h>
#include <float.h>
#include <stdlib.h>
#include <sys/time.h>
#include <string.h>
#include <time.h>
#include "mytour.h"
#include "xmmintrin.h"

// parallelization threshold
const int THRESHOLD_1 = 100000;
const int THRESHOLD_2 = 18000;

// functions provided in original code
float square(float x)
{
	return x * x;
}
float distance(const point cities[], int i, int j)
{
	return sqrt(square(cities[i].x - cities[j].x) +
				square(cities[i].y - cities[j].y));
}

// helper function for timing sections of the code
void print_time(char *msg, struct timeval start, struct timeval stop,
				long long compute)
{
	compute = (stop.tv_sec - start.tv_sec) * 1000000L +
			  (stop.tv_usec - start.tv_usec);
	printf("Time to %s: %lld microseconds\n", msg, compute);
}

void my_tour(const point cities[], int tour[], int ncities)
{
	int i, j;
	// variables used for timing of blocks
	struct timeval start_time, stop_time;
	long long compute_time;

	// variables shared by all threads
	int this_pt, final_close_pt = 0;
	float final_close_dist;
	int endtour = 0;
	this_pt = ncities - 1;
	tour[endtour++] = ncities - 1;

	gettimeofday(&start_time, NULL);
	// list of unvisited cities (indexes of the cities[] array)
	int *unvisited = malloc(sizeof(int) * ncities - 1);
	#pragma omp parallel for if (ncities >= THRESHOLD_1)
	for (i = 0; i < ncities - 1; i++)
	{
		unvisited[i] = i;
	}
	int num_unvisited = ncities - 1;
	gettimeofday(&stop_time, NULL);
	print_time("time to set unvisited array", start_time, stop_time, compute_time);

	gettimeofday(&start_time, NULL);
	for (i = 1; i < ncities; i++)
	{
		// variables shared by threads in j for-loop
		int min_city = -1;
		int unvisited_idx = -1;	// used in removal of visited city from unvisited array
		float min_dist = DBL_MAX;
		// vector where every element is the x component of the current city
		__m128 curr_xs = _mm_set1_ps(cities[this_pt].x);
		// vector where every element is the y component of the current city
		__m128 curr_ys = _mm_set1_ps(cities[this_pt].y);
		#pragma omp parallel if (ncities >= THRESHOLD_2)
		{
			// thread-local variables
			int num_next_cities = 0;
			int temp_min_city = -1;
			int temp_unvisited_idx = -1;
			float temp_min_dist = DBL_MAX;
			int *next_cities = malloc(sizeof(int) * 4); // used in vectorization
			int *unvisited_idxs = malloc(sizeof(int) * 4); // tracks corresponding idxs
			float *dist_arr = malloc(sizeof(float) * 4);
			
			#pragma omp for
			for (j = 0; j < num_unvisited; j++)
			{
				// fill array of length 4 to be used in vectorization
				next_cities[num_next_cities] = unvisited[j];
				unvisited_idxs[num_next_cities] = j;
				num_next_cities++;
				// if there are 4 cities in the array, do vectorized distance computation
				if (num_next_cities == 4)
				{
					// vector of the x-components of the next four cities
					__m128 next_xs = _mm_setr_ps(cities[next_cities[0]].x,
												 cities[next_cities[1]].x,
												 cities[next_cities[2]].x,
												 cities[next_cities[3]].x);
					// vector of the y-components of the next four cities
					__m128 next_ys = _mm_setr_ps(cities[next_cities[0]].y,
												 cities[next_cities[1]].y,
												 cities[next_cities[2]].y,
												 cities[next_cities[3]].y);
					// vectorized computation of:
					// sqrt(square(curr_city.x-next_city.x)+ square(curr_city.y-next_city.y))
					__m128 x_sub = _mm_sub_ps(curr_xs, next_xs);
					__m128 y_sub = _mm_sub_ps(curr_ys, next_ys);
					__m128 x_square = _mm_mul_ps(x_sub, x_sub);
					__m128 y_square = _mm_mul_ps(y_sub, y_sub);
					__m128 sum_squares = _mm_add_ps(x_square, y_square);
					__m128 dist_vec = _mm_sqrt_ps(sum_squares);
					// store vector back into 4 element array
					_mm_store_ps(dist_arr, dist_vec);
					// check if any of the cities are the smallest distance away
					for (int k = 0; k < 4; k++)
					{
						if (dist_arr[k] < temp_min_dist)
						{
							temp_min_dist = dist_arr[k];
							temp_min_city = next_cities[k];
							temp_unvisited_idx = unvisited_idxs[k];
						}
					}
					// reset counter of cities in array
					num_next_cities = 0;
				}
			}
			// handle extra cities for case when num non-visited cities does not divide 4
			if (num_next_cities > 0)
			{
				for (int k = 0; k < num_next_cities; k++)
				{
					// use non-vectorized distance function provided in example code
					float dist = distance(cities, this_pt, next_cities[k]);
					if (dist < temp_min_dist)
					{
						temp_min_dist = dist;
						temp_min_city = next_cities[k];
						temp_unvisited_idx = unvisited_idxs[k];
					}
				}
			}
			// use critical block to update shared variables for the closest city
			#pragma omp critical
			{
				if (temp_min_dist < min_dist)
				{
					min_city = temp_min_city;
					min_dist = temp_min_dist;
					unvisited_idx = temp_unvisited_idx;
				}
			}
			free(dist_arr);
			free(next_cities);
			free(unvisited_idxs);
		}
		tour[endtour++] = min_city;
		this_pt = min_city;
		// the next two lines 'remove' the visited city from the unvisited array
		unvisited[unvisited_idx] = unvisited[num_unvisited - 1];
		num_unvisited--;
	}
	gettimeofday(&stop_time, NULL);
	print_time("time to run main for", start_time, stop_time, compute_time);
	free(unvisited);
}
