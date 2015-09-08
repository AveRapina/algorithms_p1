import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private WeightedQuickUnionUF sites;
    private boolean[] siteStates;

    private int N;
    private int virtualTopSiteIndex;
    private int virtualBottomSiteIndex;


    //Note: i and j are integers between 1 and N, where 1, 1 is the upper
    //left. Throw java.lang.IndexOutOfBoundsException if any argument is
    //out of range.

    //Creates N by N grid with all sites blocked
    public Percolation(int N)
    {
        if (N <= 0)
        {
            throw new IllegalArgumentException();
        }

        this.N = N;
        virtualTopSiteIndex = N * N;
        virtualBottomSiteIndex = N * N + 1;

        //Add 2 sites (last 2 indices) to determine if we percolate
        //Indices from 0 to N-1 correspond to the real sites
        //Index N corresponds to the top fake site
        //Index N + 1 corresponds to the bottom fake site 
        int numSites = N * N; 

        sites = new WeightedQuickUnionUF(numSites + 2);
        siteStates = new boolean[numSites];

        //siteStates[virtualTopSiteIndex] = true;
        //siteStates[virtualBottomSiteIndex] = true;
    }

    //Open a site. If open already, np.
    public void open(int i, int j)
    {
        //must validate i and j here. 

        if (isOpen(i, j))
        {
            return;
        }

        int siteIndex = 2Dto1D(i, j);
        siteStates[siteIndex] = true;

        //Connect to the adjacent site on top of the current site.
        //If top row, connect to the faux top site.
        if (i == 1)
        {
            sites.union(siteIndex, virtualTopSiteIndex);
        }
        else
        {
            if (isOpen(i - 1, j))
            {
                int adjacentTopSiteIndex = 2Dto1D(i - 1, j);
                sites.union(siteIndex, adjacentTopSiteIndex);
            }
        }

        //Connect to the adjacent site on the left of the current site.
        //If the current site is located in the leftmost column, do nothing.
        if (j != 1)
        {
            if (isOpen(i, j - 1))
            {
                int adjacentLeftSiteIndex = 2Dto1D(i, j - 1);
                sites.union(siteIndex, adjacentLeftSiteIndex);
            }
        }

        //Connect to the adjacent site on the right of the current site.
        //If the current site is located in the rightmost column, do nothing. 
        if (j != N)
        {
            if (isOpen(i, j + 1))
            {
                int adjacentRightSiteIndex = 2Dto1D(i, j + 1);
                sites.union(siteIndex, adjacentRightSiteIndex);
            }
        }

        //Connect to the adjacent site on bottom of the current site.
        //If top row, connect to the faux bottom site.
        if (i == N)
        {
            sites.union(siteIndex, virtualBottomSiteIndex);
        }
        else
        {
            if (isOpen(i + 1, j))
            {
                int adjacentBottomSiteIndex = 2Dto1D(i + 1, j);
                sites.union(siteIndex, adjacentBottomSiteIndex);
            }
        }
    }

    public boolean isOpen(int i, int j)
    {
        int siteIndex = 2Dto1D(i, j);
        return siteStates[siteIndex];
    }

    //Determines if a site is full
    public boolean isFull(int i, int j)
    {
        if (!isOpen(i, j))
        {
            return false;
        }
        
        int siteIndex = 2Dto1D(i, j);

        return sites.connected(siteIndex, virtualTopSiteIndex);
    }

    //Checks to see if the system percolates
    public boolean percolates()
    {
        return sites.connected(virtualTopSiteIndex, virtualBottomSiteIndex);
    }

    private int 2Dto1D(int i, int j)
    {
        if ((i < 1) || (i > N) || (j < 1) || (j > N))
        {
            throw new IndexOutOfBoundsException();
        }

        return (i - 1) * N + (j - 1);
    }

    public static void main(String[] args)
    {
    }
 }
