
        .section    .rodata
size:   .string "%d"
str1:   .string "%s"
printStr:           .string "%s\n"

.text
.global run_main
.type run_main, @function
run_main:
    pushq   %rbp
    movq    %rsp, %rbp #for correct debugging

    #make space for the stuck
    movq    $0, %rax
    #give the pointer of the strat of the stuck for first scanf
    #why 528? => 256 + 256 for strings
    #            2 for \0 for each strinf
    #            2 for the select select
    leaq    -528(%rsp), %rsp
    leaq    (%rsp), %rsi
    #save the start of the input
    movq    %rsp, %r15
    #give the format of the scanf
    movq    $size, %rdi
    #get the firs number
    call    scanf

    movq    $0, %rax
    #move the pointer for the next scanf by one and give it as argument for scanf
    leaq    1(%rsp), %rsi
    #give as argument the format of scanf
    movq    $str1, %rdi
    #scan the first string
    call    scanf

    movq    $0, %rax
    #move the pointr for scanf fo the next int and scan it like befor
    leaq    257(%rsp), %rsi
    movq    $size, %rdi
    call    scanf
    #move the pointer for the stuck by one after the int and get the next string as before
    movq    $0, %rax
    leaq    258(%rsp), %rsi
    movq    $str1, %rdi
    call    scanf
    #move the pointer of the stuck for save number option
    movq    $0, %rax
    leaq    514(%rsp), %rsi
    movq    $size, %rdi
    call    scanf

    #move selected number as argument
    movq    $0,%rdi
    movq    %rsi, %rdi
    movq    %rsp, %rsi
    leaq    257(%rsp), %rdx
    #call the the select_func function that on the select_func file with the stuck
    movq    $0, %r9
    movq    %r15, %r9

    call    run_func

    #from the start of the input restore the value of %rbp

    movq    %rbp, %rsp
    popq    %rbp

    movq    $0, %rax
    ret




    

