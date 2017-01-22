package id.barkost.haditsarbain.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by fikri on 20/12/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public String tableName = "hadits";
    public String colId = "id";
    public String colJudulLatin = "judul";
    public String colJudulArabic = "judul_arabic";
    public String colIsiHadits = "isi_hadits";
    public String colTerjemahHadits = "terjemah_hadits";
    public String colFootNote = "fote_note_hadits";

    private SQLiteDatabase db;

    public final String table_create_hadits = "CREATE TABLE " + tableName + " ( " +
            colId + " INT PRIMARY KEY, " +
            colJudulLatin + " TEXT, " +
            colJudulArabic + " TEXT, " +
            colIsiHadits +" TEXT, " +
            colTerjemahHadits +" TEXT, " +
            colFootNote +" TEXT);";

    public DatabaseHelper(Context context) {
        super(context, "arbain.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_create_hadits);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

    public boolean insert_table(int id, String judul, String judul_arabic, String isi_hadits, String terjemah_hadits, String foot_note_hadits) {
        db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(colId, id);
        content_values.put(colJudulLatin, judul);
        content_values.put(colJudulArabic, judul_arabic);
        content_values.put(colIsiHadits, isi_hadits);
        content_values.put(colTerjemahHadits, terjemah_hadits);
        content_values.put(colFootNote, foot_note_hadits);
        long result = db.insert(tableName, null, content_values);
        return result != -1;
    }

    public Cursor select_menu() {
        db = this.getWritableDatabase();
        Cursor transaction = db.rawQuery("SELECT " + colId + ", " + colJudulLatin + ", " + colJudulArabic + " FROM " + tableName + " ORDER BY " + colId, null);
//        Cursor transaction = db.rawQuery("SELECT * FROM transactions WHERE date ='" + date + "' ORDER BY date+time DESC", null);
        return transaction;
    }

    public Cursor select_single_data(String id) {
        db = this.getWritableDatabase();
        Cursor transaction = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + colId + " = " + id + " ORDER BY " + colId, null);
//        Cursor transaction = db.rawQuery("SELECT * FROM transactions WHERE date ='" + date + "' ORDER BY date+time DESC", null);
        return transaction;
    }

    public Cursor delete() {
        db = this.getWritableDatabase();
        Cursor transaction = db.rawQuery("DELETE FROM " + tableName, null);
        return transaction;
    }
}