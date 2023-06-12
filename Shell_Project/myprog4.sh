#this shell script takes a number as an input and convert the
#given number input into the hexadecimal format
#in this script we used an algorithm which is used when converting
#decimal to binary.
#the script does not accept two or more input or none input

if [[ $# -eq 0 || $# -gt 1 ]]; then
   echo "please provide a valid argument"
   exit 1
fi
input=$1


result_array=()
final_array=()
#==>önce input 16 dan büyük mü kontrol edilir
#==>eğer büyükse 16 dan 16 ya bölünür kalan arraya eklenir mod ise yeniye eklenir

while [[ $input -gt 15 ]]; do
   result=$(($input%16))
   if [[ $result -gt 9 ]]; then
      if [[ $result -eq 10 ]]; then
         result_array+=("A")
      elif [[ $result -eq 11 ]]; then
         result_array+=("B")
      elif [[ $result -eq 12 ]]; then
         result_array+=("C")
      elif [[ $result -eq 13 ]]; then
         result_array+=("D")
      elif [[ $result -eq 14 ]]; then
         result_array+=("E")
      elif [[ $result -eq 15 ]]; then
         result_array+=("F")
      fi
      
   else
      result_array+=("$result")
   fi
   
   let input=input/16
done
result_array+=("$input")

# reversing array
for (( i = "${#result_array[@]}"-1; i >= 0 ; i-- )); do
   
   final_array+=("${result_array[$i]}")

done



printf %s "${final_array[@]}" $'\n'