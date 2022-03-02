//
// t1Test.cpp
//

#include <iostream>         // std::cout
#include "t1.h"

int  poly(int  arg) { 
	int  res; 
	res = pow(arg, 2); 
	res = res + arg + 1; 
	return (res);
}
int  pow(int  arg0, int  arg1) {
	int  result, i; 
	result = 1;
	for(i = 1;  i <= arg1; i++)
		result = result * arg0; 
	return (result); 
}
int  factorial(int N) { 
	if(N == 0)
		return  1; 
	else 
		return N * factorial(N-1); 
}
//
//Array sum through inline assembly
//
int array_proc_inline(int array[], int n)
{
	_asm {
		mov esi, array	// address of input_array
		mov ecx, n		// input_array size
		mov eax, 0		// clearing the accumulator
		L1 : add eax, [esi]
		add esi, 4
		loop L1
	}
}

//
// Multiple_k
//
void multiple_k(uint16_t N, uint16_t K, uint16_t* array)
{
	for (uint16_t i = 0; i < N; ++i)
	{
		if ((i + 1) % K == 0)
		{
			array[i] = 1;
		}
		else
		{
			array[i] = 0;
		}
	}
}

//
// check
//
void check(char* s, int v, int expected) {
	std::cout << s << " = " << v;
	if (v == expected) {
		std::cout << " OK";
	}
	else {
		std::cout << " ERROR: should be " << expected;
	}
	std::cout << "\n";
}

int main() {
	char str[] = "poly(2)";
	check(str, poly_asm(2), 7);
	char str2[] = "poly(3)";
	check(str2, poly_asm(3), 13);
	char str3[] = "poly(-1)";
	check(str3, poly_asm(-1), 1);
	char str4[] = "poly(-2)";
	check(str4, poly_asm(-2), 3);
	std::cout << "pow result: \n";
	std::cout << pow_asm(4,2) << " \n";


	char str5[] = "factorial(5)";
	check(str5, factorial_asm(5), 120);
	char str6[] = "factorial(4)";
	check(str6, factorial_asm(4), 24);
	char str7[] = "factorial(10)";
	check(str7, factorial_asm(7), 5040);
	char str8[] = "factorial(10)";
	check(str8, factorial_asm(6), 720);

	// Multiple_k evluation
	uint16_t K1 = 3;
	const uint16_t N1 = 10;
	uint16_t array_N1[N1];
	uint16_t array_N2[N1];
	std::cout << "Array of multiples by Assembly: \n";
	multiple_k_asm(N1, K1, array_N1);
	for (uint16_t i = 0; i < N1; ++i)
		std::cout << array_N1[i] << " ";

	std::cout << "\n";

	std::cout << "Array of multiples by C++: \n";
	multiple_k(N1, K1, array_N2);
	for (uint16_t i = 0; i < N1; ++i)
		std::cout << array_N2[i] << " ";

	std::cout << "\n";

	uint16_t K2 = 7;
	const uint16_t N2 = 50;
	uint16_t array_N3[N2];
	uint16_t array_N4[N2];
	std::cout << "Array of multiples by Assembly: \n";
	//multiple_k_asm(N2, K2, array_N3);
	for (uint16_t i = 0; i < N2; ++i)
		std::cout << array_N3[i] << " ";

	std::cout << "\n";

	std::cout << "Array of multiples by C++: \n";
	multiple_k(N2, K2, array_N4);
	for (uint16_t i = 0; i < N2; ++i)
		std::cout << array_N4[i] << " ";

	std::cout << "\n";

	// Sum of array through assembly
	int array[5] = { 1,2,3,4,5 };
	int array_size = 5;
	std::cout << "Array: ";
	for (int i = 0; i < array_size; i++)
		std::cout << array[i] << " ";

	std::cout << "\n";

	std::cout << "Sum: " << array_proc(array, array_size) << "\n";
	std::cout << "Sum with inline assembly: " << array_proc_inline(array, array_size) << "\n";


	getchar();

	return 0;
}
