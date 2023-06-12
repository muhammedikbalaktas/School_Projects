#this shell scripts copies all files with write permisson for user
#to a new directory under a new directory created via this script 
#called writable
#
if [[ $# -gt 0 ]]; then
	echo "please do not provide an input"
	exit 0
fi
mkdir ./writable
size=0
for file in *
do

if [ -f $file ]
then

if [ -r $file  ]
then
cp $file ./writable;
let size+=1

fi


fi

done

echo "$size files moved to writable directory"
