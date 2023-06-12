import java.time.Duration;
import java.time.Instant;
//Muhammed ikbal aktaş 150119710
//Yasin Enes Şişik 150119807
class Main {



        public static void main( String args[] ) {
            InputGenerator inputGenerator=new InputGenerator();
            int size=10000;
            Sorting sorting=new Sorting();
            int []arr=inputGenerator.generateArray(size);
            long startTime = System.nanoTime();
            int k=size-100;

            sorting.insertionSort(arr,k);


            long endTime = System.nanoTime();
            long duration = (endTime - startTime);

            System.out.println(duration/1000000);









        }
        public static void calculate(){
            InputGenerator inputGenerator=new InputGenerator();
            int size=1000000;
            Sorting sorting=new Sorting();
            int []arr=inputGenerator.generateArray(size);
            long startTime = System.nanoTime();



            sorting.partialSelectionSort(arr,size/2);

            long endTime = System.nanoTime();
            long duration = (endTime - startTime);

            System.out.println(duration/1000000);

        }
    }

