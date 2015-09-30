import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
    private int size;
    private int maxSize;
    private Item[] data;

    public RandomizedQueue()
    {
        size = 0;
        maxSize = 0;
        data = null;
    }

    //Copy constructor used to instantiate an iterator.
    private RandomizedQueue(RandomizedQueue<Item> rq)
    {
        this.size = rq.size;
        this.maxSize = rq.maxSize;

        this.data = (Item[]) new Object[maxSize];
        for (int i = 0; i < size; i++)
        {
            data[i] = rq.data[i];
        }
    }
    
    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }
    
    public void enqueue(Item item)
    {
        if (item == null)
        {
            throw new java.lang.NullPointerException();
        }

        if (isEmpty())
        {
            maxSize = 1;
            data = (Item[]) new Object[maxSize];
            data[size] = item;
        }
        else
        {
            //Double the size of the array if the array is full. 
            if (size == maxSize)
            {
                maxSize = maxSize * 2;
                Item[] newData = (Item[]) new Object[maxSize];

                //Copy all elements from previous array into the new array
                for (int i = 0; i < size; i++)
                {
                    newData[i] = data[i];
                }

                //Throw away the old array
                data = newData;
            }
        
            data[size] = item;
        }

        size++;
    }

    public Item dequeue()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException();
        }

        int r = StdRandom.uniform(size);

        //I might need to do a deep copy here...not sure yet.
        Item out = data[r];

        //Copy the rightmost item to index R.
        data[r] = data[size - 1];

        //Set the rightmost item to null to prevent loitering.
        data[size - 1] = null;
        
        size--;

        //Decrease the size of the array if we need to. 
        if ((maxSize > 2) && (size == maxSize / 4))
        {
            maxSize = maxSize / 2;
            Item[] newData = (Item[]) new Object[maxSize];
                
            for (int i = 0; i < size; i++)
            {
                newData[i] = data[i];
            }

            //Throw away the old array
            data = newData;
        }
        
        return out;
    }

    public Item sample()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException();
        }
        
        int r = StdRandom.uniform(size);

        //I might need to do a deep copy here...not sure yet.
        Item out = data[r];

        return out;
    }

    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator(this);
    }

    public static void main(String[] args)
    {
        RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();
        r.enqueue(1);
        r.enqueue(2);
        r.enqueue(3);
        r.enqueue(4);
        r.enqueue(5);
        r.enqueue(6);
        r.enqueue(7);
        r.enqueue(8);
        r.enqueue(9);

        /*
        Iterator<Integer> iter = r.iterator();

        while (iter.hasNext())
        {
            System.out.println(iter.next());
        }
        */

        /*
        while (!r.isEmpty())
        {
            System.out.println(r.dequeue());
        }
        
        r.enqueue(11);
        r.enqueue(12);
        r.enqueue(13);
        r.enqueue(14);
        r.enqueue(15);
        r.enqueue(16);
        r.enqueue(17);
        r.enqueue(18);
        r.enqueue(19);
        
        while (!r.isEmpty())
        {
            System.out.println(r.dequeue());
        }
        */

        /*
        for (int i = 0; i < 100; i++)
        {
            System.out.println(StdRandom.uniform(100));
        }
        System.out.println("Hello!");
        */
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private RandomizedQueue<Item> data;

        public RandomizedQueueIterator(RandomizedQueue<Item> rq)
        {
            data = new RandomizedQueue(rq);
        }

        public boolean hasNext()
        {
            return !data.isEmpty();
        }

        public void remove()
        {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next()
        {
            return (Item) data.dequeue();
        }
    }
}
