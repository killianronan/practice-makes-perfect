#include "bitset.h"
#include <stdio.h>
#include <stdlib.h>
// create a new, empty bit vector set with a universe of 'size' items
struct bitset * bitset_new(int size) {
    struct bitset * result;
    result = malloc(sizeof(struct bitset));

    result->universe_size = size;
    int wordCount = size/64;
    if(size%64!=0){
        wordCount++;
    }
    result->size_in_words = wordCount;
    result->bits = malloc(sizeof(uint64_t)*(wordCount));
    // result->bits = malloc(sizeof(uint64_t) * (wordCount));
    int x;
    for(x = 0; x<result->size_in_words; x++){
	result->bits[x] = 0;
    }
    return result;
}

// get the size of the universe of items that could be stored in the set
int bitset_size(struct bitset * this) {
    return this->universe_size;
}

// get the number of items that are stored in the set
int bitset_cardinality(struct bitset * this){
    int x;
    int y;
    uint64_t mask;
    uint64_t one = 1;
    uint64_t result;
    int count = 0;
    for(x=0;x<this->size_in_words; x++){
        for(y=0;y<64; y++){
            mask = one<<y;
            result = this->bits[x]&mask;
            if(result==mask){
                count++;
            }
        }
    }
    return count;
}

// check to see if an item is in the set
int bitset_lookup(struct bitset * this, int item){
 if(item<this->universe_size&&item>=0){
        int int_adr = item/64;  //this->bits[k]
        int shift = item%64;
        uint64_t mask = 1;
        uint64_t adr = mask<<shift;
	uint64_t result = this->bits[int_adr];
        result &= adr;
        if(result==adr){
            return 1;
        }
        else{
            return 0;
        }
    }
    else {
        return 0;
    }
}

// add an item, with number 'item' to the set
// has no effect if the item is already in the set
void bitset_add(struct bitset * this, int item) {
 if(item<this->universe_size&&item>=0){
        int int_adr = item/64; //this->bits[k]
        uint64_t mask = 1;
        int shift = (item%64);
        mask = mask<<shift;
        this->bits[int_adr] |= mask;
        return;
    }
    else {
        return;
    }
}

// remove an item with number 'item' from the set
void bitset_remove(struct bitset * this, int item) {
 if(item<this->universe_size&&item>=0){
        int int_adr = item/64;  //this->bits[k]
        int shift = item%64;
        uint64_t mask = 1;
        uint64_t adr = mask<<shift;
        adr = ~adr; // i.e 111101111
        this->bits[int_adr] &= adr;
        return;
    }
    else {
        return;
    }
}
// place the union of src1 and src2 into dest;
// all of src1, src2, and dest must have the same size universe
void bitset_union(struct bitset * dest, struct bitset * src1,
    struct bitset * src2) {
  if(dest->universe_size==src1->universe_size&&src1->universe_size==src2->universe_size){
        int x;
        for(x = 0; x<dest->size_in_words; x++){
            dest->bits[x]= src1->bits[x]|src2->bits[x];
        }
    }
    return;
}

// place the intersection of src1 and src2 into dest
// all of src1, src2, and dest must have the same size universe
void bitset_intersect(struct bitset * dest, struct bitset * src1,
    struct bitset * src2) {
    if(dest->universe_size==src1->universe_size&&src1->universe_size==src2->universe_size){
        int x;
        for(x = 0; x<=dest->size_in_words; x++){
            dest->bits[x]= src1->bits[x]&src2->bits[x];
        }
    }
    return;
}
