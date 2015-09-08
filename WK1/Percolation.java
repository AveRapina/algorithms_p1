import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Any methods with parameters i and j expect a value between 1 and N,
 * where (1, 1) is the upper left site.
 */
public class Percolation
{
    private WeightedQuickUnionUF sites;
    private WeightedQuickUnionUF backwashSites;
    private boolean[] siteStates;

    private int N;
    private int virtualTopSiteIndex;
    private int virtualBottomSiteIndex;

    //Creates N by N grid with all sites blocked
    public Percolation(int N)
    {
        if (N <= 0)
        {
            throw new IllegalArgumentException();
        }

        this.N = N;
        int numSites = N * N; 
        
        //Indices 0 to N-1 correspond to non-virtual sites.
        virtualTopSiteIndex = numSites;
        virtualBottomSiteIndex = numSites + 1;

        //Add two virtual sites used to determine if the system percolates.
        sites = new WeightedQuickUnionUF(numSites + 2);
        siteStates = new boolean[numSites];

        //doesn't have virtual bottom site 
        backwashSites = new WeightedQuickUnionUF(numSites + 1);
    }

    //Open a site. If open already, np.
    public void open(int i, int j)
    {
        if (isOpen(i, j))
        {
            return;
        }

        //Open the current site.
        int siteIndex = convert2Dto1D(i, j);
        siteStates[siteIndex] = true;

        //Connect the current site to the adjacent top site.
        //If the current site is on the top row, connect to the virtual top site.
        if (i == 1)
        {
            sites.union(siteIndex, virtualTopSiteIndex);
            
            backwashSites.union(siteIndex, virtualTopSiteIndex);
        }
        else if (isOpen(i - 1, j))
        {
            int adjacentTopSiteIndex = convert2Dto1D(i - 1, j);
            sites.union(siteIndex, adjacentTopSiteIndex);
            
            backwashSites.union(siteIndex, adjacentTopSiteIndex);
        }

        //Connect the current site to the adjacent left site.
        //If the current site is on the leftmost column, do nothing.
        if ((j != 1) && isOpen(i, j - 1))
        { 
            int adjacentLeftSiteIndex = convert2Dto1D(i, j - 1);
            sites.union(siteIndex, adjacentLeftSiteIndex);
            
            backwashSites.union(siteIndex, adjacentLeftSiteIndex);
        }

        //Connect the current site to the adjacent right site.
        //If the current site is on the rightmost column, do nothing. 
        if ((j != N) && isOpen(i, j + 1))
        {
            int adjacentRightSiteIndex = convert2Dto1D(i, j + 1);
            sites.union(siteIndex, adjacentRightSiteIndex);
            
            backwashSites.union(siteIndex, adjacentRightSiteIndex);
        }

        //Connect to the adjacent site on bottom of the current site.
        //If top row, connect to the faux bottom site.
        if (i == N)
        {
            sites.union(siteIndex, virtualBottomSiteIndex);
        }
        else if (isOpen(i + 1, j))
        {
            int adjacentBottomSiteIndex = convert2Dto1D(i + 1, j);
            sites.union(siteIndex, adjacentBottomSiteIndex);
            
            backwashSites.union(siteIndex, adjacentBottomSiteIndex);
        }
    }

    /**
     * Determine if a site is open or not.
     */
    public boolean isOpen(int i, int j)
    {
        int siteIndex = convert2Dto1D(i, j);
        return siteStates[siteIndex];
    }

    /**
     * Determine if a site is full or not.
     * A full site is a site that is connected to a site on the top row.
     */
    public boolean isFull(int i, int j)
    {
        if (!isOpen(i, j))
        {
            return false;
        }
        
        int siteIndex = convert2Dto1D(i, j);

        //return sites.connected(siteIndex, virtualTopSiteIndex);
        return backwashSites.connected(siteIndex, virtualTopSiteIndex);
    }

    /**
     * Determine if the system percolates.
     */
    public boolean percolates()
    {
        return sites.connected(virtualTopSiteIndex, virtualBottomSiteIndex);
    }



    /**
     * Converts 2D coordinates, i and j, to a one dimensional index.
     */
    private int convert2Dto1D(int i, int j)
    {
        validate2D(i, j);
        return (i - 1) * N + (j - 1);
    }

    /**
     * Validate 2D coordinates, i and j.
     */
    private void validate2D(int i, int j)
    {
        if ((i < 1) || (i > N))
        {
            throw new IndexOutOfBoundsException("Row index i is out of range.");
        }
        
        if ((j < 1) || (j > N))
        {
            throw new IndexOutOfBoundsException("Column index j is out of range.");
        }
    }
} 
