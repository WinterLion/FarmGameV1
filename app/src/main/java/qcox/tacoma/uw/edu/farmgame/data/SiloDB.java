package qcox.tacoma.uw.edu.farmgame.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cox Family on 4/30/2016.
 */
public class SiloDB {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "farminggame.db";

    private SiloDBHelper mSiloDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public SiloDB(Context context) {
        mSiloDBHelper = new SiloDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mSiloDBHelper.getWritableDatabase();
    }



    /**
     * Inserts the inventory into the local sqlite table. Returns true if successful, false otherwise.
     * @param id
     * @param name
     * @param itemType
     * @param itemTypeDetail
     * @param quantity
     * @param buyCost
     * @param sellCost
     * @param growTime
     * @param description
     * @param imageName
     * @return true or false
     */
    public boolean insertInventory(String id, String name, String itemType, String itemTypeDetail
                                 ,Integer quantity, Integer buyCost, Integer sellCost, Integer growTime
                                 ,String description, String imageName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("itemType", itemType);
        contentValues.put("itemTypeDetail", itemTypeDetail);
        contentValues.put("quantity", quantity);
        contentValues.put("buyCost", buyCost);
        contentValues.put("sellCost", sellCost);
        contentValues.put("growTime", growTime);
        contentValues.put("description", description);
        contentValues.put("imageName", imageName);

        long rowId = mSQLiteDatabase.insert("Inventory", null, contentValues);
        return rowId != -1;
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }


    class SiloDBHelper extends SQLiteOpenHelper {
//        String id = "basic_wheat";
//        String name = "Wheat";
//        String itemType = "plant";
//        String itemTypeDetail = "";
//        int quantity = 1;
//        int buyCost = 5;
//        int sellCost = 3;
//        int growTime = 10;
//        String description = "Wheat is easy to grow but not worth much";
//        String imageName = "wheat";

        private static final String CREATE_INVENTORY_SQL =
                "CREATE TABLE IF NOT EXISTS Inventory "
                        + "(id TEXT PRIMARY KEY, name TEXT, itemType TEXT, itemTypeDetail TEXT, " +
                        "quantity INTEGER, buyCost INTEGER, sellCost INTEGER, growTime INTEGER, " +
                        "description TEXT, imageName TEXT)";

        private static final String DROP_INVENTORY_SQL =
                "DROP TABLE IF EXISTS Inventory";

        public SiloDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_INVENTORY_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_INVENTORY_SQL);
            onCreate(sqLiteDatabase);
        }
    }
}

