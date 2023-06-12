public class old {
    //-----------------------Quick sort part-----------------------------------------------------
    private int partition(int arr[], int low, int high)
    {
        int pivot = arr[high];
        int i = (low-1);
        for (int j=low; j<high; j++)
        {
            if (arr[j] <= pivot)
            {
                i++;


                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }


        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
    }



    public int quickSort(int arr[], int low, int high)//works
    {   //java support recursion untill 6000 do not use an array more than it
        /* low  --> Starting index,  high  --> Ending index */
        if (low < high)
        {

            int pi = partition(arr, low, high);


            quickSort(arr, low, pi-1);
            quickSort(arr, pi+1, high);
        }
        return arr[0];
    }
    //-----------------------Quick sort part-----------------------------------------------------
    //----------------------------------------------------------------------------------------------
}
