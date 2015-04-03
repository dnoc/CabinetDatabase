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

    private String material;

    private String catalogName;

    public Cabinet(String modNo, int wid, int hei, int dep, String typ, String desFile, String catName)
    {
        modelNum = modNo;
        width = wid;
        height = hei;
        depth = dep;
        type = typ;
        designFile = desFile;
        catalogName = catName;
    }

    public Cabinet(String modNo, int wid, int hei, int dep, String typ, String desFile, String cabName, String mat)
    {
        modelNum = modNo;
        width = wid;
        height = hei;
        depth = dep;
        type = typ;
        designFile = desFile;
        catalogName = cabName;
        material = mat;
    }

    public String getMaterial() { return material; }

    public  String getCatalogName() { return catalogName; }

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
