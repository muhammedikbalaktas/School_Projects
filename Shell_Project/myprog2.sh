if [[ -z $1 ]]
then
	echo "There is no given argument"
	exit 0
	echo " "

elif [[ ! $1 == *".txt"* ]]
then
	echo "You must provide txt file"
	exit 0
	
#when txt file already exists
elif [ -f "$1" ]; 
then 
	echo -n "$1 exists.Do you want it to be modified? (y/n): "
	read input
	if [ $input = "y" ]
	then
	
		> $1 #empty the file
		
		grep  -v -e '^[[:space:]]*$' giris.txt | shuf -n 1 >> $1 # take random line from giris.txt and add to given txt
		echo >> $1
		grep  -v -e '^[[:space:]]*$' gelisme.txt | shuf -n 1 >> $1 # take random line from gelisme.txt and add to given txt
		echo >> $1
		grep  -v -e '^[[:space:]]*$' sonuc.txt | shuf -n 1 >> $1 # take random line from sonuc.txt and add to given txt
		echo >> $1
		
		
	elif [ $input = "n" ]
	then
		echo "exiting the program..."
		exit 1
	else
		echo "provide valid input"
	
	fi

#create txt file	
else
	(> $1)
	
	grep  -v -e '^[[:space:]]*$' giris.txt | shuf -n 1 >> $1 # take random line from giris.txt and add to given txt
	echo >> $1
	grep  -v -e '^[[:space:]]*$' gelisme.txt | shuf -n 1 >> $1 # take random line from gelisme.txt and add to given txt
	echo >> $1
	grep  -v -e '^[[:space:]]*$' sonuc.txt | shuf -n 1 >> $1 # take random line from sonuc.txt and add to given txt
	echo >> $1
	
	echo -n "A random story is created and stored in $1."
	echo " "
	

fi


