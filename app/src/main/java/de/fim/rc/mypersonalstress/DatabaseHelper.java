package de.fim.rc.mypersonalstress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * DatabaseHelper
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static Context context;
    private static final String DB_NAME = "mypersonalstress.db";
    private static final int DB_VERSION = 1;
    private static DatabaseHelper instance;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DatabaseHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    public DatabaseHelper(Context context) {
        super(context, "name", null, 1);
    }


    //Erstellt eine neue Tabelle(falls noch nicht vorhanden) mit den PSSScores die über den Fragebogen ermittelt wurden, beinhaltet ID, Timestamp und den Score.
    protected void createPSSScoreTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS PSSScores (_id INTEGER PRIMARY KEY AUTOINCREMENT, Datum DATETIME DEFAULT CURRENT_TIMESTAMP, PSSScore REAL)");
    }

    //Fügt einen neuen PSSScore hinzu, welcher zuvor über den Fragebogen ermittelt wurde.
    protected boolean addNewPSSScore(long zeit, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Datum", zeit);
        contentValues.put("PSSScore", score);
        long result = db.insert("PSSScores", null, contentValues);
        return result != -1;
    }

    //Erstellt eine neue Tabelle(falls noch nicht vorhanden) für die Koeffizienten. Beinhaltet ID, Timestamp und die Koeffizienten zur jeweiligen Zeit
    protected void createCoefficientsTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS Coefficients (_id INTEGER PRIMARY KEY AUTOINCREMENT, Datum DATETIME DEFAULT CURRENT_TIMESTAMP, coeff1 REAL, coeff2 REAL)");
    }

    //Fügt einen neuen PSSScore hinzu, welcher zuvor über den Fragebogen ermittelt wurde.
    protected boolean addNewCoefficients(long zeit, double coeff1, double coeff2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Datum", zeit);
        contentValues.put("coeff1", coeff1);
        contentValues.put("coeff2", coeff2);
        long result = db.insert("Coefficients", null, contentValues);
        return result != -1;
    }


    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }




















    //Von DatabaseManager benötigt
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
