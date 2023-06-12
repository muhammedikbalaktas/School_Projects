import java.util.Random;
public class InputGenerator {
    int[] generateArray(int size){
        Random random=new Random();
        int[] arr=new int[size];
        for (int i=0;i<size;i++){
            arr[i]=random.nextInt(1,size);
        }
        return arr;
    }





}
