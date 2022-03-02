// t2Test.cpp : Defines the entry point for the console application.
//
#include "sample.h"
#include <iostream> // for using std::cout
#include <algorithm> // for using std::max
_int64 fib(_int64 n) {
	if (n <= 1)
		return n;
	else
		return fib(n - 1) + fib(n - 2);
}

//
// check
//
void check(const char* s, _int64 v, _int64 expected) {
	std::cout << s << " = " << v;
	if (v == expected) {
		std::cout << " OK";
	}
	else {
		std::cout << " ERROR: should be " << expected;
	}
	std::cout << "\n";
}

int main()
{
	std::cout << "Fibonacci Series: " << "\n";
	for (int i = -1; i < 20; ++i)
		std::cout << fib(i) << " ";
	std::cout << "\n";

	std::cout << "Fibonacci Series by assembly: " << "\n";
	for (int i = -1; i < 20; ++i)
		std::cout << fibX64(i) << " ";
	std::cout << "\n\n";

	//// use_scanf
	_int64 sum_scanf;
	_int64 sum_check;
	sum_scanf = use_scanf(5, 5, 5);
	sum_check = 21;
	check("use_scanf(1,2,3)", sum_scanf, sum_check);
	std::cout << "\n";
	sum_scanf = use_scanf(-3, 2, -2);
	sum_check = -3 + 2 - 2 + 2;
	check("use_scanf(-3,2,-2)", sum_scanf, sum_check);
	// Code to clear the newline from the buffer and having to wait before exiting
	int c;
	do {
		c = getchar();
	} while (c != '\n' && c != EOF);
	if (c == EOF) {
		// input stream ended, do something about it, exit perhaps
	}
	else {
		printf("Type Enter to continue\n");
		getchar();
	}

	return 0;
}

