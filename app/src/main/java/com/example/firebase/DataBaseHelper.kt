package com.example.firebase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DataBaseHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "student.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "students"
        private const val COLUMN_ID = "id"      //primary key
        private const val COLUMN_NAME = "name"
        private const val COLUMN_Class = "class"
        private const val COLUMN_Roll_No = "roll_no"
    }
// create the database
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_Class TEXT, $COLUMN_Roll_No INTEGER)"
        db?.execSQL(createTable)
    }
// if there is any change in the database such as update,edit,delete etc.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun insertStudent(student: Student){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, student.name)
            put(COLUMN_Class, student.className)
            put(COLUMN_Roll_No, student.rollNo)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllStudents(): ArrayList<Student>{

        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        val studentList = ArrayList<Student>()
        if(cursor.moveToFirst()){
            do{
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val class_name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Class))
                val roll_No = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_Roll_No))
                studentList.add(Student( name, class_name, roll_No))
            }
            while(cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return studentList


    }
}