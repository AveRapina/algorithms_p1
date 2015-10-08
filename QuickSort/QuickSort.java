import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class QuickSort
{
    public static void sort(Comparable[] array)
    {
        //Randomize the array prior to sorting it. This is needed for this specific
        //implementation since the leftmost element in the array is always picked as the pivot when partitioning.
        //This is actually inefficient since shuffling the array takes additional time. It would be more efficient to 
        //choose the pivot in such a way that quicksorting an already sorted array does not take quadratic time.
        StdRandom.shuffle(array);

        sort(array, 0, array.length - 1);
    }

    private static void sort(Comparable[] array, int lo, int hi)
    {
        //Base case where the array size is 0 or 1
        if (hi <= lo)
        {
            return;
        }

        int sortedPivotIndex = partition(array, lo, hi);
   
        //Recursively divide the array into halves and sort each half
        sort(array, lo, sortedPivotIndex - 1);
        sort(array, sortedPivotIndex + 1, hi);
    }

    /*
     * Partition some array with a lower bound index, lo, and an upper bound index, hi such that 
     * there is a pivot somewhere in the array where all elements to the left of the pivot are less than
     * the pivot and all elements to the right of the pivot are greater than the pivot.
     *
     * The index of the pivot is returned bythe function.
     */
    private static int partition(Comparable[] array, int lo, int hi)
    {
        //The pivot value by default is the value at index lo.

        int i = lo + 1;
        int j = hi;

        while (true) 
        {
            //Let the pivot be the value at index lo.

            //Increment i until the value at i is greater than the pivot, or until i reaches the end of the array. 
            while ((array[i].compareTo(array[lo]) != 1) && (i != hi))
            {
                i++;
            }

            //Decrement j until the value at j is less than the pivot, or until j reaches the pivot index. 
            while ((array[j].compareTo(array[lo]) != -1) && (j != lo))
            {
                j--;
            }

            //If i and j have crossed or are pointing at the same element, break out.
            //Otherwise, swap the items at those indices.
            if (j <= i)
            {
                break;
            }
            else
            {
                swap(array, i, j);
            }
        }
        
        //Move the pivot into place and return the index of the pivot.
        swap(array, lo, j);
        return j;
    }

    /*
     * Swap the elements at i and j in array.
     */
    private static void swap(Comparable[] array, int i, int j)
    {
        Comparable temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }



    public static void main(String[] args)
    {
        int numElements = 10000000;
        Comparable[] array = new Comparable[numElements];
        
        for (int i = 0; i < numElements; i++)
        {
            array[i] = StdRandom.uniform(numElements);
        }
        
        Stopwatch s = new Stopwatch();
        QuickSort.sort(array);
        System.out.printf("Sorting time for %d elements: %f seconds\n", numElements, s.elapsedTime());

        //Primitive way of asserting that the array is sorted.
        for (int i = 0; i < numElements - 1; i++)
        {
            if (array[i].compareTo(array[i + 1]) == 1)
            {
                throw new java.lang.UnsupportedOperationException();
            }
        }
    }
}
