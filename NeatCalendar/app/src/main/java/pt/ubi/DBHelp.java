package pt.ubi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelp {

    private DataBase dataBase;
    private SQLiteDatabase db;

    public  DBHelp (Context context){
        dataBase = new DataBase(context);
        db = dataBase.getWritableDatabase();

    }

    public long inserir(Item item){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        ContentValues values = new ContentValues();
        values.put("nomeItem", item.getNomeItem());
        values.put("cnt", item.getCont());
        values.put("description", item.getDescription());
        values.put("datetime", formatter.format(date) );
        return db.insert("Items", null, values);
    }

    public long inserirHistorico(Item item, ItemHistoric itemH){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        ContentValues values = new ContentValues();
        values.put("id", item.getID());
        values.put("cnt", itemH.getCnt());
        values.put("datetime", formatter.format(date) );
        return db.insert("ItemsHistoric", null, values);

    }

    public List<ItemHistoric> getAllhistoric(Item item){

        List<ItemHistoric> lstHistoric = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT idh, id, cnt, datetime FROM ItemsHistoric WHERE id = " + item.getID(), null);

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    ItemHistoric ih = new ItemHistoric();
                    ih.setIdh(Integer.parseInt(cursor.getString(cursor.getColumnIndex("idh"))));
                    ih.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                    ih.setCnt(Integer.parseInt(cursor.getString(cursor.getColumnIndex("cnt"))));
                    ih.setDatetime(cursor.getString(cursor.getColumnIndex("datetime")));
                    lstHistoric.add(ih);

                } while (cursor.moveToNext());
            }

        }
        return lstHistoric;
    }


    public List<Item> getAllItems(){
        List<Item> lstItems = new ArrayList<>();
        Cursor cursor = db.query("Items", new String[]{"id", "nomeItem", "cnt", "description", "datetime"},
                                 null, null, null, null, null);

        while (cursor.moveToNext()){
            Item i = new Item();
            i.setID(cursor.getInt(0));
            i.setNomeItem(cursor.getString(1));
            i.setCont(cursor.getInt(2));
            i.setDescription(cursor.getString(3));
            i.setDatetime(cursor.getString(4) );
            lstItems.add(i);
        }

        return  lstItems;
    }

    public void eliminar(Item i){
        db.delete("Items", "id = ?", new String[]{i.getID().toString()});
        db.delete("ItemsHistoric", "id = ?", new String[]{i.getID().toString()});
    }

    public void eliminarHistorico(ItemHistoric i){
        db.delete("ItemsHistoric", "idh = ?", new String[]{i.getIdh().toString()});
    }

    public void actualizar(Item item){
        ContentValues values = new ContentValues();
        values.put("nomeItem", item.getNomeItem() );
        values.put("cnt", item.getCont());
        values.put("description", item.getDescription());
        db.update("Items", values, "id = ?", new String[]{item.getID().toString()});

    }

   public int plus(Item item){
        ContentValues values = new ContentValues();

        int value = item.getCont();
        values.put("cnt", value + 1  );
        int i = db.update("Items", values, "id = ?", new String[]{item.getID().toString()});

        return i;
    }

    public int PlusValue(Item item, int value){
        ContentValues values = new ContentValues();
        values.put ("cnt", value);
        int i = db.update("Items", values, "id = ?", new String[]{item.getID().toString()});

        return i;
    }



}
