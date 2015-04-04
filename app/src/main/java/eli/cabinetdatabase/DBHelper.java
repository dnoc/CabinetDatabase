package eli.cabinetdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

/**
 * Created by Eli on 3/29/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    protected static final String DB_NAME = "catalog.db";
    private static final int VERSION = 1;

    private static SQLiteDatabase DB;

    protected static final String TABLE_CABINET = "cabinet";
    protected static final String COLUMN_CABINET_WIDTH = "width";
    protected static final String COLUMN_CABINET_HEIGHT = "height";
    protected static final String COLUMN_CABINET_DEPTH = "depth";
    protected static final String COLUMN_CABINET_TYPE = "type";
    protected static final String COLUMN_CABINET_DESIGN_FILE = "design_file";
    protected static final String COLUMN_CABINET_MODEL_NUMBER = "model_number";
    protected static final String COLUMN_FK_CATALOG_NAME = "catalog_name";

    //private static final String TABLE_DESIGN = "design";
    //private static final String COLUMN_DESIGN_ID = "id";
    //private static final String COLUMN_DESIGN_IMAGE_FILE = "image_file";

    protected static final String TABLE_CATALOG = "catalog";
    protected static final String COLUMN_CATALOG_NAME = "name";
    protected static final String COLUMN_CATALOG_MATERIAL = "material";
    protected static final String COLUMN_CATALOG_YEAR = "year";

    //private static final String TABLE_SECTION = "section";
    //private static final String COLUMN_SECTION_TYPE = "type";

    public DBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        DB = db;

        //delete an existing instance of the tables if they exist
        //REMOVE THIS FOR FINAL PRODUCT RELEASE
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CABINET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATALOG);

        //Create Cabinet Table
        DB.execSQL("create table " + TABLE_CABINET + " (" + COLUMN_CABINET_MODEL_NUMBER +
            " text primary key, " + COLUMN_CABINET_WIDTH + " integer, " + COLUMN_CABINET_HEIGHT +
            " integer, " + COLUMN_CABINET_DEPTH + " integer, " + COLUMN_CABINET_TYPE +
            " text, " + COLUMN_CABINET_DESIGN_FILE +
            " text, catalog_name text references "
            + TABLE_CATALOG + "(" + COLUMN_CATALOG_NAME + "))");

        //Create Catalog Table
        DB.execSQL("create table " + TABLE_CATALOG + " (" + COLUMN_CATALOG_NAME + " text primary key, " + COLUMN_CATALOG_MATERIAL + " text, " + COLUMN_CATALOG_YEAR + " integer)");

        populateCatalogs();

        populateCabinets();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //schema changes and data massage here on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CABINET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATALOG);
        onCreate(db);
    }

    public SQLiteDatabase open()
    {
        return getReadableDatabase();
    }

    private void populateCatalogs(){
        ContentValues cv = new ContentValues();
        //Insert catalogs
        //Steel catalog
        cv.put(COLUMN_CATALOG_NAME,"Research Collection");
        cv.put(COLUMN_CATALOG_MATERIAL,"Steel");
        cv.put(COLUMN_CATALOG_YEAR,2015);

        DB.insert(TABLE_CATALOG, null, cv);

        cv = new ContentValues();

        //Wood catalog
        cv.put(COLUMN_CATALOG_NAME,"Signature Series");
        cv.put(COLUMN_CATALOG_MATERIAL,"Wood");
        cv.put(COLUMN_CATALOG_YEAR,2015);
        DB.insert(TABLE_CATALOG, null, cv);

    }

    private void populateCabinets(){
        ContentValues cv = new ContentValues();
        //insert cabinets
            //e43cl
        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"e43c352230l");
        cv.put(COLUMN_CABINET_WIDTH,30);
        cv.put(COLUMN_CABINET_HEIGHT,35);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"Standing");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"e43cl.bmp");
        cv.put(COLUMN_FK_CATALOG_NAME, "Research Collection");
        DB.insert(TABLE_CABINET, null, cv);

        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"e43c352236l");
        cv.put(COLUMN_CABINET_WIDTH,36);
        cv.put(COLUMN_CABINET_HEIGHT,35);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"Standing");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"e43cl.bmp");
        cv.put(COLUMN_FK_CATALOG_NAME,"Research Collection");
        DB.insert(TABLE_CABINET, null, cv);

        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"e43c352242l");
        cv.put(COLUMN_CABINET_WIDTH,42);
        cv.put(COLUMN_CABINET_HEIGHT,35);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"Standing");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"e43cl.bmp");
        cv.put(COLUMN_FK_CATALOG_NAME,"Research Collection");
        DB.insert(TABLE_CABINET, null, cv);

        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"e43c352248l");
        cv.put(COLUMN_CABINET_WIDTH,48);
        cv.put(COLUMN_CABINET_HEIGHT,35);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"Standing");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"e43cl.bmp");
        cv.put(COLUMN_FK_CATALOG_NAME,"Research Collection");
        DB.insert(TABLE_CABINET, null, cv);

            //d30w15_24 width
        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"d30w342215");
        cv.put(COLUMN_CABINET_WIDTH,15);
        cv.put(COLUMN_CABINET_HEIGHT,34);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"ADA");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"d30w15_24.bmp");
        cv.put(COLUMN_FK_CATALOG_NAME,"Signature Series");
        DB.insert(TABLE_CABINET, null, cv);

        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"d30w342218");
        cv.put(COLUMN_CABINET_WIDTH,18);
        cv.put(COLUMN_CABINET_HEIGHT,34);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"ADA");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"d30w15_24.bmp");
        cv.put(COLUMN_FK_CATALOG_NAME,"Signature Series");
        DB.insert(TABLE_CABINET, null, cv);

        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"d30w342221");
        cv.put(COLUMN_CABINET_WIDTH,21);
        cv.put(COLUMN_CABINET_HEIGHT,34);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"ADA");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"d30w15_24.bmp");
        cv.put(COLUMN_FK_CATALOG_NAME,"Signature Series");
        DB.insert(TABLE_CABINET, null, cv);

        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"d30w342224");
        cv.put(COLUMN_CABINET_WIDTH,24);
        cv.put(COLUMN_CABINET_HEIGHT,34);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"ADA");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"d30w15_24.bmp");
        cv.put(COLUMN_FK_CATALOG_NAME,"Signature Series");
        DB.insert(TABLE_CABINET, null, cv);

            //w21w_30 height
        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"w21w301217l");
        cv.put(COLUMN_CABINET_WIDTH,17);
        cv.put(COLUMN_CABINET_HEIGHT,30);
        cv.put(COLUMN_CABINET_DEPTH,12);
        cv.put(COLUMN_CABINET_TYPE,"Wall");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"w21wl_30.bmp");
        cv.put(COLUMN_FK_CATALOG_NAME,"Signature Series");
        DB.insert(TABLE_CABINET, null, cv);

        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"w21w301223l");
        cv.put(COLUMN_CABINET_WIDTH,23);
        cv.put(COLUMN_CABINET_HEIGHT,30);
        cv.put(COLUMN_CABINET_DEPTH,12);
        cv.put(COLUMN_CABINET_TYPE,"Wall");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"w21wl_30.bmp");
        cv.put(COLUMN_FK_CATALOG_NAME,"Signature Series");
        DB.insert(TABLE_CABINET, null, cv);

            //d00w15_24 width
        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"d00w342215");
        cv.put(COLUMN_CABINET_WIDTH,15);
        cv.put(COLUMN_CABINET_HEIGHT,34);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"Sitting");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"d00w15_24.bmp");
        cv.put("catalog_name","Signature Series");
        DB.insert(TABLE_CABINET, null, cv);

        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"d00w342218");
        cv.put(COLUMN_CABINET_WIDTH,18);
        cv.put(COLUMN_CABINET_HEIGHT,34);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"Sitting");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"d00w15_24.bmp");
        cv.put("catalog_name","Signature Series");
        DB.insert(TABLE_CABINET, null, cv);

        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"d00w342221");
        cv.put(COLUMN_CABINET_WIDTH,21);
        cv.put(COLUMN_CABINET_HEIGHT,34);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"Sitting");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"d00w15_24.bmp");
        cv.put("catalog_name","Signature Series");
        DB.insert(TABLE_CABINET, null, cv);

        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"d00w342224");
        cv.put(COLUMN_CABINET_WIDTH,24);
        cv.put(COLUMN_CABINET_HEIGHT,34);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"Sitting");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"d00w15_24.bmp");
        cv.put("catalog_name","Signature Series");
        DB.insert(TABLE_CABINET, null, cv);

            //s40m36_48 width
        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"s40m842236");
        cv.put(COLUMN_CABINET_WIDTH,36);
        cv.put(COLUMN_CABINET_HEIGHT,84);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"Full Height");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"s40m36_48.bmp");
        cv.put("catalog_name","Research Collection");
        DB.insert(TABLE_CABINET, null, cv);

        cv = new ContentValues();
        cv.put(COLUMN_CABINET_MODEL_NUMBER,"s40m842248");
        cv.put(COLUMN_CABINET_WIDTH,48);
        cv.put(COLUMN_CABINET_HEIGHT,84);
        cv.put(COLUMN_CABINET_DEPTH,22);
        cv.put(COLUMN_CABINET_TYPE,"Full Height");
        cv.put(COLUMN_CABINET_DESIGN_FILE,"s40m36_48.bmp");
        cv.put("catalog_name","Research Collection");
        DB.insert(TABLE_CABINET, null, cv);
    }

}
