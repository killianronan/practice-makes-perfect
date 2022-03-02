#include "postfix.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <math.h>
#include <string.h>


// evaluate expression stored as an array of string tokens
double evaluate_postfix_expression(char ** expr, int elements){
    struct double_stack *stack;
    stack = double_stack_new(elements);
    int i;
    double token;
    double result;
    double number1;
    double number2;
    
    for(i = 0; i<elements; i++){
        if(strlen(expr[i])==1){
            switch(expr[i][0]){
                case '+':
                    number1 = double_stack_pop(stack);
                    number2 = double_stack_pop(stack);
                    result = number1+number2;
                    double_stack_push(stack,result);
                    break;
                case '-':
                
                    number1 = double_stack_pop(stack);
                    number2 = double_stack_pop(stack);
                    result = number2-number1;
                    double_stack_push(stack,result);
                    
                    break;
                case 'X':
                    number1 = double_stack_pop(stack);
                    number2 = double_stack_pop(stack);
                    result = number1*number2;
                    double_stack_push(stack,result);
                    break;
                case '/':
                    number1 = double_stack_pop(stack);
                    number2 = double_stack_pop(stack);
                    result = number2/number1;
                    double_stack_push(stack,result);
                    break;
                case '^':
                    number1 = double_stack_pop(stack);
                    number2 = double_stack_pop(stack);
                    result = pow(number2,number1);
                    double_stack_push(stack,result);
                    break;
                default:
                        token = atof(expr[i]);
                        double_stack_push(stack,token);
                        break;
                }
        }
        else{
            token = atof(expr[i]);
            double_stack_push(stack,token);
        }
    }
    return result;
}


