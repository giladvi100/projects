 .section   .rodata
    invalidInput:   .string "invalid input!\n"

.text
.globl pstrlen
.type pstrlen, @function
pstrlen:
    pushq	%rbp		#save the old frame pointer
    movq    %rsp, %rbp

    movq    $0, %rax
    movq    $0, %r8
    movq    -1(%rdi),%r8
    movb    %r8b, %al

    movq	%rbp, %rsp	#restore the old stack pointer - release all used memory.
    popq	%rbp		#restore old frame pointer (the caller function frame)


    ret			#return to caller function (OS).


 #--------------------------------------------------------------------------------------------------------------

.globl replaceChar
.type replaceChar, @function
replaceChar:
    #init %r11 to be the size of the string
    movq    $0, %r11
    movq    -1(%rdi),%r8
    movb    %r8b,%r11b

    #init %r12 to be the counter
    movq    $0,%rax

LOOP_REP_STR1:
    #if %rax(counter) >= %r11(size of string) ==> end the loop
    cmpq    %r11,%rax       #if done the loop finish
    jge     END_LOOP_STR1

    #if this char is equal to the old one replace it in the label L_REPLACE
    #save in %r8 the char of the string
    movq    $0, %r8
    movq    (%rdi), %r8
    #comper to the old char
    cmpb    %sil, %r8b
    je      L_REPLACE

#the location that we go back after the replace
L_END_OF_REPLACE:

    #update counter
    addq    $1,%rax

    #move to the next char by moving the address of the pointer on the string that on the stuck
    addq     $1,%rdi
    jmp     LOOP_REP_STR1

#labele that replace old char with new char and jump back to the loop for getting the next char
L_REPLACE:
    movq    $0,%r8
    movq    (%rdi), %r8
    movb    %dl,%r8b
    movq    %r8, (%rdi)
    jmp     L_END_OF_REPLACE

END_LOOP_STR1:
    ret

.globl doReplace
.type doReplace, @function
doReplace:

    #this function dont change the rsp and rbp

    #save on %r10 %rdi
    movq    %rdi, %r10

    #make the change for the first string
    call    replaceChar

    #setting value for seccend string
    movq    %r10, %rdi
    addq     $257, %rdi
    call    replaceChar

    #for print send the address of the start of the first string
    #old char and new char all redy in %rsi and %rdi
    movq    %r10, %rdi
    movq    $0, %rax
    movq    %r10, %rax
    ret



#------------------------------------------------------------------------------------------------------------------


.globl pstrijcpy
.type pstrijcpy, @function
pstrijcpy:
    pushq	%rbp		#save the old frame pointer
    movq    %rsp, %rbp

    #save pointer to the start of the first string in %r15
    movq    %rdi, %r15
    #check for invalid input:

    #if i>j:
    cmp     %rdx, %rcx
    jl      L_INVALID_INPUT


    #if j>str1
    #%rdi is all ready str1
    call    pstrlen
    cmp     %rcx, %rax
    jle      L_INVALID_INPUT

    #if j>str2
    #move str2 that in %rsi to %rdi and save str2 in %r14
    movq    %rdi, %r14
    movq    %rsi, %rdi
    call    pstrlen
    cmp     %rcx, %rax
    jle      L_INVALID_INPUT

    #if i<0
    movq    $0, %rax
    cmp     %rax, %rdx
    jl      L_INVALID_INPUT

    jmp     L_VALID_INPUT
L_INVALID_INPUT:
    call    invalidInputFunc
    jmp     L_END_COPY_LOOP
L_VALID_INPUT:
    #save in %rax the pointer for dst for return
    movq    %rdi, %rax
    #update str1 (%rdi) and str2 (%rsi) to start at index i
    addq    %rdx, %r14
    addq    %rdx, %rsi
    #update j to be the number og itertion
    sub     %rdx, %rcx
    #init %rdx to be 0 for counter
    movq    $0, %rdx
    #for (i (%rdx = 0) < j (%rcx); i++)
L_LOOP_FOR_COPY:
    #conditin of the loop
    cmp     %rcx, %rdx
    jg      L_END_COPY_LOOP

    #if didnt jump copy from src to dst
    movq    (%r14), %r13
    movq    (%rsi), %r12
    movb    %r12b, %r13b
    movq    %r13, (%r14)

    #update for the next char
    addq    $1, %r14
    addq    $1, %rsi
    #update counter
    addq    $1, %rdx
    jmp     L_LOOP_FOR_COPY

L_END_COPY_LOOP:
    #return of pointer to the first string
    movq    %r15, %rax
    movq	%rbp, %rsp	#restore the old stack pointer - release all used memory.
    popq	%rbp		#restore old frame pointer (the caller function frame)


    ret			#return to caller function (OS).
#--------------------------------------------------------------------------------------------------------------------

.globl swapCase
.type swapCase, @function
swapCase:
    pushq	%rbp		#save the old frame pointer
    movq    %rsp, %rbp

    #save the pointer of the string in %r14
    movq    %rdi, %r14

    #get fron the function the size of th string and save it on %r11
    movq    $0, %rax
    call    pstrlen
    movq    $0, %r11
    movq    %rax, %r11

    #init counter in %rax
    movq    $0, %rax

    #save the pointer of the string in %rdi
    movq    %r14, %rdi

    LOOP_UPER_CASE:
        #if %rax(counter) >= %r11(size of string) ==> end the loop
        cmpq    %r11,%rax       #if done the loop finish
        jge     END_LOOP_UPPER

        #if this char is in the range [65,90] so its uppercase
        #save in %r8 the char of the string
        movq    $0, %r8
        movq    (%rdi), %r8
        #if its too smale
        movq    $65, %rsi
        cmpb    %sil, %r8b
        jl      L_NOT_UPPER

        #if its too big
        movq    $90, %rsi
        cmpb    %sil, %r8b
        jg      L_NOT_UPPER

        #if we get here we know that this is an upper case
        jmp     L_UPPERCASE

    L_NOT_UPPER:
        #so maybe its small later.
        #if its too smale
        movq    $97, %rsi
        cmpb    %sil, %r8b
        jl      L_KEEP_GOING

        #if its too big
        movq    $122, %rsi
        cmpb    %sil, %r8b
        jg      L_KEEP_GOING

        #if we get here we know that this is a lower case
        jmp     L_SMALLERCASE



    L_UPPERCASE:
        #add to the char 32 to make it lower case
        addq     $32, (%rdi)
        jmp     L_KEEP_GOING

    L_SMALLERCASE:
        #add to the char -32 to make it upper case
        addq     $-32, (%rdi)
        jmp     L_KEEP_GOING

    L_KEEP_GOING:
        #update counter
        addq    $1,%rax

        #move to the next char by moving the address of the pointer on the string that on the stuck
        addq     $1,%rdi
        jmp     LOOP_UPER_CASE

END_LOOP_UPPER:
    #save in %rax the pointer of the string to rturn with %r14
    movq    %r14, %rax
    movq	%rbp, %rsp	#restore the old stack pointer - release all used memory.
    popq	%rbp		#restore old frame pointer (the caller function frame)
    ret			#return to caller function (OS).



#------------------------------------------------------------------------------
.globl pstrijcmp
.type pstrijcmp, @function
pstrijcmp:
    pushq	%rbp		#save the old frame pointer
    movq    %rsp, %rbp

    #save pointer to the start of the first string in %r15
    movq    %rdi, %r15
    #check for invalid input:

    #if i>j:
    cmp     %rdx, %rcx
    jl      L_INVALID_INPUT_CMP


    #if j>str1
    #%rdi is all ready str1
    call    pstrlen
    cmp     %rcx, %rax
    jle      L_INVALID_INPUT_CMP

    #if j>str2
    #move str2 that in %rsi to %rdi and save str2 in %r14
    movq    %rdi, %r14
    movq    %rsi, %rdi
    call    pstrlen
    cmp     %rcx, %rax
    jle      L_INVALID_INPUT_CMP

    #if i<0
    movq    $0, %rax
    cmp     %rax, %rdx
    jl      L_INVALID_INPUT_CMP

    jmp     L_VALID_INPUT_CMP
L_INVALID_INPUT_CMP:
    call    invalidInputFunc
    movq    $-2, %rax
    jmp     L_END_FUNC
L_VALID_INPUT_CMP:

    #save in %rax the pointer for dst for now
    movq    %rdi, %rax
    #update str1 (%rdi) and str2 (%rsi) to start at index i
    addq    %rdx, %r14
    addq    %rdx, %rsi
    #update j to be the number of itertion
    sub     %rdx, %rcx
    #init %rdx to be 0 for counter
    movq    $0, %rdx

    #for (i (%rdx = 0) < j (%rcx); i++)
L_LOOP_FOR_CMP:
    #conditin of the loop
    cmp     %rcx, %rdx
    jg      L_END_CMP_LOOP

    #if didnt jump compare between src to dst

    movq    (%r14), %r13
    movq    (%rsi), %r12
    cmpb    %r12b, %r13b
    jg      L_PSTR1_BIGGER

    cmpb    %r12b, %r13b
    jl      L_PSTR2_BIGGER

    #update for the next char
    addq    $1, %r14
    addq    $1, %rsi
    #update counter
    addq    $1, %rdx
    jmp     L_LOOP_FOR_CMP

L_PSTR1_BIGGER:
    movq    $1, %rax
    jmp     L_END_FUNC
L_PSTR2_BIGGER:
    movq    $-1, %rax
    jmp     L_END_FUNC
L_END_CMP_LOOP:
    movq    $0, %rax
    jmp     L_END_FUNC
L_END_FUNC:

    movq	%rbp, %rsp	#restore the old stack pointer - release all used memory.
    popq	%rbp		#restore old frame pointer (the caller function frame)


    ret			#return to caller function (OS).



#------------------------------------------------------------------------------------
#function that print invalidInput
.globl invalidInputFunc
.type invalidInputFunc, @function
invalidInputFunc:
    pushq	%rbp		#save the old frame pointer
    movq    %rsp, %rbp

    movq    $invalidInput, %rdi
    movq    $0, %rax
    call    printf

    movq	%rbp, %rsp	#restore the old stack pointer - release all used memory.
    popq	%rbp		#restore old frame pointer (the caller function frame)


    ret			#return to caller function (OS).
