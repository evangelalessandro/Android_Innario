package it.trumbl.ilprofeticoDaniele.dataset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;

/**
 * @author Jhon_Li
 */
public class DBAdapter {

    public static final String DATABASE_TABLE_2008 = "innarioadulti";
    private static final String TAG = "DBAdapter";
    private final Context context;
    private it.trumbl.ilprofeticoDaniele.dataset.DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context context) {
        this.context = context;
        Log.e(TAG, "DBAdapter: ");
        DBHelper = new DatabaseHelper(this.context);
    }

    public String getPath() {
        return db.getPath();
    }

    public long insert(int num, String titulo, String letra) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.Columns.numero.name(), num);
        values.put(DatabaseHelper.Columns.titolo.name(), titulo);
        values.put(DatabaseHelper.Columns.testo.name(), letra);
        return db.insert(DATABASE_TABLE_2008, null, values);
    }

    public Cursor getHimno(long id, boolean versionH) {
        Cursor res =  db.query(tableVersion(versionH), null,DatabaseHelper.Columns.numero.name() + " = " + id, null, null, null, null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    private String tableVersion(boolean versionH) {
        return DATABASE_TABLE_2008;
    }

    public Cursor getHimno(String title, boolean versionH) {
        Cursor res = null;
        res = db.query(tableVersion(versionH), null, DatabaseHelper.Columns.titolo.name() + "='" + title + "'", null, null, null, null);
        if (res != null) {
            res.moveToFirst();
        }
        return res;
    }

    public LinkedList<String> getAllTitles(boolean versionH) {
        LinkedList<String> lls = null;
        Cursor res =  db.query(tableVersion(versionH), null, null, null, null, null, null);
        if (res != null) {
            lls = new LinkedList<>();
            while (res.moveToNext()) {
                lls.add("  " + DatabaseHelper.Columns.titolo.name() + "  ");
            }
        }
        return lls;
    }

    public Cursor getAllHimno(boolean versionH) {
        return db.query(tableVersion(versionH), null, null, null, null, null, null);
    }

    public Cursor getHimnoForTitle(String filter, boolean versionH) {
        return db.query(tableVersion(versionH), null, DatabaseHelper.Columns.titolo.name() + " LIKE '%" + filter + "%'", null,
                null, null, DatabaseHelper.Columns.numero.name() + " ASC");
    }

    public Cursor getAllHimnoASC(boolean versionH) {
        return db.query(tableVersion(versionH), null, null, null, null, null, DatabaseHelper.Columns.numero.name() + " ASC");
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

}
