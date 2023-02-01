package me.ddrkhat.spawnborder;

public class ChunkArea
{
    /*
    This is kinda extra and another way is probably possible to store these ints in a readable way.
    This is one way I've learned how so I'll use it.
     */
    int startX = 0;
    int endX = 0;
    int startZ = 0;
    int endZ = 0;
    int xDifference = 0;
    int zDifference = 0;

    public void setStartX(int newNum)
    {
        startX = newNum;
        calcXDiff();
    }

    public void setStartZ(int newNum)
    {
        startZ = newNum;
        calcZDiff();
    }

    public void setEndX(int newNum)
    {
        endX = newNum;
        calcXDiff();
    }

    public void setEndZ(int newNum)
    {
        endZ = newNum;
        calcZDiff();
    }

    public void calcXDiff()
    {
        xDifference = Math.abs(startX - endX);
    }

    public void calcZDiff()
    {
        zDifference = Math.abs(startZ - endZ);
    }

    public int getStartX()
    {
        return startX;
    }

    public int getStartZ()
    {
        return startZ;
    }

    public int getEndX()
    {
        return endX;
    }

    public int getEndZ()
    {
        return endZ;
    }

    public int getxDifference()
    {
        return xDifference;
    }

    public int getzDifference()
    {
        return zDifference;
    }

}
