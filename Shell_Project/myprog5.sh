#this shell script takes one or two command line argument.
#if number of argument is one than script ask for all files under
#current directory wheter you want to delete them or not
#if number of arg is two than it works same way for another directory 


if [[ $# -eq 0 || $# -gt 2 ]]; then
	echo "please provide a valid argument"
	exit 1
fi
echo "please provide y or n for decisions"
if [[ $# -eq 1 ]];
then
	find ./ -name "$1" -exec rm -i {} \;
elif [[ $# -eq 2 ]]; then
	find $2 -name "$1" -exec rm -i {} \;
fi