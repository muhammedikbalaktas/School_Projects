.data

q1_phase1:  .space 101
q1_phase2:  .space 101
endl: .asciiz "\n"
palindrome_message: .asciiz "the longest palindrome is "
palindrome_message2: .asciiz " and it length is ==> "


.text
q1_main:
    ######################################################################
    # Read the string input from the user
    li      $v0, 9          # system call for allocating memory
    li      $a0, 100        # allocate 100 bytes of memory
    syscall
    move    $s5, $v0        # save the address of the new memory block

    li      $v0, 8          # system call for reading a string
    move      $a0, $s5   # load the address of the input buffer
    li      $a1, 100        # maximum length of input is 100 bytes
    syscall
    ######################################################################
    move      $t1,        $s5
    move      $t5,        $s5,       #
    #allocate memory for arrays
    li $a0, 400          # Load the size of the array into $a0 (100 * 4 bytes = 400 bytes)
    li $v0, 9            
    syscall              
    move $s7, $v0   

    li			$a0, 400				# $a0 = 400
    li			$v0, 9				# $v0 = 9
    syscall
    move 		$s6, $v0			# $s6 = $v0


    li      $s3,        0               # $s3 = 0 size of input
    li     $s4,        0                # $s4 = 0 number of occurrence for each char
    la			$t9, q1_phase1 			# 

    addi		$t9, $t9, 100			# $t9 = $t9 + 49
    sb			$zero, ($t9)			# 
    la			$t9, q1_phase2 			# 

    addi		$t9, $t9, 100			# $t9 = $t9 + 49
    sb			$zero, ($t9)			# 
    
    



    #######CONVERTTHEINPUTINTOLOWERCASEIFITCANBECONVERTED,  TO,             LOWERCASE################ #
    ########################################################################################### #
nextCh:
    lb      $t2,        ($t1)
    beqz    $t2,        reload
    addi    $s3,        $s3,            1          # $s3 = $s3 + 1 increment size

    li      $t4,        97
    li      $t5,        65                        # $t5 = 65
    li      $t6,        91                        # $t6 = 90
    slt     $t0,        $t2,            $t5
    beq     $t0,        1,              l1        # if $t0 == 1 then l1
    slt     $t0,        $t2,            $t6       # if $t2<$t6 then $t0 =1
    bne     $t0,        1,              l1        # if $t0 != 1 then l1
    sub     $t3,        $t2,            $t4
    bgez    $t3,        l1       # branch on greater than or equal to zero
    add     $t2,        $t2,            32
    sb      $t2,        ($t1)
l1:
    add     $t1,        $t1,            1
    j       nextCh
    #######CONVERTTHEINPUTINTOLOWERCASEIFITCANBECONVERTED,  TO,             LOWERCASE################ #
    ########################################################################################### #
reload:
    move      $t1,        $s5
    move      $t5,        $s5       #
    move      $s2,        $s7  
    li      $t3,        0          #it is offset for occurance

    j       outerLoop               # jump to outerLoop



outerLoop:
    li     $s4,        0                # $s4 = 0 number of occurrence for each char
    lb      $t2,        ($t1)    #address of input
    move			$t5, $s5			# 
     
    
    beqz    $t2,    reload_for_second
    addi		$t1, $t1, 1			# $t1 = $t1 + 1
    j			innerLoop				# jump to outerLoop

innerLoop:
    lb			$t4, ($t5)			# 
    beqz        $t4, update_occurancy
    beq			$t2, $t4, add_occurancy	# if $t2 == $t4 then add_occurancy
    
    addi		$t5, $t5, 1			# $t5 = $t5 + 1
    j			innerLoop				# jump to innerLoop
    
  
add_occurancy:
    addi		$s4, $s4, 1		# $s4 = $s4 + 1 number of occurancy
    addi		$t5, $t5, 1			# $t5 = $t5 + 1
    j			innerLoop				# jump to innerLoop
    
update_occurancy:
    sll			$t6, $t3, 2			# $t3 = $t3 << 2 offset for occurancy array
    add			$s0, $s2, $t6		# $s2 = $s2 + $t3 $t6 is address of occurancy array set offset
    sw			$s4, ($s0)			# 
    addi		$t3, $t3, 1			# $t3 = $t3 + 1
    j			outerLoop				# jump to outerLoop
       
##############################PHASE 2################################################
#####################################################################################
reload_for_second:
    #$s7 for occurance address
    #$s6 is for second phase occurance
    #$s3 is size of input
    
    move			$s0, $s5
    move			$t7, $s5			# 
    			 
    li			$s3, 0				# $s3 = 0  reset the size
    li			$s1, 0				# $s1 = 0  for offset counter
    li			$s4, 0				# $s4 = 0
    li			$t8, 0				# $t8 = 0
    li			$t9, 0				# $t9 = 0
    
    j			delete_redundant				# jump to delete_redundant

delete_redundant:#outer loop
    lb			$t1, ($s0)
    move			$t7, $s5			# 
    
    beqz        $t1, delete_duplicate
    li $t2, 97                  # Load ASCII value of 'a' into register $t2
    li $t5, 122                 # Load ASCII value of 'z' into register $t3
    blt $t1, $t2, countinue_to_delete  # If char < 'a', jump to not_a_letter
    bgt $t1, $t5, countinue_to_delete  # If char > 'z', jump to not_a_letter
    sll			$t3, $s1, 2			# $t3 = $s1 << 2
    add			$t3, $s7, $t3		# $t3 = $s7 + $t3
    lw			$t4, ($t3)			# 
    move 		$t5, $s1			# $t5 = $s1
    beq			$t4, 1, countinue_to_delete	# if $t4 == $t2 then countinue_to_delete
    
    j			update_second_phase				# jump to set_most_occurance
    

    

update_second_phase:
    la			$s2, q1_phase1			# 
    add			$t8, $t9, $s2		# $t8 = $t9 + $s2
    sb			$t1, ($t8)			# 
    sll			$t1, $t9, 2			# $t7 = $t9 << 2
    add			$t5, $t1, $s6		# $t6 = $t7 + $s6
    sw			$t4, ($t5)			# 
    addi		$t9, $t9, 1			# $t9 = $t9 + 1
    addi		$s3, $s3, 1			# $s3 = $s3 + 1
    
    j			countinue_to_delete				# jump to countinue_to_delete
    
countinue_to_delete:
    addi		$s1, $s1, 1			# $s1 = $s1 + 1
    addi		$s0, $s0, 1			# $s0 = $s0 + 1
    j			delete_redundant				# jump to delete_redundant    






##############################PHASE 2################################################
#####################################################################################


delete_duplicate:
    #now first iteration string is stored in q1_phase1 
    #and first iteration int array is stored in $s6
    li			$a0, 400				# $a0 = 400
    li			$v0, 9				# $v0 = 9
    syscall
    move 		$s7, $v0			# $s6 = $v0
    li			$t5, 0				# $t5 = 0
    la			$s0, q1_phase1			# 
    li			$s2, 0				# $s3 = 0 size for result
    li			$t1, 0				# $t1 = 0
    li			$s3, 0				# $s3 = 0
    la			$s4, q1_phase2			# 
    
    

    j			start_scan				# jump to start_scan
    
start_scan:
    lb			$t0, ($s0)			# 
    beqz        $t0,  divide_numbers
    sll			$t6, $t5, 2			# $t6 = $t5 << 2
    add			$t6, $t6, $s6		# $t6 = $t6 + $s6
    lw			$t7, ($t6)			# 
    la			$s1, q1_phase2			# 
    j			inner_scan				# jump to inner_scan
go_next:
    addi		$s0, $s0, 1			# $s0 = $s0 + 1
    addi		$t5, $t5, 1			# $t5 = $t5 + 1
    j			start_scan				# jump to start_scan
   
inner_scan:
    lb			$t2, ($s1)			# 
    beqz        $t2,  add_new

    beq			$t2, $t0, go_next	# if $t2 == $t0 then go_next
    j			next_inner				# jump to next_inner
    
next_inner:
    addi		$s1, $s1, 1			# $s1 = $s1 + 1
    j			inner_scan				# jump to inner_scan
    
    

add_new:
    add			$t4, $t1, $s4		# $t4 = $t1 + $s4
    sb			$t0, ($t4)			# 
    sll			$t4, $t1, 2			# $t4 = $t1 << 2
    add			$t4, $s7, $t4		# $t4 = $s7 + $t4
    sw			$t7, 0($t4)			# 
    addi		$t1, $t1, 1			# $t1 = $t1 + 1
    addi		$s3, $s3, 1			# $s3 = $s3 + 1
    
    j			go_next				# jump to start_scan
    
    

    
divide_numbers:
    li			$t0, 0				# $t0 = 0
    li			$t5, 2				# $t5 = 2
    j			divide				# jump to get_occurance
    

divide:
    beq			$t0, $s3, print_result	# if $t0 == $s3 then final_exit
    sll			$t1, $t0, 2			# $t1 = $t0 << 2
    add			$t3, $t1, $s7		# $t3 = $t1 + $s7
    lw			$t4, ($t3)			# 
    div         $t4, $t5    # Divide $s0 by $s1
    mflo        $t7        # Move quotient to $t0
    sw			$t7, ($t3)			# 
    
    addi		$t0, $t0, 1			# $t0 = $t0 + 1
    j			divide				# jump to divide
    


print_result:
    la			$t0, q1_phase2			# 
    li			$t1, 0				# $t1 = 0
    li			$t4, 0				# $t4 = 0
    li			$v0, 4				# $v0 = 4
    la			$a0, palindrome_message			# 
    syscall
    j			print				# jump to print
    
    
    
print:
    lb			$t2, ($t0)			# 
    beqz        $t2,  print_backward
    sll			$t3, $t1, 2			# $t3 = $t1 << 2
    add			$t3, $t3, $s7		# $t3 = $t3 + $s6
    lw			$t6, ($t3)			# 
    li			$t4, 0				# $t4 = 0
    j			print_letter				# jump to print_letter
    
print_letter:
    beq			$t4, $t6, increment_print	# if $t4 == $t3 then target
    li			$v0, 11				# $v0 = 11
    move 		$a0, $t2			# $a0 = $t2
    syscall
    addi		$t4, $t4, 1			# $t4 = $t4 + 1
    j			print_letter				# jump to print_letter
    
    
    
increment_print:
    add			$t0, $t0, 1		# $t0 = $t0 + 1
    addi		$t1, $t1, 1			# $t1 = $t1 + 1
    
    j			print				# jump to print
    
###################################################################################3

print_backward:
    
    move 		$t5, $s3			# $t5 = $s3
    addi		$t5, $t5, -1			# $t5 = $t5 + -1
    
    la			$t0, q1_phase2			#
    add			$t0, $t5, $t0		# $t0 = $t5 + q1_phase2
    li			$s5, 0				# $s5 = 0
    
    j			print_letter_backward				# jump to print
    
  
print_letter_backward:
    beq			$t5, -1, final_exit	# if $t5 == -1 then final_exit
    sll			$t1, $t5, 2			# $t1 = $t5 << 2
    lb			$t4, ($t0)			# 
    li			$t2, 0				# $t2 = 0
    add			$t1, $t1, $s7		# $t1 = $t1 + $s7
    lw			$t6, ($t1)			# 
    add			$s5, $s5, $t6		# $s5 = $s5 + $t6
    
    j,          print_them
    
print_them:
    beq			$t2, $t6, continue_backward	# if $t2 == $t6 then continue_backward
    li			$v0, 11				# $v0 = 11
    move 		$a0, $t4			# $a0 = $t4
    syscall
    addi		$t2, $t2, 1			# $t2 = $t2 + 1
    j			print_them				# jump to print_them
    
    


continue_backward:
    addi		$t5, $t5, -1			# $t5 = $t5 + -1
    addi		$t0, $t0, -1			# $t0 = $t0 + -1
    
    j			print_letter_backward				# jump to print_letter_backward
        

final_exit:
    li			$v0, 4				# $v0 = 4
    la			$a0, palindrome_message2			# 
    syscall
    sll			$s5, $s5, 1			# $s5 = $s5 << 2
    
    li			$v0, 1				# $v0 = 1
    move 		$a0, $s5			# $a0 = $s3
    syscall
    
    li			$v0, 10				# $v0 = 10
    syscall
    
    
