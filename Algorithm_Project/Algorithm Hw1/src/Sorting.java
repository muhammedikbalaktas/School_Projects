public class Sorting {
    //*****************************Merge Sort*******************************************************
    private  void merge(int[] a, int[] l, int[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    private   void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);
    }
    public int mergeSortKthValue(int []arr,int k){

        this.mergeSort(arr,arr.length);
        return arr[k];
    }
    //*****************************Insertion Sort*******************************************************
    public int insertionSort(int [] arr,int k){//works

        for(int i=0;i<arr.length;++i){

            int j = i;

            while(j > 0 && arr[j-1]>arr[j]){

                int key = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = key;
                j = j-1;

            }
        }
        return arr[k];
    }
    //*********************Selection sort*******************************
    private   void selectionSort(final int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minElementIndex] > arr[j]) {
                    minElementIndex = j;
                }
            }

            if (minElementIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minElementIndex];
                arr[minElementIndex] = temp;
            }
        }
    }
    int [] partialSelectionSort(int arr[], int k) {

        for (int i=0;i<k;i++){
            int minIndex=i;
            int minValue=arr[i];
            for(int j=i+1;j<arr.length;j++){
                if(arr[j]<minValue){
                    minIndex=j;
                    minValue=arr[j];
                    int temp=arr[i];
                    arr[i]=arr[minIndex];
                    arr[minIndex]=temp;

                }
            }
        }
        return arr;
    }

    //*************************Quick Sort**************************************************

    // Java program of Quick Select
    private void leftMostQuicSort(int[] array, int l, int r) {//leftmost pivot
        if (l < r) {
            // select pivot element (left-most)
            int pivot = array[l];
            // partition and shuffle around pivot
            int i = l;
            int j = r;
            while (i < j) {
                // move right to avoid pivot element
                i += 1;
                // scan right: find elements greater than pivot
                while (i <= r && array[i] < pivot) {
                    i += 1;
                }
                // scan left: find elements smaller than pivot
                while (j >= l && array[j] > pivot) {
                    j -= 1;
                }
                if (i <= r && i < j) {
                    // swap around pivot
                    swap1(array, i, j);
                }
            }
            // put pivot in correct place
            swap1(array, l, j);
            // sort partitions
            leftMostQuicSort(array, l, j - 1);
            leftMostQuicSort(array, j + 1, r);
        }

    }


    private void swap1(int[] array, int i, int j) {
        if (i >= 0 && j >= 0 && i < array.length && j < array.length) {
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

    public int leftMostQuickSortKthValue(int []array,int k){
        this.leftMostQuicSort(array,0, array.length-1);
        return array[k];
    }




    //*****************************Left Most Quick Select*************************

    public static void swap(int[] arr, int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int partition(int[] arr, int pivot, int lo, int hi) {

        int i = lo, j = lo;
        while (i <= hi) {
            if (arr[i] <= pivot) {
                swap(arr, i, j);
                i++;
                j++;
            } else {
                i++;
            }
        }

        return (j - 1);
    }
    public  int quickSelect(int []arr,int lo,int hi,int k){
        int pivot=arr[hi];
        int pi=partition(arr,pivot,lo,hi);
        if(k>pi){
            return quickSelect(arr,pi+1,hi,k);

        }
        else if(k<pi){
            return quickSelect(arr,lo,pi-1,k);

        }
        else return pivot;
    }



    //***********************Three median pivot*****************************

    public  int quickSelectMedianOfThree(int []arr,int lo,int hi,int k){
        int pivot=(arr[0]+arr[arr.length-1]+arr[arr.length/2])/3;
        int pi=partition(arr,pivot,lo,hi);
        if(k>pi){
            return quickSelect(arr,pi+1,hi,k);

        }
        else if(k<pi){
            return quickSelect(arr,lo,pi-1,k);

        }
        else return pivot;
    }


    //****************************Heap Sort**************************************

    public void heapSort(int arr[]) {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }
    private void heapify(int arr[], int n, int i) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }





    }





