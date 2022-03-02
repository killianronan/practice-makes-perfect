includelib legacy_stdio_definitions.lib
extrn printf:near
extrn scanf:near


;; Data segment
.data

	print_string BYTE "Sum of i: %I64d and j: %lld is: %I64d", 0Ah, 00h
	print_string2 BYTE "Sum of i: %I64d, j: %lld and bias: %lld is: %I64d", 0Ah, 00h
	format_string BYTE "%lld"
	final_string BYTE "The sum of proc. and user inputs (%lld, %lld, %lld, %lld): %lld\n", 0Ah, 00h
	int_string BYTE "Please enter an integer: ", 0Ah, 00h
	bias QWORD 50
	sum2 QWORD 0
	input QWORD  0


;; Code segment
.code

;; address of the array: RDX
;; size of the array: RCX
public array_proc

array_proc:
			;; RAX is the accumulator
			xor rax, rax

			;; the main loop
L1:			add rax, [rdx] ;; access and add contents
			add rdx, TYPE QWORD	;; TYPE operator returns the number of bytes used by the identified QWORD
			loop L1 ;; RCX as the loop counter

			;; returning from the function/procedure
			ret

;; i in RCX (arg1)
;; j in RDX (arg2)
;; print_proc: adds the two arguments and prints it through printf
public print_proc

print_proc:
			lea rax, [rcx+rdx]		;; a way to add two regs and place it in rax
			mov [rsp+24], rax		;; preserving rax in shadow space
			mov [rsp+16], rcx		;; preserving i
			mov [rsp+8], rdx		;; preserving j

			;; Calling our printf function
			sub rsp, 40				;; the shadow space
			mov r9, rax				;; 4th argument
			mov r8, rdx				;; 3rd argument: j
			mov rdx, rcx			;; 2nd argument: i
			lea rcx, print_string	;; 1st argument: string
			call printf				;; call the function

			;; 2nd call to printf
			;; RSP has changed, so the displacements have also changed
			mov rax, [rsp+64]		;; restoring the sum
			add rax, bias			;; adding the bias
			mov [rsp+72], rax			;; preserving rax
			mov [rsp+32], rax		;; the 5th argument on stack
			mov r9, bias			;; the 4th argument
			mov r8, [rsp+48]		;; restoring j, the 3rd arg
			mov rdx, [rsp+56]		;; restoring i, the 2nd arg
			lea rcx, print_string2 ;; 1st argument: string
			call printf				;; call the function
			
			add rsp, 40				;; deallocate the shadow space
			
			;; restore rax
			mov rax, [rsp+32]
			ret

public      use_scanf			; make sure function name is exported

use_scanf:    
			push    rbp				; push frame pointer
            mov     rbp, rsp		; update ebp
			sub     rsp, 40			; space for local variables (fib-1) [ebp-4] 
			mov		r15, r8			;; c
			mov		r14, rdx		;; b
			mov		r13, rcx		;; a
			mov		rax, 0			; eax = fin;
			add		rax, rcx		; sum + a
			add		rax, rdx		; sum + b
			add		rax, r8			; sum + c
			mov		[rbp-32], rax	; sum
			mov		r12, rax		; sum

			;; Calling our printf function
			lea rcx, int_string		;; 1st argument: string
			call	printf			;; call the function

			lea		rdx, input
			lea		rcx, format_string;; 1st argument: string
			call	scanf			; call the function

			mov		rax, input		; rax = input
			mov		rbx, r12		; rbx = sum
			add		rbx, rax		; sum + input

			mov		[rsp+40], rbx
			mov		[rsp+32], rax																																																																																																																																								
			mov		r9, r15	; c
			mov		r8, r14	; b
			mov		rdx, r13	; a
			lea rcx, final_string		;; 1st argument: string
			call	printf			;; call the function
			mov		rax, rbx 

			mov     rsp, rbp       ; restore esp
            pop     rbp            ; restore ebp
            ret     0              ; return

public      max5			; make sure function name is exported

max5:    
			push    rbp				; push frame pointer
            mov     rbp, rsp		; update ebp
			sub     rsp, 40			; space for local variables (fib-1) [ebp-4] 
			mov		rax,[ebp+8]		;a 
			mov		rbx, [ebp+16]	;b 
			mov		rcx, [ebp+24]	;c
			cmp		rax, rbx
			jle		here
			cmp		rcx, rbx
			jle		here2

		here2:
			mov		rax, rcx
			jmp fin
		here:
			mov		rax, rbx
			jmp fin
		fin:mov     rsp, rbp       ; restore esp
            pop     rbp            ; restore ebp
            ret     0              ; return
public      max			; make sure function name is exported

max:    
			push    rbp				; push frame pointer
            mov     rbp, rsp		; update ebp
			sub     rsp, 40			; space for local variables (fib-1) [ebp-4]
			mov     rbp, [rbp+8]	; I
			mov     rbp, [rbp+16]	; J
			mov     rbp, [rbp+24]	; K
			mov     rbp, [rbp+32]	; L

			mov     rsp, rbp       ; restore esp
            pop     rbp            ; restore ebp
            ret     0              ; return
			
public      fibX64		; make sure function name is exported

fibX64:    
			push    rbp				; push frame pointer
            mov     rbp, rsp		; update ebp
			sub     rsp, 16			; space for local variables (fib-1) [ebp-4] 
			mov		rax, rcx		; eax = fin;
			mov		rcx, 0			; ecx = 0;
			cmp		rax, rcx		; if(fin<=0)
			jle		lessThan		;
			mov		rbx, 1			; ecx = 1
			cmp		rax, rbx		; if(fin == 1)
			je		equal			;
			mov		[rbp-16], rax	;
			mov		rdx, rax		;
			sub		rax, 1			; fin-1
			mov		rcx, rax		;
			call	fibX64			;
			mov     [rbp-8],rax     ; store result of (fib-1)
			mov		rax, [rbp-16]	;
			sub		rax, 2			; fin-2
			mov		rcx, rax		;		
			call	fibX64;
			mov		rbx, [rbp-8]
			add		rax, rbx		;  return fibonacci_recursion(fin-1) + fibonacci_recursion(fin-2)
			jmp		finish2			;
	lessThan:
			;mov		rax,rcx			; eax = fin
			jmp		finish2			; return fin;
	equal:	mov		rax,1			; eax = 1
			jmp		finish2			; return 1;

	finish2:mov     rsp, rbp       ; restore esp
            pop     rbp            ; restore ebp
            ret     0              ; return
end