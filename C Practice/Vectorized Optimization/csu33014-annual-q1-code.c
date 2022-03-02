//
// CSU33014 Annual Exam, May 2021
// Question 1
//

// Please examine version each of the following routines with names
// starting 'routine_'. Where the routine can be vectorized, please
// replace the corresponding 'vectorized' version using SSE vector
// intrinsics. Where it cannot be vectorized please explain why.

// To illustrate what you need to do, routine_0 contains a
// non-vectorized piece of code, and vectorized_0 shows a
// corresponding vectorized version of the same code.

// Note that to simplify testing, I have put a copy of the original
// non-vectorized code in the vectorized version of the code for
// routines 1 to 6. This allows you to easily see what the output of
// the program looks like when the original and vectorized version of
// the code produce equivalent output.

// Note the restrict qualifier in C indicates that "only the pointer
// itself or a value directly derived from it (such as pointer + 1)
// will be used to access the object to which it points".


#include <immintrin.h>
#include <stdio.h>
#include "csu33014-annual-q1-code.h"

/****************  routine 0 *******************/

// Here is an example routine that should be vectorized
void routine_0(float * restrict a, float * restrict b,
		    float * restrict c) {
  for (int i = 0; i < 1024; i++ ) {
    a[i] = b[i] * c[i];
  }
}

// here is a vectorized solution for the example above
void vectorized_0(float * restrict a, float * restrict b,
		    float * restrict c) {
  __m128 a4, b4, c4;
  
  for (int i = 0; i < 1024; i = i+4 ) {
    b4 = _mm_loadu_ps(&b[i]);
    c4 = _mm_loadu_ps(&c[i]);
    a4 = _mm_mul_ps(b4, c4);
    _mm_storeu_ps(&a[i], a4);
  }
}

/***************** routine 1 *********************/

// in the following, size can have any positive value
float routine_1(float * restrict a, float * restrict b,
		int size) {
  float sum_a = 0.0;
  float sum_b = 0.0;
  
  for ( int i = 0; i < size; i++ ) {
    sum_a = sum_a + a[i];
    sum_b = sum_b + b[i];
  }
  return sum_a * sum_b;
}

// in the following, size can have any positive value
float vectorized_1(float * restrict a, float * restrict b,
		   int size) {
  // use malloc for output
  float * sum_a = malloc(sizeof(float)*4);       
  float * sum_b = malloc(sizeof(float)*4);
  __m128 aVec, bVec, sumAVec, sumBVec;
  //initialize sums to 0
  sumAVec = _mm_set1_ps(0.0f);
  sumBVec = _mm_set1_ps(0.0f);
  int remainder = size%4;
  int i;
  for ( int i = 0; i < size-remainder; i+=4 ) {
    // Load vectors into float arrays
    aVec = _mm_loadu_ps(&a[i]);
    bVec = _mm_loadu_ps(&b[i]);
    // Add values
    sumAVec = _mm_add_ps(sumAVec, aVec);
    sumBVec = _mm_add_ps(sumBVec, bVec);
  }
  //store sums
  _mm_storeu_ps(sum_a, sumAVec);
  _mm_storeu_ps(sum_b, sumBVec);
  // add final sums
  float sum_a_final = sum_a[0]+sum_a[1]+sum_a[2]+sum_a[3];
  float sum_b_final = sum_b[0]+sum_b[1]+sum_b[2]+sum_b[3];
  //Cover remaining values
  for ( i = size-remainder; i < size; i++ ) {
    sum_a_final = sum_a_final + a[i];
    sum_b_final = sum_b_final + b[i];
  }
  //free allocated mem
  free(sum_a);
  free(sum_b);
  //multiply for result
  return sum_a_final * sum_b_final;
}
/******************* routine 2 ***********************/

// in the following, size can have any positive value
void routine_2(float * restrict a, float * restrict b, int size) {
  for ( int i = 0; i < size; i++ ) {
    a[i] = 1.5379 - (1.0/b[i]);
  }
}

void vectorized_2(float * restrict a, float * restrict b, int size) {
  // replace the following code with vectorized code
  float decimal = 1.5379;
  float one = 1;
  __m128 aVec, bVec, decimalVec, oneVec;
  //load 1.5379 vec
  decimalVec = _mm_load1_ps(&decimal);
  // load 1 vec
  oneVec = _mm_load1_ps(&one);
  int remainder = size%4;
  int i;
  for (i = 0; i < size-remainder; i+=4 ) {
    //b[i]
    bVec = _mm_loadu_ps(&b[i]);
    //(1.0/b[i])
    bVec = _mm_div_ps(oneVec,bVec);
    // a[i] = 1.5379 - (1.0/b[i])
    aVec = _mm_sub_ps(decimalVec,bVec);
    // store aVec
    _mm_storeu_ps(&a[i],aVec);
  }
  //Cover remiaining values
  for (i = size-remainder; i < size; i++ ) {
    a[i] = 1.5379 - (1.0/b[i]);
  }
}

/******************** routine 3 ************************/

// in the following, size can have any positive value
void routine_3(float * restrict a, float * restrict b, int size) {
  for ( int i = 0; i < size; i++ ) {
    if ( a[i] < b[i] ) {
      a[i] = b[i];
    }
  }
}

void vectorized_3(float * restrict a, float * restrict b, int size) {
  __m128 aVec, bVec, cmpVec;
  int remainder = size%4;
  int i;
  for (i = 0; i < size-remainder; i+=4 ) {
    //load a and b vectors
    aVec = _mm_loadu_ps(&a[i]);
    bVec = _mm_loadu_ps(&b[i]);
    // a[i] < b[i], a[i+1] < b[i+1],...
    // if values true store b[i] to a[i]
    cmpVec = _mm_cmplt_ps(aVec,bVec);
    // AND bVec and cmpVec where a[i] < b[i]
    bVec = _mm_and_ps(bVec,cmpVec);
    // a[i] >= b[i]
    aVec = _mm_andnot_ps(cmpVec, aVec);
    // Extract result using or and store
    _mm_storeu_ps(&a[i], _mm_or_ps(aVec, bVec));
 }
  //Cover remiaining values
  for (i = size-remainder; i < size; i++ ) {
    if ( a[i] < b[i] ) {
      a[i] = b[i];
    }
  }
}

/********************* routine 4 ***********************/

// hint: one way to vectorize the following code might use
// vector shuffle operations
void routine_4(float * restrict a, float * restrict b,
		 float * restrict c) {
  for ( int i = 0; i < 2048; i = i+2  ) {
    a[i] = b[i]*c[i+1] + b[i+1]*c[i];
    a[i+1] = b[i]*c[i] - b[i+1]*c[i+1];
  }
}

void vectorized_4(float * restrict a, float * restrict b,
		    float * restrict  c) {
  __m128 aVec, bVec, cVec, mul1, mul2;
  for(int i = 0; i < 2048; i+=4) {
    //load b and c vectors
    bVec = _mm_loadu_ps(&b[i]);
    cVec = _mm_loadu_ps(&c[i]);
    //vector b*c
    mul1 = _mm_mul_ps(bVec, cVec);
    //horizontal subtraction on adjacent pairs
    //b[i]*c[i] - b[i+1]*c[i+1];
    mul1 = _mm_hsub_ps(mul1, mul1);
    // extract values for c
    cVec = _mm_shuffle_ps(cVec, cVec, _MM_SHUFFLE(2,3,0,1));
    // b[i]*c[i+1]
    mul2 = _mm_mul_ps(bVec, cVec);
    // b[i]*c[i+1] + b[i+1]*c[i]
    mul2 = _mm_hadd_ps(mul2, mul2);
    //extract result values
    aVec = _mm_shuffle_ps(mul2, mul1, _MM_SHUFFLE(3,2,3,2)); 
    //extract a vector result
    aVec = _mm_shuffle_ps(aVec, aVec, _MM_SHUFFLE(3,1,2,0));
    //store result
    _mm_storeu_ps(&a[i], aVec);
  }
}

/********************* routine 5 ***********************/

// in the following, size can have any positive value
int routine_5(unsigned char * restrict a,
	      unsigned char * restrict b, int size) {
  for ( int i = 0; i < size; i++ ) {
    if ( a[i] != b[i] )
      return 0;
  }
  return 1;
}

int vectorized_5(unsigned char * restrict a,
		 unsigned char * restrict b, int size) {
  // use int instead of float
  __m128i aVec, bVec;
  int remainder = size%16;
  int i;
  for ( i = 0; i < size-remainder; i+=16 ) {
    //load integer vectors (1 character = 1 byte, 1 int = 4 bytes)
    aVec = _mm_lddqu_si128((const __m128i*)&a[i]);
    bVec = _mm_lddqu_si128((const __m128i*)&b[i]);
    // test to see if not equal
    // bitwise XOR on 128 bits
    __m128i notEq = _mm_xor_si128(aVec,bVec);
    //check if equal
    if(_mm_test_all_zeros(notEq,notEq)) {
        //If equal do nothing
    } else {//else return 0
      return 0;
    }
  }
  //Cover remaining values
  for ( i = size-remainder; i < size; i++ ) {
    if ( a[i] != b[i] )
      return 0;
  }
  //return 1 if never equal
  return 1;
}

/********************* routine 6 ***********************/

void routine_6(float * restrict a, float * restrict b,
		       float * restrict c) {
  a[0] = 0.0;
  for ( int i = 1; i < 1023; i++ ) {
    float sum = 0.0;
    for ( int j = 0; j < 3; j++ ) {
      sum = sum +  b[i+j-1] * c[j];
    }
    a[i] = sum;
  }
  a[1023] = 0.0;
}

void vectorized_6(float * restrict a, float * restrict b,
		       float * restrict c) {
  a[0] = 0.0;
  int size = 1023;
  int remainder = size % 4;
  __m128 bVec, cVec, sumVec;
  int i;
  for (i = 1; i < size-remainder; i+=4)
  {
    //Set sum to 0
    sumVec = _mm_set_ps1(0.0f);
    for (int j = 0; j < 3; j++)
    {
      //load b[i+j-1]
      bVec = _mm_loadu_ps(&b[i+j-1]);
      //set c vector to jth value
      cVec = _mm_set_ps1(c[j]);
      //b[i+j-1] * c[j]
      bVec = _mm_mul_ps(bVec, cVec);
      //add result into sumVec
      sumVec = _mm_add_ps(bVec, sumVec);
    }
    //store sumVec result in a
    _mm_storeu_ps(&a[i], sumVec);
  }
  //Cover remiaining values
  while (i < 1023)
  {
    float sum = 0.0;
    for (int j = 0; j<3; j++)
    {
      sum = sum + b[i+j-1] * c[j];
    }
    a[i] = sum;
    i++;
  }
  //set last value to 0.0
  a[1023] = 0.0;
}



