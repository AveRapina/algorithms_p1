import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{
    private double[] percolationThresholds;

    /**
     * Perform T independent experiments on an N sized percolation systme.
     */
    public PercolationStats(int N, int T)
    {
        if ((N <= 0) || (T <= 0))
        {
            throw new IllegalArgumentException();
        }
        
        percolationThresholds = new double[T];
        
        for (int i = 0; i < T; i++)
        {
            Percolation p = new Percolation(N);
            int numOpenSites = 0;
           
            //Open sites at random until the system percolates. 
            while (!p.percolates())
            {
                int x = StdRandom.uniform(1, N + 1);
                int y = StdRandom.uniform(1, N + 1);

                if (!p.isOpen(x, y))
                {
                    p.open(x, y);
                    numOpenSites++;
                }
            }

            percolationThresholds[i] = (double) numOpenSites / (N * N);
        }
    }

    /**
     * Determine sample mean of percolation threshold.
     */
    public double mean()
    {
        return StdStats.mean(percolationThresholds); 
    }

    /**
     * Determine standard deviation of percolation threshold.
     */
    public double stddev()
    {
        return StdStats.stddev(percolationThresholds); 
    }

    /**
     * Determine low endpoint of a 95% confidence interval.
     */
    public double confidenceLo()
    {
        return mean() - 1.96 * (stddev() / Math.sqrt(percolationThresholds.length));
    }

    /**
     * Determine high endpoint of a 95% confidence interval.
     */
    public double confidenceHi()
    {
        return mean() + 1.96 * (stddev() / Math.sqrt(percolationThresholds.length));
    }

    public static void main(String[] args)
    {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(N, T);

        System.out.printf("mean\t\t\t= %f\n", stats.mean());
        System.out.printf("stddev\t\t\t= %f\n", stats.stddev());
        System.out.printf("95%% confidence interval = %f, %f", stats.confidenceLo(), stats.confidenceHi());
    }
}
