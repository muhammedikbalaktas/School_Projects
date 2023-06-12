string_input=$1
integer_input=$2

#not given argument
if [[ (-z $string_input) && (-z $integer_input) ]]
then
	echo "There is no given argument"
	exit 0
	echo " "
fi



#first argument must be lower case str
for ((j = 0; j < ${#string_input}; j++))
do
	printf -v st_integer %d "'${string_input:j:1}"
	if [[ $st_integer -gt 96  ]] &&  [[ $st_integer -lt 123 ]] 
	then
		continue
	else
		echo "Your first input must be lower case string"
		exit 0
		echo " "
	fi
done

#second argument must be int
for ((j = 0; j < ${#integer_input}; j++))
do
	
	if [ "${integer_input:j:1}" -eq 0 -o "${integer_input:j:1}" -ne 0 ];
	then
		continue
	else
		echo "Your second input must be number"
		exit 0
		echo " "
	fi
done



#digits are same or not
if [ ${#string_input} -ne ${#integer_input} ] && [ ${#integer_input} -ne 1 ]
then
	echo "Your first and second argument's digits must be match or you must need to provide one digit number for the second argument"	
	exit 0
	echo " "
fi


same_digit(){

printf -v int_value_ascii %d "'${integer_input:j:1}" 
printf -v changed_ascii_value \\$(printf '%o' "$int_value_ascii") 
if [ ` expr $int_ascii_of_str + $changed_ascii_value ` -le 122 ]
then
	(( int_ascii_of_str = int_ascii_of_str + $changed_ascii_value ))
else
	int_ascii_of_str=`expr 96 +  $int_ascii_of_str + $changed_ascii_value - 122`
fi

}	


one_digit(){

if [ `expr $int_ascii_of_str + $integer_input` -le 122 ]
then
	(( int_ascii_of_str = int_ascii_of_str + $integer_input))
else
	int_ascii_of_str=`expr 96 +  $int_ascii_of_str + $integer_input - 122`
fi

}


write_to_console(){

printf \\$(printf '%o' "$int_ascii_of_str")
echo -n "$new_str"

}	
	
#calling functions
for ((j = 0; j < ${#string_input}; j++))
do
printf -v int_ascii_of_str %d "'${string_input:j:1}" 
if [ ${#integer_input} -ne 1 ]
then
	same_digit 
	write_to_console
	
else
	one_digit
	write_to_console
fi
done
echo " "







