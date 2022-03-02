.686 ;; identifies this as a 32-bit assembly
.model flat, C ;; define the memory model and calling convention

;; Assembly programs divided into two sections 

.data


.code
;
; example mixing C/C++ and IA32 assembly language
;
;
public      factorial_asm               ; make sure function name is exported

factorial_asm:    push    ebp       ; push frame pointer
            mov     ebp, esp		; update ebp
			sub     esp, 4			; space for local variables result [ebp-4], i [ebp-8]
			mov		eax, [ebp+8]	; eax = N;
			mov		ecx, 0			; ecx = 0;
			cmp		eax, ecx		; if (N!=0)
			jne		else2			;
			mov		eax, 1			; eax = 1
			jmp		finish			;
	else2:	mov		ebx,[ebp+8]		; ebx = N
			mov		edx,[ebp+8]		; ebx = N
			sub		ebx, 1			; ebx = N-1
			push	ebx				;
			call	factorial_asm	;
			add		esp, 4			;
			imul	eax, [ebp+8]	;

	finish:	mov     esp, ebp       ; restore esp
            pop     ebp            ; restore ebp
            ret     0              ; return

public      multiple_k_asm               ; make sure function name is exported

multiple_k_asm:    push    ebp            ; push frame pointer
            mov     ebp, esp       ; update ebp
			sub     esp, 4         ; space for local variables result [ebp-4], i [ebp-8]
			mov		ebx, [ebp+16]	; ebx = array; 
			mov		eax, [ebp+12]	; ecx = K;
			mov		edx, [ebp+8]	; edx = N;
			mov		ecx, 0			; ecx = 0;
			mov		[ebp-4], ecx	; i = 0;
forLoop:	cmp		ecx, [ebp+8]	; if(i>=N)
			jge		endLoop			; jmp
			add		ecx, 1			; ecx = i+1
			mov		eax, ecx 		; eax = i+1
			sub		ecx, 1			; ecx = i
			mov		ebx, [ebp+12]	; ebx = K
			xor		edx, edx 
			idiv	ebx				; if((i+1)%K == 0)
			mov		ebx, 0			; ebx = 0
			cmp		edx,ebx			;
			je		L9				; ebp+16= i , ebp+16+2 = i+1
			mov		edx, ecx		;
			imul	edx, 2			; eax = eax * arg0;
			mov		[ebp+16+edx], 0; array[i] = 0;
			add		ecx, 1			; i++
			mov		[ebp-4], ecx	; i = ecx;
			jmp		forLoop
	L9:		
			mov		edx, ecx		;
			imul	edx, 2			; eax = eax * arg0;
			mov		[ebp+16+edx], 1	; array[i] = 1;
			add		ecx, 1			; i++
			mov		[ebp-4], ecx	; i = ecx;
			jmp		forLoop

	endLoop:mov		eax,[ebp+16]	;
			mov     esp, ebp       ; restore esp
            pop     ebp            ; restore ebp
            ret     0              ; return

public      poly_asm               ; make sure function name is exported

poly_asm:   push    ebp            ; push frame pointer
            mov     ebp, esp       ; update ebp
			sub     esp, 4         ; space for local variable result [ebp-4]
            mov     ebx, [ebp+8]   ; mov arg into eax
			mov		ecx, [ebp-4]   ; mov result into ecx
			push	2			
			push	ebx		
			call	pow_asm			;
            mov     ebx, [ebp+8]   ; mov arg into ebx
			add		esp, 8			; remove params from stack
			add		ebx, eax       ; res = res + arg;
			add		ebx, 1	       ; res = res + 1;
	L3:		mov		eax, ebx
			mov     esp, ebp       ; restore esp
            pop     ebp            ; restore ebp
            ret     0              ; return

public      pow_asm               ; make sure function name is exported

pow_asm:    push    ebp            ; push frame pointer
            mov     ebp, esp       ; update ebp
			sub     esp, 8         ; space for local variables result [ebp-4], i [ebp-8]
			mov		ebx, [ebp+12]	; ebx = arg1;
			mov		edx, [ebp+8]	; edx = arg0;
			mov		eax, 1			; eax = 1;
			mov		ecx, 1			; ecx = 1;
			mov		[ebp-4], eax	; result = 1;
			mov		[ebp-8], ecx	; i = 1;
pow_asm1:	cmp		[ebp-8], ebx	; if(i>arg1)
			jg		pow_asm2		; jmp
			imul	eax, edx		; eax = eax * arg0;
			mov		[ebp-4], eax	; result = eax;
			add		ecx, 1			; i++;
			mov		[ebp-8], ecx	; i = ecx;
			jmp		pow_asm1             

pow_asm2:	mov     esp, ebp       ; restore esp
            pop     ebp            ; restore ebp
            ret     0              ; return
end
