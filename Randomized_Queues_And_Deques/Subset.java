
import edu.princeton.cs.algs4.StdIn;

public class Subset
{
    public static void main(String[] args)
    {
        int numOutputs = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        //Populate the randomized queue
        while(!StdIn.isEmpty())
        {
            rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < numOutputs; i++)
        {
            System.out.println(rq.dequeue());
        }
    }
}
