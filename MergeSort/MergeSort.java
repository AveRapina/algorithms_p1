import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

/*
 * Sorting algorithm where an array is sorted by recursively dividing it into halves, 
 * sorting those halves, and merging the halves together.
 *
 * An auxillary array is used to store the latest sorted values during merging. 
 */
public class MergeSort
{
    public static void sort(Comparable[] array)
    {
        //Make an empty array so values can be stored during the merge process
        Comparable[] aux = new Comparable[array.length];

        sort(array, aux, 0, array.length - 1);
    }

    private static void sort(Comparable[] array, Comparable[] aux, int lo, int hi)
    {
        //Base case where the array size is 1
        if (lo == hi)
        {
            return;
        }

        //Recursively divide the array into halves and sort each half
        int mid = lo + (hi - lo) / 2;
        sort(array, aux, lo, mid);
        sort(array, aux, mid + 1, hi);

        //Merge the two sorted halves
        merge(array, aux, lo, mid, hi);
    }

    private static void merge(Comparable[] array, Comparable[] aux, int lo, int mid, int hi)
    {
        //Copy the values being merged into aux.
        //This needs to be done here so tht aux is always using the latest sorted values.
        for (int i = lo; i <= hi; i++)
        {
            aux[i] = array[i];
        }

        int leftArrayIndex = lo;
        int rightArrayIndex = mid + 1;

        for (int i = lo; i <= hi; i++)
        {
            if (leftArrayIndex > mid)
            {
                //Left half has been fully merged, so insert from the right half. 
                array[i] = aux[rightArrayIndex];
                rightArrayIndex++;
            }
            else if (rightArrayIndex > hi)
            {
                //Right half has been fully merged, so insert from the left half.
                array[i] = aux[leftArrayIndex];
                leftArrayIndex++;
            }
            else if (aux[leftArrayIndex].compareTo(aux[rightArrayIndex]) == 1)
            {
                //Current value in the left half is greater than the current value in the right half.
                //Insert from the right half.
                array[i] = aux[rightArrayIndex];
                rightArrayIndex++;
            }
            else
            {
                //Current values in the left half and right half are equal OR
                //Current value in the left half is less than the current value in the right half.
                //Insert from the left half.
                array[i] = aux[leftArrayIndex];
                leftArrayIndex++;
            }
        }
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
        MergeSort.sort(array);
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
