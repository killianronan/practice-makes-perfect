#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>
#include <stdbool.h>
#include "huff.h"
#include "bitfile.h"

// create a new huffcoder structure
struct huffcoder *  huffcoder_new()
{
	struct huffcoder * result;
	result = malloc(sizeof(struct huffcoder));
	for(int x = 0; x<256; x++){
		result->freqs[x] = 0; //char frequencies
		result->code_lengths[x] = 0; //a = 1, b = 2, c = 2
		result->codes[x] = 0; //a=0, b= 01, c = 11
	}
	return result;
}
// count the frequency of characters in a file; set chars with zero
// frequency to one
void huffcoder_count(struct huffcoder * this, char * filename)
{
	int x;
	unsigned char c;
	FILE * file;
	file = fopen(filename, "r");
	assert( file != NULL );
	c = fgetc(file);
	while( !feof(file) ) {
	  	this->freqs[(int)c]++;
	   	c = fgetc(file);
	}
	fclose(file);
	for(x = 0;x<256;x++){
		if(this->freqs[x]==0){
			this->freqs[x]++;
		}
	}
}
struct huffchar * find_and_remove_smallest(struct huffchar** list, int size){
	int x;
	int smallest = 0;
	struct huffchar * result;
	for(x = 1; x<size; x++){
		if(list[x]->freq < list[smallest]->freq){
			smallest = x;
		}
		else if((list[x]->freq < list[smallest]->freq) && (list[x]->seqno < list[smallest]->seqno)){
			smallest = x;
		}
	}
	result = list[smallest];
	int y;
	for(y = smallest; y<size-1; y++){
		list[y]= list[y+1];
	}
	return result;
}
struct huffchar * createCompoundNode(struct huffchar * small, struct huffchar * smaller, int seqno){
	struct huffchar * compound;
	compound = malloc(sizeof(struct huffchar));
	compound->is_compound = 1;
	compound->freq = small->freq + smaller-> freq;
	compound->u.compound.left = small;
	compound->u.compound.right = smaller;
	compound->seqno = seqno;
	return compound;
}
struct huffchar * createSimpleNode(int value, int freq){
	struct huffchar * leaf;
	leaf = malloc(sizeof(struct huffchar));
	leaf->is_compound = 0;
	leaf->seqno = value;
	leaf->u.c = (unsigned char)value;
	leaf->freq = freq;
	return leaf;
}


void huffcoder_build_tree(struct huffcoder * this)
{
	int nchars;
	nchars = NUM_CHARS;
	struct huffchar ** list;
	list = malloc(sizeof(struct huffchar*)*NUM_CHARS);
	int x;
	for(x=0; x<256; x++){
		list[x] = createSimpleNode(x, this->freqs[x]);
	}
	int seqno = 256;
	while(nchars>1){
		struct huffchar * smallest;
		struct huffchar * nextSmallest;
		smallest = find_and_remove_smallest(list, nchars);
		nchars--;
		nextSmallest = find_and_remove_smallest(list, nchars);
		struct huffchar * compound_node;
		compound_node = createCompoundNode(smallest, nextSmallest, seqno);
		list[nchars-1] = compound_node;
		seqno++;
	}
	this->tree = list[0];
}
void build_table(struct huffcoder * this, struct huffchar* tree , unsigned long long code, int code_length){
	if(tree->is_compound==0){
		this->codes[(int)tree->u.c] = code;
		this->code_lengths[(int)tree->seqno] = code_length;
	}
	else{
		build_table(this, tree->u.compound.left, (code) | ((unsigned long long)0 << code_length), code_length+1);
		build_table(this, tree->u.compound.right, (code) | ((unsigned long long)1 << code_length), code_length+1);
	}
}
// using the Huffman tree, build a table of the Huffman codes
// with the huffcoder object
void huffcoder_tree2table(struct huffcoder * this)
{
	unsigned long long code = 0;
	int length = 0;
	build_table(this, this->tree, code, length);
}

// print the Huffman codes for each character in order
void huffcoder_print_codes(struct huffcoder * this)
{
  int i, j;
  char buffer[NUM_CHARS];

  for ( i = 0; i < NUM_CHARS; i++ ) {
    // put the code into a string
    assert(this->code_lengths[i] < NUM_CHARS);
    for ( j = this->code_lengths[i]-1; j >= 0; j--) {
      buffer[j] = ((this->codes[i] >> j) & 1) + '0';
    }
    // don't forget to add a zero to end of string
    buffer[this->code_lengths[i]] = '\0';

    // print the code
    printf("char: %d, freq: %d, code: %s\n", i, this->freqs[i], buffer);;
  }
}



// encode the input file and write the encoding to the output file
void huffcoder_encode(struct huffcoder * this, char * input_filename,
		      char * output_filename)
{

}

// decode the input file and write the decoding to the output file
void huffcoder_decode(struct huffcoder * this, char * input_filename,
		      char * output_filename)
{

}

