package com.example.contaceapplicationcrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactBDD {
    private SQLiteDatabase database;
    private DatabaseHandler databaseHandler;

    public ContactBDD(Context context) {
        databaseHandler = new DatabaseHandler(context, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
        database = databaseHandler.getWritableDatabase();
    }

    public SQLiteDatabase openWritable() {
        database = databaseHandler.getWritableDatabase();
        return database;
    }

    public SQLiteDatabase openReadable() {
        database = databaseHandler.getReadableDatabase();
        return database;
    }

    public long addContact(Contact contact) {
        database = openWritable();
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_NAME, contact.getName());
        values.put(DatabaseHandler.KEY_PHONENUMBER, contact.getPhoneNumber());
        long i= database.insert(DatabaseHandler.TABLE_CONTACTS, null, values);
        return i;
    }

    public Contact searchContact(int id) {
        String Query = "SELECT * FROM " + DatabaseHandler.TABLE_CONTACTS + " WHERE id = ?";
        database = openReadable();
        Cursor cursor = database.rawQuery(Query, new String[]{String.valueOf(id)});
        if (cursor.getCount() == 0)
            return null;

        Contact contact = new Contact();
        if (cursor.moveToFirst()) {
            contact.setName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));
        }
        cursor.close();
        database.close();
        return contact;
    }

    public List<Contact> getAllContact(){
        List<Contact> contactList = new ArrayList<>();
        String Query = "SELECT * FROM " + DatabaseHandler.TABLE_CONTACTS;
        database = openReadable();
        Cursor cursor = database.rawQuery(Query, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return contactList;
    }

    public List<Contact> getContactByName(String name){
        List<Contact> contactList = new ArrayList<>();
        String Query = "SELECT * FROM " + DatabaseHandler.TABLE_CONTACTS + " WHERE name LIKE ?";
        database = openReadable();
        Cursor cursor = database.rawQuery(Query, new String[]{
                "%" + name + "%"
        });
        if(cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return contactList;
    }

    public int updateContact(Contact contact) {
        database = openWritable();
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_NAME, contact.getName());
        values.put(DatabaseHandler.KEY_PHONENUMBER, contact.getPhoneNumber());
        return database.update(DatabaseHandler.TABLE_CONTACTS, values, DatabaseHandler.KEY_ID + " = ?", new String[]{String.valueOf(contact.getId())});
    }

    public void deleteContact(int id) {
        database = openWritable();
        database.delete(DatabaseHandler.TABLE_CONTACTS, DatabaseHandler.KEY_ID + " = ?", new String[]{String.valueOf(id)});
        database.close();
    }
}
