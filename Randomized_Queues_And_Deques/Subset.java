import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;

public class Subset
{
    public static void main(String[] args)
    {
        int numOutputs = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
    
        String[] tokens = StdIn.readAllStrings();

        int numTokens = tokens.length;
        for (int i = 0; i < numOutputs; i++)
        {
            int r = StdRandom.uniform(numTokens);

            rq.enqueue(tokens[r]);
            tokens[r] = tokens[numTokens - 1];
            tokens[numTokens - 1] = null;
            numTokens--;
        }
        
        for (int i = 0; i < numOutputs; i++)
        {
            System.out.println(rq.dequeue());
        }
    }
}
