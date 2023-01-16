    .section	.rodata
formatPrintFunc31:  .string "first pstring length: %d, second pstring length: %d\n"
formatPrintAfterChange32:   .string "old char: %c, new char: %c, first string: %s, second string: %s\n"
inputStringForm:    .string "%s"
inputIntForm:       .string "%d"
formatForPrintCase35:   .string "length: %d, string: %s\n"
formatPrintCmpRes:      .string "compare result: %d\n"
    .section	.data
    
#align for 8 and make lable L for each case.    

.align 8 #Align address to multiple of 8
JUMP_TABLE:
    .quad L31 # Case 31
    .quad L33_32 # Case 32 or 33
    .quad L33_32 # Case 32 or 33
    .quad LOTHER # casses that are not in the table
    .quad L35 # Case 35
    .quad L36 # Case 36
    .quad L37 # Case 37


    .section	.rodata
    #thr format for scanf of number from the user
    formatNum:       .string "%d"

.text
.globl run_func
.type run_func, @function
run_func:


    #save temp return address in the stuck
    pushq   %rbp
    movq    %rsp, %rbp

    movq    %rsp, %rbp #for correct debugging




    movq    $0, %rax
    movb    %dil, %al
    
    #init the swich casses tabel that the cases will be in the range of [0,6]
    leaq    -31(%rax),%rax
    
    #if the number affter the the initial os bigger then 6 so ot the case was bigger then 37 or smalleer the 31
    cmpq    $6,%rax         # Compare xi:6
    ja      LOTHER          # if >, goto default-case
    jmp *JUMP_TABLE(,%rax,8)#jump to the lable of the case 
    
    
L31:
    movq    %r9, %rdi
    addq    $1, %rdi
    movq    $0, %rax
    call    pstrlen
    #save for the print the lenth of first string in %rsi
    movq    %rax, %rsi
    #update %rsi to be the second stirng
    movq    %r9, %rdi
    addq    $258,%rdi
    movq    $0, %rax
    call    pstrlen
    #save the return number from the function as argument to print
    movq    %rax, %rdx

    #send the format of the string
    movq    $formatPrintFunc31, %rdi
    call    printf
    jmp     DONE
L33_32:

    #make space for scanf on the stuck
    sub     $16, %rbp

    #get the old char and the new char in one line like the example
    movq    $0, %rax
    #only 4 chars for tow letters one space and one '\0' so we have space on the stuck
    leaq    (%rbp), %rsi
    movq    $inputStringForm, %rdi
    call    scanf

    movq    (%rbp), %r12

    leaq    (%rbp), %rsi
    movq    $inputStringForm, %rdi
    call    scanf
    movq    (%rbp), %r13

    #save as argument the old char
    movq    $0, %rsi
    movb    %r12b, %sil
    #save as argument the old char
    movq    $0, %rdx
    movb    %r13b, %dl
    #save as first argument the pointer for the string.
    movq    $0, %rdi
    movq    %r9, %rdi
    addq    $1,%rdi

    call    doReplace

    #for print send the address of the start of the first string
    #old char and new char all ready in %rsi and %rdi
    movq    %r10, %rdi

    #--------------print
    #save all the arguments for the printf
    #save first string that now in %rax from the func to be fours argument
    movq    %rax, %rcx
    movq    290(%rbp), %rcx

    #update %rax to the seccond string and save it as 5th argument
    add     $257, %rax
    movq    %rax, %r8

    #save ole char as first argument and new char as 2th
    movq    %r12, %rsi
    movq    %r13, %rdx

    #save the format of the string as first argument
    movq    $formatPrintAfterChange32, %rdi

    #call printf
    movq    $0,%rax
    call    printf

    add     $16, %rbp
    jmp     DONE


#--------------------------------------------------------------------------------------------------------------
LOTHER:
    call    invalidInputFunc
    jmp     DONE




L35:
    #make space on the stuck
    sub     $16, %rsp

    #get the i and the j for 3th and 4th argument
    #get i:
    movq    $0, %rax
    leaq    (%rbp), %rsi
    movq    $inputIntForm, %rdi
    movq    $0, %rax
    call    scanf
    movq    $0, %rax
    movq    $0, %r13
    movq    (%rbp), %rax
    movb    %al, %r13b
    #get j:

    leaq    (%rbp), %rsi
    movq    $0, %rax
    movq    $inputIntForm, %rdi
    call    scanf
    movq    $0, %rax
    movq    $0, %rcx
    movq    (%rbp), %rax
    movb    %al, %cl

    movq    %r13, %rdx

    #save as first argument for dst the firs string
    movq    $0, %rax
    movq    %r15, %rax
    addq    $1, %rax
    movq    %rax, %rdi
    #save as second argument the second string for src
    addq    $257, %rax
    movq    %rax, %rsi

    call    pstrijcpy
    #print message
    #--------------------------------
    #send as second arg the size and as third the first string
    movq    %rax, %rdx
    movq    %rax, %rdi  #in %rax we have pointer to the first string from the function
    call    pstrlen     #get in %rax the size of the string
    movq    %rax, %rsi
    #move as first argument the format of the printf.
    movq    $formatForPrintCase35, %rdi
    movq    $0, %rax
    call    printf

    #again for second string
    #send as second arg the size and as third the first string
    movq    %r15, %rdx
    add     $257, %rdx  #move %rdx to the next string
    movq    %rdx, %rdi
    call    pstrlen     #get in %rax the size of the string
    movq    %rax, %rsi
    movq    $formatForPrintCase35, %rdi
    movq    $0, %rax
    call    printf

    #return the stuck to the correct place from this label
    addq    $16, %rsp
    addq    $527, %r15
    movq    %r15, %rbp
    jmp     DONE
#-------------------------------------------------------------------------------------------------------
L36:
    #send as arg pointer to the func
    movq    %r15, %rdi
    addq    $1, %rdi
    call    swapCase
        #print
        #send as second arg the size and as third the first string
        movq    %rax, %rdx
        movq    %rax, %rdi  #in %rax we have pointer to the first string from the function
        call    pstrlen     #get in %rax the size of the string
        movq    %rax, %rsi
        #move as first argument the format of the printf.
        movq    $formatForPrintCase35, %rdi
        movq    $0, %rax
        call    printf

    #the same way for the second string:
    #send as arg pointer to the func
    movq    %r15, %rdi
    addq    $1, %rdi
    addq    $257, %rdi
    call    swapCase
        #print
        #send as second arg the size and as third the first string
        movq    %rax, %rdx
        movq    %rax, %rdi  #in %rax we have pointer to the first string from the function
        call    pstrlen     #get in %rax the size of the string
        movq    %rax, %rsi
        #move as first argument the format of the printf.
        movq    $formatForPrintCase35, %rdi
        movq    $0, %rax
        call    printf
    jmp     DONE
#--------------------------------------------------------------------------------------------
L37:
        #make space on the stuck
        sub     $16, %rbp

        #get the i and the j for 3th and 4th argument
        #get i:
        movq    $0, %rax
        leaq    (%rbp), %rsi
        movq    $inputIntForm, %rdi
        movq    $0, %rax
        call    scanf
        movq    $0, %rax
        movq    $0, %r13
        movq    (%rbp), %rax
        movb    %al, %r13b
        #get j:

        leaq    (%rbp), %rsi
        movq    $0, %rax
        movq    $inputIntForm, %rdi
        call    scanf
        movq    $0, %rax
        movq    $0, %rcx
        movq    (%rbp), %rax
        movb    %al, %cl

        movq    %r13, %rdx

        #save as first argument for dst the firs string
        movq    $0, %rax
        movq    %r15, %rax
        addq    $1, %rax
        movq    %rax, %rdi
        #save as second argument the second string for src
        addq    $257, %rax
        movq    %rax, %rsi

        call    pstrijcmp
        #print message
        #sending args
        movq    $formatPrintCmpRes, %rdi
        movq    %rax, %rsi  #the return in %rax
        call    printf

        add     $16, %rbp

    jmp     DONE
DONE:
    
    
    #cefore ret, make the stuck empty

    #movq       %rsp,%rbp
    movq        %rbp, %rsp
    popq    	%rbp
    xorq        %rax, %rax

    ret
