package eli.cabinetdatabase;

/**
 * Created by Zak on 4/1/2015.
 */
public class Cabinet {

    private String modelNum;

    private int width;

    private int height;

    private int depth;

    private String type;

    private String designFile;

    public Cabinet(String modNo, int wid, int hei, int dep, String typ, String desFile)
    {
        modelNum = modNo;
        width = wid;
        height = hei;
        depth = dep;
        type = typ;
        designFile = desFile;
    }

    public String getModelNum()
    {
        return modelNum;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getDepth()
    {
        return depth;
    }

    public String getType()
    {
        return type;
    }

    public String getDesignFile()
    {
        return designFile;
    }
}
