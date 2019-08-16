package com.example.asociadosboapaz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLite extends SQLiteOpenHelper{


    public static final String Create_Table_Profile =
            "CREATE TABLE  Profile (id INTEGER PRIMARY KEY AUTOINCREMENT , gender String, disability String)";

    public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table_Profile);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Profile");
        onCreate(db);
    }

    public long InsertUsers (String gender, String disability){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("gender",gender);
        values.put("disability",disability);

        long id = database.insert("Profile",null,values);
        database.close();
        return id;
    }

    public void UpdateUser (int id, String gender, String disability){


        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("gender",gender);
        values.put("disability",disability);


        SQLiteDatabase database = this.getWritableDatabase();
        database.update("Profile",values,"id="+id,null);
        database.close();
    }

    public UserData SelectUserByID(int id_request){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Profile WHERE id ="+id_request ,null);
        UserData user = new UserData();

        if (cursor.moveToFirst()){

            user.id  = cursor.getInt(0);
            user.gender  = cursor.getString(1);
            user.disability  = cursor.getString(2);


        }

        database.close();

        return user;
    }




}
