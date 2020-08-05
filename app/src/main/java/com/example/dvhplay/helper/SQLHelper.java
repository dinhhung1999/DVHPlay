package com.example.dvhplay.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dvhplay.Models.SearchHistory;
import com.example.dvhplay.Models.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLHelper";
    static final String DB_NAME = "DVHPlay4.db";
    static final String DB_SearchHistory_TABLE = "SearchHistory";
    static final String DB_USER_TABLE = "USER";
    static final int DB_VERSION = 1;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;

    public SQLHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreaTable = "CREATE TABLE SearchHistory ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "title Text," +
                "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(queryCreaTable);

        String queryCreateUserTable = "CREATE TABLE USER ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "username Text NOT NULL," +
                "password Text NOT NULL)";
        db.execSQL(queryCreateUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("drop table if exists " + DB_SearchHistory_TABLE);
            onCreate(db);
        }
    }

    public void insertHistory(String title) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put("title", title);

        sqLiteDatabase.insert(DB_SearchHistory_TABLE, null, contentValues);
        closeDB();
    }
    public void insertUser(String username, String password) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put("username", username);
        contentValues.put("password", password);

        sqLiteDatabase.insert(DB_USER_TABLE, null, contentValues);
        closeDB();
    }

//    public int deleteProduct(String id) {
//        sqLiteDatabase = getWritableDatabase();
//        return sqLiteDatabase.delete(DB_SearchHistory_TABLE, " id=?",
//                new String[]{String.valueOf(id)});
//    }

//    public boolean delAllProduct() {
//        int result;
//        sqLiteDatabase = getWritableDatabase();
//        result = sqLiteDatabase.delete(DB_SearchHistory_TABLE, null, null);
//        closeDB();
//        if (result == 1) return true;
//        else return false;
//    }
//
//    public void updateProduct(String id, String name, String quantity) {
//        sqLiteDatabase = getWritableDatabase();
//        contentValues = new ContentValues();
//        contentValues.put("name", name);
//        contentValues.put("quantity", quantity);
//
//        sqLiteDatabase.update(DB_SearchHistory_TABLE, contentValues, "id=?",
//                new String[]{String.valueOf(id)});
//        closeDB();
//    }
//

//    public void getAllProduct() {
//        sqLiteDatabase = getReadableDatabase();
//        cursor = sqLiteDatabase.query(false, DB_SearchHistory_TABLE, null,
//                null, null, null, null, null, null);
//
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(cursor.getColumnIndex("id"));
//            String name = cursor.getString(cursor.getColumnIndex("name"));
//            int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
//            Log.d(TAG, "getAllProduct: " + "id - " + id + " - name - " + name + " - quantity - " + quantity);
//        }
//        closeDB();
//    }

    public List<SearchHistory> getAllHistoryAdvanced() {
        ArrayList historys = new ArrayList<>();

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_SearchHistory_TABLE, null, null, null
                , null, null, null, null);

        while (cursor.moveToNext()) {
//            String id = cursor.getString(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
//            String date = cursor.getString(cursor.getColumnIndex("date"));
            historys.add(title);
        }
        closeDB();
        return historys;
    }
    public List<User> getAllUser() {
        ArrayList users = new ArrayList<>();

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_USER_TABLE, null, null, null
                , null, null, null, null);
        if (cursor!=null&&cursor.getColumnCount()!=0){
            while (cursor.moveToNext()) {
//            String id = cursor.getString(cursor.getColumnIndex("id"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
//            String date = cursor.getString(cursor.getColumnIndex("date"));
                users.add(new User(id,username,password));
            }
        }
        closeDB();
        return users;
    }

    private void closeDB() {
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (contentValues != null) contentValues.clear();
        if (cursor != null) cursor.close();
    }
}
