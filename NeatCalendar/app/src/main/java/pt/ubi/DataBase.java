package pt.ubi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    private static String name = "neatcalendar.db";
    private static final int version = 1;

    public DataBase( Context context) {
        super(context, name, null, version);
    }

    private static final String ITEM_TABLE_CREATE = "CREATE TABLE  Items(id integer primary key autoincrement, nomeItem varchar(80), cnt varchar(5000000000000), description varchar(500), datetime varchar(10))";

    private static final String ITEMHISTORIC_TABLE_CREATE =  "CREATE TABLE  ItemsHistoric(idh integer primary key autoincrement, id integer, cnt varchar(5000000000000), datetime varchar(15))";

    private static final String ITEM_DROP_TABLE = "DROP TABLE Items;";
    private static final String ITEMHISTORIC_DROP_TABLE = "DROP TABLE ITEMS";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ITEM_TABLE_CREATE);
        db.execSQL(ITEMHISTORIC_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
