import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>
{
    private int size;
    private LinkedListNode head;
    private LinkedListNode tail;

    public Deque()
    {
        size = 0;
        head = null;
        tail = null;
    }
    
    public int size()
    {
        return size;
    }
    
    public boolean isEmpty()
    {
        return size() == 0;
    }

    public void addFirst(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException();
        }

        if (head == null)
        {
            head = new LinkedListNode(item);
            tail = head;
        }
        else
        {
            LinkedListNode newHead = new LinkedListNode(item);
            
            newHead.next = head;
            head.prev = newHead;
            
            head = newHead;
        }

        size++;
    }

    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException();
        }

        if (tail == null)
        {
            head = new LinkedListNode(item);
            tail = head;
        }
        else
        {
            LinkedListNode newTail = new LinkedListNode(item);

            newTail.prev = tail;
            tail.next = newTail;

            tail = newTail;
        }

        size++;
    }

    public Item removeFirst()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException();
        }

        Item payload = (Item) head.payload;

        //Remove the current head
        head = head.next;

        //Update the new head if possible.
        if (head != null)
        {
            head.prev = null;
        }

        size--;
        return (Item) payload; 
    }

    public Item removeLast()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException();
        }
        
        Item payload = (Item) tail.payload;

        //Remove the current tail 
        tail = tail.prev;

        //Update the new tail if possible.
        if (tail != null)
        {
            tail.next = null;
        }

        size--;
        return (Item) payload;
    }

    public Iterator<Item> iterator()
    {
        return new DequeIterator(head);
    }

    public static void main(String[] args)
    {
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        d.addFirst(4);

        /*
        while (!d.isEmpty())
        {
            System.out.println(d.removeFirst());
        }
        */


        /*
        for (int i = 0; i < 4; i++)
        {
            System.out.printf("Current Size: %d\n", d.size());
            int x = d.removeLast();
            System.out.println(x);
        }
        */

        Iterator<Integer> i = d.iterator();

        while (i.hasNext())
        {
            System.out.println(i.next());
        }
    }
    
    private class LinkedListNode<Item>
    {
        private Item payload;
        private LinkedListNode next;
        private LinkedListNode prev;

        public LinkedListNode(Item payload)
        {
            this.payload = payload;
            this.next = null;
            this.prev = null;
        }
    }

    private class DequeIterator implements Iterator<Item>
    {
        private LinkedListNode current;

        public DequeIterator(LinkedListNode head)
        {
            this.current = head;
        }

        public boolean hasNext()
        {
            return current != null;
        }

        public void remove()
        {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next()
        {
            Item payload = (Item) current.payload;
            current = current.next;
            return payload;
        }
    }
}
