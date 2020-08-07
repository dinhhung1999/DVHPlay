package com.example.dvhplay.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dvhplay.Models.Comment;
import com.example.dvhplay.Models.FavoriteVideo;
import com.example.dvhplay.Models.SearchHistory;
import com.example.dvhplay.Models.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLHelper";
    static final String DB_NAME = "db2.db";
    static final String DB_SEARCH_TABLE = "SEARCH";
    static final String DB_USER_TABLE = "USER";
    static final String DB_COMMENT_TABLE = "COMMENT";
    static final String DB_FAVORITE_TABLE = "FAVORITE";
    static final String ID = "id";
    static final String USER_ID = "user_id";
    static final String VIDEO_ID = "video_id";
    static final String CONTENT = "content";
    static final String TITLE = "title";
    static final String AVATAR = "avatar";
    static final String FILE_MP4 = "file_mp4";
    static final String USERNAME = "username";
    static final String PASSWORD = "password";
    static final String TIME = "time";
    static final int DB_VERSION = 1;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;

    public SQLHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreaTable = "CREATE TABLE SEARCH ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "title Text," +
                "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(queryCreaTable);

        String queryCreateUserTable = "CREATE TABLE USER ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "username Text NOT NULL," +
                "password Text NOT NULL)";
        db.execSQL(queryCreateUserTable);

        String queryCreateCommentTable = "CREATE TABLE COMMENT ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "video_id INTEGER NOT NULL,"+
                "username Text NOT NULL," +
                "content Text NOT NULL,"+
                "FOREIGN KEY (user_id) REFERENCES USER(id))";
        db.execSQL(queryCreateCommentTable);

        String queryCreateFavoriteTable = "CREATE TABLE FAVORITE ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "video_id INTEGER NOT NULL," +
                "title Text NOT NULL,"+
                "avatar Text NOT NULL,"+
                "file_mp4 NOT NULL,"+
                "FOREIGN KEY (user_id) REFERENCES USER(id))";
        db.execSQL(queryCreateFavoriteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("drop table if exists " + DB_SEARCH_TABLE);
            onCreate(db);
        }
    }

    public void insertHistory(String title) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(TITLE, title);

        sqLiteDatabase.insert(DB_SEARCH_TABLE, null, contentValues);
        closeDB();
    }
    public void insertUser(String username, String password) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(USERNAME, username);
        contentValues.put(PASSWORD, password);

        sqLiteDatabase.insert(DB_USER_TABLE, null, contentValues);
        closeDB();
    }
    public void insertComment(int user_id,int video_id,String username, String content) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(USER_ID, user_id);
        contentValues.put(VIDEO_ID, video_id);
        contentValues.put(USERNAME, username);

        contentValues.put(CONTENT, content);

        sqLiteDatabase.insert(DB_COMMENT_TABLE, null, contentValues);
        closeDB();
    }
    public void insertFavorite(int user_id,int video_id, String title, String avatar, String file_mp4) {
        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(USER_ID, user_id);
        contentValues.put(VIDEO_ID, video_id);
        contentValues.put(TITLE, title);
        contentValues.put(AVATAR, avatar);
        contentValues.put(FILE_MP4, file_mp4);

        sqLiteDatabase.insert(DB_FAVORITE_TABLE, null, contentValues);
        closeDB();
    }
    public int deleteFavorite(int id) {
        sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(DB_FAVORITE_TABLE, " video_id=?",
                new String[]{String.valueOf(id)});
    }

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
    public List<SearchHistory> getAllHistoryAdvanced() {
        ArrayList historys = new ArrayList<>();

        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_SEARCH_TABLE, null, null, null
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
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String username = cursor.getString(cursor.getColumnIndex(USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(PASSWORD));
//            String date = cursor.getString(cursor.getColumnIndex("date"));
                users.add(new User(id,username,password));
            }
        }
        closeDB();
        return users;
    }
    public List<Comment> getALlComment(int id_video){
        List<Comment> comments = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_COMMENT_TABLE, null, null, null
                , null, null, null, null);
        if (cursor!=null&&cursor.getColumnCount()!=0){
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                int user_id = cursor.getInt(cursor.getColumnIndex(USER_ID));
                int video_id = cursor.getInt(cursor.getColumnIndex(VIDEO_ID));
                String username = cursor.getString(cursor.getColumnIndex(USERNAME));
                String content = cursor.getString(cursor.getColumnIndex(CONTENT));
                if (video_id == id_video) comments.add(new Comment(id,user_id,video_id,username,content));
            }
        }
        closeDB();
        return comments;
    }
    public List<FavoriteVideo> getALlFavorite(int id_user){
        List<FavoriteVideo> videos = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_FAVORITE_TABLE, null, null, null
                , null, null, null, null);
        if (cursor!=null&&cursor.getColumnCount()!=0){
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                int user_id = cursor.getInt(cursor.getColumnIndex(USER_ID));
                int video_id = cursor.getInt(cursor.getColumnIndex(VIDEO_ID));
                String title = cursor.getString(cursor.getColumnIndex(TITLE));
                String avatar = cursor.getString(cursor.getColumnIndex(AVATAR));
                String file_mp4 = cursor.getString(cursor.getColumnIndex(FILE_MP4));
                if (user_id == id_user)videos.add(new FavoriteVideo(id,user_id,video_id,title,avatar,file_mp4));
            }
        }
        closeDB();
        return videos;
    }

    private void closeDB() {
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (contentValues != null) contentValues.clear();
        if (cursor != null) cursor.close();
    }
}
