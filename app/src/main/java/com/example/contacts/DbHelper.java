package com.example.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, "Contacts.db", null, 1); //Create DataBase with version=1
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String createTableStatement = "CREATE TABLE  Contact_TABLE " +
                "( ID Integer PRIMARY KEY AUTOINCREMENT,  NAME  Text, PHONE Text, CITY Text, ContactCreated Text,ImageResource Integer)";
                DB.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        String dropTablesStatement="DROP TABLE if EXISTS Contact_TABLE";
        DB.execSQL(dropTablesStatement);
    }

    public Boolean CreateContact(String name, String phone, String city, String contactCreated, Integer imageResource){
        SQLiteDatabase DB=this.getWritableDatabase(); //gets writeable dataBase and pass it to DB
        ContentValues contentValues=new ContentValues(); //used to put values inside the table
        contentValues.put("NAME",name);
        contentValues.put("PHONE",phone);
        contentValues.put("CITY",city);
        contentValues.put("ContactCreated",contactCreated);
        contentValues.put("ImageResource", imageResource);
        long result=DB.insert("Contact_TABLE",null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Boolean UpdateContact(int id,String name,String phone, String city,String contactCreated,Integer imageResource) {
        SQLiteDatabase DB = this.getWritableDatabase(); //gets writeable dataBase and pass it to DB
        ContentValues contentValues = new ContentValues(); //used to put values inside the table
        Cursor cursor = DB.rawQuery("SELECT * from Contact_TABLE WHERE ID = ?",new String[]{String.valueOf(id)});
        if(cursor.getCount() > 0) {
            contentValues.put("NAME", name);
            contentValues.put("PHONE", phone);
            contentValues.put("CITY", city);
            contentValues.put("ContactCreated", contactCreated);
            contentValues.put("ImageResource", imageResource);
            long result = DB.update("Contact_TABLE", contentValues, "ID=?", new String[]{String.valueOf(id)});
            if (result == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public Boolean DeleteContact(int id) {
        SQLiteDatabase DB = this.getWritableDatabase(); //gets writeable dataBase and pass it to DB
        Cursor cursor = DB.rawQuery("SELECT * from Contact_TABLE WHERE ID = ?",new String[]{String.valueOf(id)});
        if(cursor.getCount() > 0) {
            long result = DB.delete("Contact_TABLE", "ID=?", new String[]{String.valueOf(id)});
            if (result == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public Contact getContact(int id) {
        SQLiteDatabase DB =this.getReadableDatabase(); //gets readable dataBase and pass it to DB
        Cursor cursor = DB.rawQuery("SELECT * from Contact_TABLE WHERE ID = ?",new String[]{String.valueOf(id)});
        Contact contact=new Contact();
        if (cursor.moveToFirst()) {
            contact.id=cursor.getInt(0);
                  contact.name=  cursor.getString(1);
                  contact.phone= cursor.getString(2);
                  contact.city=cursor.getString(3);
                  contact.contactCreated=cursor.getString(4);
                  contact.imageResource= cursor.getInt(5);
        }
        cursor.close();
        DB.close();
        return contact;
    }

    public ArrayList<Contact> GetAllContacts() {
        SQLiteDatabase DB =this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * from Contact_TABLE",null);
        ArrayList<Contact> contactList=new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                contactList.add(new Contact(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        DB.close();
        return contactList;
    }

}
