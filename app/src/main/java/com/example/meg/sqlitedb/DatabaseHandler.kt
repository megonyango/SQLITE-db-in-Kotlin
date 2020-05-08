package com.example.meg.sqlitedb

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION)  {

    companion object{

        val DATABASE_VERSION=1
        val DATABASE_NAME="StudentDatabase"
        val TABLE_CONTACTS="StudentTable"
        val KEY_ID="id"
        val KEY_NAME="name"
        val KEY_EMAIL="email"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")")

        db?.execSQL(CREATE_CONTACTS_TABLE)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

       db!!.execSQL("DROP TABLE IF EXISTS"+ TABLE_CONTACTS)
        onCreate(db)

    }

    //CRUD FUNCTIONS

    fun addStudent(student: Student):Long{

        val db=this.writableDatabase

        val contentValues=ContentValues()
        contentValues.put(KEY_ID,student.userId)
        contentValues.put(KEY_NAME,student.userName)
        contentValues.put(KEY_EMAIL,student.userEmail)

        //insert into row


        val success=db.insert(TABLE_CONTACTS,null,contentValues)

        db.close()

        return success

    }

    //Read data

    fun ViewStudent():List<Student>{

        val stdList:ArrayList<Student> = ArrayList<Student>()
        val selectQuery="SELECT * FROM $TABLE_CONTACTS"

        val db = this.readableDatabase

        var cursor :Cursor? =null

        try {
            cursor= db.rawQuery(selectQuery,null)
        } catch (e:SQLException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var userid:Int
        var userName:String
        var userEmail:String


        if (cursor.moveToFirst()){

            do {

                userid=cursor.getInt(cursor.getColumnIndex("id"))
                userName=cursor.getString(cursor.getColumnIndex("name"))
                userEmail=cursor.getString(cursor.getColumnIndex("email"))

                val student=Student(userId =userid,userName = userName,userEmail = userEmail)
                stdList.add(student)

            } while (cursor.moveToFirst())
        }

        return stdList
    }


    // update

    fun UpdateStudent(student: Student):Int{

        val db= writableDatabase

        val contentValues=ContentValues()
         contentValues.put(KEY_ID,student.userId)
         contentValues.put(KEY_NAME,student.userName)
        contentValues.put(KEY_EMAIL,student.userEmail)


        val success=db.update(TABLE_CONTACTS,contentValues,"id="+student.userId,null)

        db.close()

        return success


    }



    //delete


    fun DeleteStudent(student: Student):Int{

        val db=this.writableDatabase

        val contentValues=ContentValues()
        contentValues.put(KEY_ID,student.userId)


        val success=db.delete(TABLE_CONTACTS,"id="+student.userId,null)
        db.close()

        return success

    }



}