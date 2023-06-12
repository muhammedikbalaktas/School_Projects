.data
    menu_message:   .asciiz    "\n1. Find Palindrome\n2. Reverse Vowels\n3. Find Distinct Prime\n4. Lucky Number\n5. Exit\n"
    menu_exit_message:  .asciiz "goodbye!!\n"
    ################## q2 data
    q2_input_message:  .asciiz "please enter a string:"
    q2_vowels:  .asciiz "aeiuo"

    ################## q3 data
    q3message:      .asciiz "enter a number\n"
    
    m_not_square:     .asciiz " is not a square-free number.\n"
    m_square:       .asciiz " is square-free number and has "
    m_square_1:     .asciiz " distinct prime factor: "
    m_square_2:     .asciiz " has 1 distinct prime factor: "
    message3:       .asciiz " is distinct prime divider\n"
    space:          .asciiz " "
    endl:           .asciiz "\n"
    
.text
    main_menu:
        li			$v0, 4				# $v0 = 4
        la			$a0, menu_message			# 
        syscall
        li			$v0, 5				# $v0 = 5
        syscall
        move $t9, $v0  # move integer to $t9    
        beq			$t9, 1, exit	# if $t9 == 1 then find
        beq			$t9, 2, q2_main	# if $t9 == 2 then q2_main
        beq			$t9, 3, q3_main	# if $t9 == 3 then q3_main
        beq			$t9, 4, exit	# if $t9 == 4 then q4_main
        j			exit				# jump to exit

q2_main:
    li			$v0, 4				# $v0 = 4
    la			$a0, q2_input_message			# 
    syscall
    ######################################################################
    # Read the string input from the user
    li      $v0, 9          # system call for allocating memory
    li      $a0, 100        # allocate 100 bytes of memory
    syscall
    move    $s7, $v0        # save the address of the new memory block

    li      $v0, 8          # system call for reading a string
    move      $a0, $s7   # load the address of the input buffer
    li      $a1, 100        # maximum length of input is 100 bytes
    syscall
    li			$s3, 0				# $s3 = 0
    
    ######################################################################
    
    move 		$t0, $s7			# $t0 = $s7 $s7 is for input buffer

    

    j			get_size_of_input				# jump to get_size_of_input
get_size_of_input:
    lb			$t1, ($t0)			# 
    beqz        $t1, ready_for_get_vowels
    addi		$t0, $t0, 1		# $t0 = $t0 + 1
    addi		$s3, $s3, 1			# $s3 = $s3 + 1
    j			get_size_of_input				# jump to get_size_of_input
    
    
ready_for_get_vowels:
    move 		$t0, $s7			# $t0 = $s7
    li      $v0, 9          # Allocate memory for a string
                            # system call for allocating memory
    li      $a0, 100        # allocate 100 bytes of memory
    syscall
    move    $s6, $v0        # save  new memory block for vowels that is inside input
    move 		$s2, $s6			# $s2 = $s6
    li		$s4, 0			# $s4=0 size of vowels array
    j			get_vowels				# jump to get_vowels
    
    

get_vowels:
    lb			$t3, ($t0)			# 
    beqz        $t3, replace_vowels
    la			$t1, q2_vowels			# 
    j			check_vowel				# jump to check_vowel
    

check_vowel:
    lb			$t2, ($t1)			# 
    beqz        $t2, continue_to_get_vowels
    beq			$t3, $t2, add_to_vowels	# if $t3 == $t2 then add_to_vowels
    addi		$t1, $t1, 1			# $t1 = $t1 + 1
    j			check_vowel				# jump to check_vowel
     
    
add_to_vowels:
    sb			$t2, ($s2)			# 
    addi		$s2, $s2, 1			# $s2 = $s2 + 1
    addi		$s4, $s4, 1			# $s4 = $s4 + 1
    
    j			continue_to_get_vowels				# jump to continue_to_get_vowels
    
    
continue_to_get_vowels:
    addi		$t0, $t0, 1			# $t0 = $t0 + 1
    j			get_vowels				# jump to get_vowels
#################################################################################3

replace_vowels:
    addi		$s4, $s4, -1			# $s4 = $s4 + -1
    add			$s5, $s4, $s6		# $s5 = $s4 + $s6
    lb			$s0, ($s6)			# 
    move 		$t4, $s7			# $t4 = $s7
    j			start_replacment				# jump to start_replacment
    

start_replacment:
    lb			$t0, ($t4)			# 
    beqz        $t0,    q2_exit
    la			$t1, q2_vowels			#
    j			find_vowels				# jump to find_vowels

find_vowels:
    lb			$t2, ($t1)			# 
    beqz        $t2, continue_to_replace_vowels
    beq			$t2, $t0, replace_letters	# if $t2 == $t0 then replace_letters
    addi		$t1, $t1, 1			# $t1 = $t1 + 1
    j			find_vowels				# jump to find_vowels
    
replace_letters:
    lb			$s0, ($s5)			# 
    sb			$s0, ($t4)			# 
    addi		$s5, $s5, -1			# $s5 = $s5 + -1
    j			continue_to_replace_vowels				# jump to start_replacment
    
    

continue_to_replace_vowels:
    addi		$t4, $t4, 1			# $t4 = $t4 + 1
    j			start_replacment				# jump to start_replacment
    




q2_exit:
    li			$v0, 4				# $v0 = 4
    move 		$a0, $s7			# $a0 = $s7
    syscall
    j			main_menu				# jump to main_menu
    
#################################################################################################
#################################################################################################
#################################### Q2 END PART   ##############################################
#################################################################################################
#################################################################################################
q3_main:
    li			$s1, 1				# $s1 = 0 number of primes that divides input
    
    li			$v0, 4				# $v0 = 4
    la			$a0, q3message			# 
    syscall

    li			$v0, 5				# $t0 = 5  get input from user
    syscall
    move 		$s0, $v0			# $s0 = $v0  move the value of input
    li			$t3, 2				# $t3 = 2
    li			$t0, 2				# $t0 = 2
    slt         $t5,    $s0,    $t0
    beq			$t5, 1, not_square_function	# if $t5 == 1 then not_square_function
    beq			$s0, $t3, prime_square	# if $s0 == $t3 then prime_square_for_two
    li			$a0, 40				# $a0 = 40
    li			$v0, 9				# $v0 = 9
    syscall
    move 		$s7, $v0			# $s7 = $v0
    move 		$s6, $s7			# $s6 = $s7
    li			$s5, 0				# $s6 = 0
    li			$s4, 0				# $s4 = 0
    
    
    j			check_dividers				# jump to check_dividers
    
    
         
        
check_dividers:
                #$s0 is input number    $s1 is number of division
                #$t0 is i value and initially it is 2
    addi		$t2, $s0, -1		# $t2 = $s0 + -1

    div			$s0, $t0			# $s0 / $t0 
    mfhi	$t3					# $t3 = $s0 mod $t0 
    
    
    
    beq			$t3, $zero, divides	# if $t3 == $zero then divides
    
    
    slt         $t1, $t0, $t2,
    addi		$t0, $t0, 1			# $t0 = $t0 + 1
    beq			$t1, 1, check_dividers	# if $t1 == 0 then target
    li			$t1, 1				# $t1 = 1
    
    beq			$s1, $t1, prime_square	# if $t0 == 1 then target
    beq			$s0, $s1, square_function	# if $s0 == $s1 then square_function
    j			not_square_function				# jump to not_square_function


divides:
    #$t0 is i value
    #this function is called whenever i divides the input
    
    move 		$s2, $t0			# $s2 = $t0
    jal			check_prime				# jump to check_prime and save position to $ra
    #addi		$t0, $t0, 1			# $t0 = $t0 + 1
    j			check_dividers				# jump to check_dividers
    
    
check_prime:
    #$s2 is temp i value
    addi		$t0, $t0, 1			# $t0 = $t0 + 1  increment i for check_dividers i
    li			$t4, 1				# $t4 = 1  j value
    bne			$s2, 1, prime_loop	# if $s2 != $t1 then target
    
    
    addi		$s1, $s1, 1			# $s1 = $s1 + 1
   
    j			check_dividers				# jump to check_dividers
    
    
    

    
prime_loop:
    #$t4 is j value for looping
    #$s3 ==> j will loop until $s3 value
    addi		$t4, $t4, 1			# $t4 = $t4 + 1
    beq			$t4, $s2, it_is_prime	# if $t4 == $s2 then check_dividers
    div			$s2, $t4			# $s2 / $t4
    mfhi	$t3					# $t3 = $s2 mod $t4 
    beq			$t3, $zero, check_dividers	# if $t3 == $zero then check_dividers

    j			prime_loop				# jump to prime_loop
    
it_is_prime:
    mult $s1, $s2
    mflo $t5
    sll			$t7, $s5, 2			# $t0 = $s5 << 2
    add			$t7, $t7, $s6		# $t0 = $t0 + $s6
    sw			$s2, ($t7)			# 
    addi		$s5, $s5, 1			# $s5 = $s5 + 1
    move 		$s1, $t5			# $s1 = $t5
    addi		$s4, $s4, 1			# $s4 = $s4 + 1
    j			check_dividers				# jump to check_dividers
    
   
not_square_function:
    li			$v0, 1				# $v0 = 1
    move 		$a0, $s0			# $a0 = $s0
    syscall
    li			$v0, 4				# $v0 = 4
    la			$a0, m_not_square
    syscall		
    j			q3_exit				# jump to q3_exit

square_function:
    li			$v0, 1				# $v0 = 1
    move 		$a0, $s0			# $a0 = $s0
    syscall
    li			$v0, 4				# $v0 = 4
    la			$a0, m_square			# 
    syscall
    li			$v0, 1				# $v0 = 1
    move 		$a0, $s4			# $a0 = $s4
    syscall
    li			$v0, 4				# $v0 = 4
    la			$a0, m_square_1			# 
    syscall
    li			$s5, 0				# $s5 = 0
    j			print_distinct_primes				# jump to print_distinct_primes
    
print_distinct_primes:
    beq			$s5, $s4, q3_exit	# if $s5 == $s4 then q3_exit
    sll			$t0, $s5, 2			# $t0 = $s5 << 2
    add			$t0, $t0, $s6		# $t0 = $t0 + $s6
    lw			$t1, ($t0)			# 
    li			$v0, 1				# $v0 = 1
    move 		$a0, $t1			# $a0 = $t1
    syscall
    li			$v0, 4				# $v0 = 4
    la			$a0, space			# 
    syscall
    addi		$s5, $s5, 1			# $s5 = $s5 + 1
    j			print_distinct_primes				# jump to print_distinct_primes
    
    



prime_square:
    li			$v0, 1				# $v0 = 1
    move 		$a0, $s0			# $a0 = $s0
    syscall
    li			$v0, 4				# $v0 = 4
    la			$a0, m_square_2			# 
    syscall
    li			$v0, 1				# $v0 = 1
    move 		$a0, $s0			# $a0 = $t3
    syscall
    j			q3_exit				# jump to q3_exit
    
    
    


    

q3_exit:
    j			main_menu				# jump to main_menu
    
exit:
    li			$v0, 4				# $v0 = 4
    la			$a0, menu_exit_message			# 
    syscall
    li			$v0, 10				# $v0 = 10
    syscall    
