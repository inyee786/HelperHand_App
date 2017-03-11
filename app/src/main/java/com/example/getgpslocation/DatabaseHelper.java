package com.example.getgpslocation;

/**
 * Created by Eftakhar on 10-03-2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper  {



    public static final String DATABASE_NAME ="HelperHand.db";
    public static final String TABLE_NAME1="user";
    public static final String TABLE_NAME2 ="Contact";

    public static final String COL_1_1 ="ID";
    public static final String COL_1_2 ="NAME";
    public static final String COL_1_3 ="PASSWORD";
    public static final String COL_1_4 ="P_NUMBER";
    public static final String COL_1_5 ="EMAIL_ID";
    public static final String COL_1_6 ="FKEY";



    public DatabaseHelper(Context context) {
        super(context,  DATABASE_NAME, null, 2);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME1 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT , NAME TEXT , PASSWORD TEXT, P_NUMBER INTEGER , EMAIL_ID TEXT) ");
        db.execSQL("create table " + TABLE_NAME2 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, FKEY INTEGER ,NAME TEXT , P_NUMBER INTEGER , EMAIL_ID TEXT) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXITS "+TABLE_NAME2);
    }




    public boolean insertdataUser(String name,String pnumber,String password,String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_2, name);
        contentValues.put(COL_1_4, pnumber);
        contentValues.put(COL_1_3, password);
        contentValues.put(COL_1_5, email);

        long re= db.insert(TABLE_NAME1,null,contentValues);

        if(re==-1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean contactDetails(String key,String name,String pnumber,String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_6, key);
        contentValues.put(COL_1_2, name);
        contentValues.put(COL_1_4, pnumber);
        contentValues.put(COL_1_5, email);

        long re= db.insert(TABLE_NAME2,null,contentValues);

        if(re==-1) {
            return false;
        } else {
            return true;
        }
    }



    public boolean getUser(String email, String pass){

        String selectQuery = "select * from  " + TABLE_NAME1 + " where " +
                COL_1_5 + " = " + "'"+email+"'" + " and " + COL_1_3 + " = " + "'"+pass+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return true;
        }
        cursor.close();
        db.close();

        return false;
    }




    public int getSessionId(String email, String pass){

        String selectQuery = "select id from  " + TABLE_NAME1+ " where " +
                COL_1_5 + " = " + "'"+email+"'" + " and " + COL_1_3 + " = " + "'"+pass+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return -1;
    }



    public int getPhoneU(String email, String pass){

        String selectQuery = "select P_NUMBER from  " + TABLE_NAME1 + " where " +
                COL_1_5 + " = " + "'"+email+"'" + " and " + COL_1_3 + " = " + "'"+pass+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return -1;
    }





    public int getPhone(String uphone){

        String selectQuery = "select P_NUMBER from  " + TABLE_NAME2 + " where " +
                COL_1_4 + " = " + "'"+uphone+"'" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return -1;
    }




    public ArrayList<ContactList> getList(String uphone) {
        ContactList contact = null;
        ArrayList <ContactList> fList = new ArrayList<ContactList>();

        String selectQuery = "select P_NUMBER from  " + TABLE_NAME2 + " where " +
                COL_1_4 + " = " + "'"+uphone+"'" ;



        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME4, null);
        //Cursor cursor = db.rawQuery("select P_NUMBER from  " + TABLE_NAME2 + " where " + COL_1_4 + " = " +uphone, null  );
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            contact = new ContactList(cursor.getString(0));
            fList.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return fList;
    }






}
