#include "infix.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <math.h>
#include <string.h>

double calculatePrecedence(char * opera){
    if(*opera == '-'|| *opera == '+'){
        return 1;
    }
    if(*opera == '/'|| *opera == 'X'){
        return 2;
    }
    if(*opera == '^'){
        return 3;
    }
    return 0;
}


// evaluate expression stored as an array of string tokens
double evaluate_infix_expression(char ** expr, int elements) {

    char ** postfix;
    postfix = malloc(sizeof(double)*elements);
    struct double_stack *stack;
    stack = double_stack_new(elements);
    double temp;
    double y;
    int postfixIndex = 0;
    int x;
    int i;

    for(i = 0; i<elements; i++){
        if(strlen(expr[i])==1){
            switch(expr[i][0]){
               case '(':
                    double_stack_push(stack,i);
                    break;
               case ')':
                    y = stack->items[stack->top-1];
                    x = (int)y;
                    while(expr[x][0]!='('){
                        double index = double_stack_pop(stack);
                        int temp = (int)index;
                        postfix[postfixIndex]=expr[temp];
                        postfixIndex++;
                        y = stack->items[stack->top-1];
                        x = (int)y;
                    }
                    double_stack_pop(stack);
                    
                    break;
                case '+':
                    y = stack->items[stack->top-1];
                    x = (int)y;
                    while(calculatePrecedence(expr[x])>1){
                        temp = double_stack_pop(stack);
                        int temp2 = (int)temp;
                        postfix[postfixIndex]=expr[temp2];
                        postfixIndex++;
                        y = stack->items[stack->top-1];
                        x = (int)y;
                    }
                    double_stack_push(stack, i);
                    break;
                case '-':
                    y = stack->items[stack->top-1];
                    x = (int)y;
                    while(calculatePrecedence(expr[x])>1){
                        temp = double_stack_pop(stack);
                        int temp2 = (int)temp;
                        postfix[postfixIndex]=expr[temp2];
                        postfixIndex++;
                        y = stack->items[stack->top-1];
                        x = (int)y;
                    }
                    double_stack_push(stack, i);
                    break;
                case 'X':
                    y = stack->items[stack->top-1];
                    x = (int)y;
                    while(calculatePrecedence(expr[x])>2){
                        temp = double_stack_pop(stack);
                        int temp2 = (int)temp;
                        postfix[postfixIndex]=expr[temp2];
                        postfixIndex++;
                        y = stack->items[stack->top-1];
                         x = (int)y;
                    }
                    double_stack_push(stack, i);
                    break;
                case '/':
                    y = stack->items[stack->top-1];
                    x = (int)y;
                    while(calculatePrecedence(expr[x])>2){
                        temp = double_stack_pop(stack);
                        int temp2 = (int)temp;
                        postfix[postfixIndex]=expr[temp2];
                        postfixIndex++;
                        y = stack->items[stack->top-1];
                         x = (int)y;
                    }
                    double_stack_push(stack, i);
                    break;
                case '^':
                    double_stack_push(stack, i);
                    break;
               default:
                    postfix[postfixIndex]=expr[i];
                    postfixIndex++;
                    break;
            }
        }
        else{
            postfix[postfixIndex]=expr[i];
            postfixIndex++;
        }
    }
    while(stack->top!=0){
        double item = double_stack_pop(stack);
        int temp2 = (int)item;
        postfix[postfixIndex]=expr[temp2];
        postfixIndex++;
    }
    double result;
    result = evaluate_postfix_expression(postfix,postfixIndex);
    return result;
}



