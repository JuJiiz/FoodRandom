package com.example.jujiiz.foodrandom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class myDBClass extends SQLiteOpenHelper {

    private static final String DB_NAME = "MIS";
    private static final int DB_VERSION = 1;

    String TAG = "MYLOG";

    public myDBClass(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `foodTable` (\n" +
                "\t`fid`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`food_img`\tTEXT NOT NULL,\n" +
                "\t`food_name`\tTEXT NOT NULL,\n" +
                "\t`food_type`\tTEXT NOT NULL,\n" +
                "\t`status`\tTEXT NOT NULL\n" +
                ");");
        Log.d(TAG, "Create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    // Select Data
    public ArrayList<HashMap<String, String>> SelectData(String tableName) {
        // TODO Auto-generated method stub

        try {
            //String arrData[] = null;
            ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            /*Cursor cursor = db.query("opt_type", new String[]{"*"}, null,
                    new String[]{String.valueOf(strMemberID)}, null, null, null, null);*/
            String strSQL = "SELECT * FROM " + tableName;
            Cursor cursor = db.rawQuery(strSQL, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        map = new HashMap<String, String>();
                        for (int i = 0; i < cursor.getColumnCount(); i++) {
                            map.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                        MyArrList.add(map);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
            Log.d(TAG, "SelectData: " + MyArrList);
            return MyArrList;

        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e);
            return null;
        }
    }

    // Select Data
    public ArrayList<HashMap<String, String>> SelectRandom(String tableName) {
        // TODO Auto-generated method stub

        try {
            //String arrData[] = null;
            ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            /*Cursor cursor = db.query("opt_type", new String[]{"*"}, null,
                    new String[]{String.valueOf(strMemberID)}, null, null, null, null);*/
            String strSQL = "SELECT * FROM " + tableName + " ORDER BY RANDOM() LIMIT 1";
            Cursor cursor = db.rawQuery(strSQL, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        map = new HashMap<String, String>();
                        for (int i = 0; i < cursor.getColumnCount(); i++) {
                            map.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                        MyArrList.add(map);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
            Log.d(TAG, "SelectData: " + MyArrList);
            return MyArrList;

        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e);
            return null;
        }
    }

    // Insert Data
    public long InsertData(String TableName, ContentValues Val) {
        // TODO Auto-generated method stub

        try {
            SQLiteDatabase db = this.getWritableDatabase(); // Write Data
            long rows = db.insert(TableName, null, Val);
            Log.d("MYLOG", TableName + " Insert: " + Val);
            Log.d("MYLOG", " rows: " + rows);
            //db.close();

            return rows; // return rows inserted.

        } catch (Exception e) {
            return -1;
        }
    }

    // Update Data
    public long UpdateData(String TableName, ContentValues Val, String strPK, String strPKValue) {
        // TODO Auto-generated method stub
        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase(); // Write Data
            String where = strPK + " = ?";
            String[] whereArgs = new String[]{String.valueOf(strPKValue)};
            long rows = db.update(TableName, Val, where, whereArgs);
            Log.d("MYLOG", TableName + " Update: " + Val);
            db.close();
            return rows; // return rows inserted.
        } catch (Exception e) {
            return -1;
        }
    }

    // Select Where Data
    public ArrayList<HashMap<String, String>> SelectWhereData(String tableName, String strKey, String strValue) {
        // TODO Auto-generated method stub

        try {
            //String arrData[] = null;
            ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            /*Cursor cursor = db.query("opt_type", new String[]{"*"}, null,
                    new String[]{String.valueOf(strMemberID)}, null, null, null, null);*/
            String strSQL = "SELECT * FROM " + tableName + " WHERE " + strKey + " = " + strValue;
            Cursor cursor = db.rawQuery(strSQL, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        map = new HashMap<String, String>();
                        for (int i = 0; i < cursor.getColumnCount(); i++) {
                            map.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                        MyArrList.add(map);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
            return MyArrList;

        } catch (Exception e) {
            return null;
        }
    }

}
