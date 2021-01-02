package com.afrakhteh.noteapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class DBManager{
    val dbName = "NoteDB"
    val tableName = "NoteTbl"
    val dbVersion = 1

    val id = "ID"
    val Title = "TITLE"
    val Description = "DESCRIPTION"
    val Date = "DATE"

    var sqlDB:SQLiteDatabase? = null
    val createTable = " CREATE TABLE IF NOT EXISTS $tableName ( " +
                      id + " INTEGER PRIMARY KEY, "+
                      Title+ " TEXT, "+
                      Description + " TEXT, "+
                      Date + " TEXT )"

    constructor(context: Context){
        val db = sqlliteNote(context)
         sqlDB =db.writableDatabase
    }

    inner class sqlliteNote : SQLiteOpenHelper {
        var context:Context
        constructor(context: Context):super(context,dbName,null,dbVersion){
            this.context = context
        }
        override fun onCreate(db: SQLiteDatabase?) {
           db?.execSQL(createTable)

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $tableName")
        }

    }
    // adding a new note
    fun Insert(values:ContentValues):Long{
        val id = sqlDB?.insert(tableName,"",values)
        return id!!
    }
    //show all list of note
    fun Query(projection:Array<String>, selection:String, selectionArg:Array<String> ,sortOrder:String):Cursor{
        val qb = SQLiteQueryBuilder()
        qb.tables = tableName
        val cursor = qb.query(sqlDB,projection,selection,selectionArg,null,null,sortOrder)
        return cursor!!
    }
    //delete one note
    fun Delete(selection: String,selectionArg: Array<String>):Int{
        val count = sqlDB?.delete(tableName,selection,selectionArg)
        return count!!
    }
    //update one note
    fun Update(values: ContentValues,selection: String,selectionArg: Array<String>):Int{
        val count = sqlDB?.update(tableName,values,selection,selectionArg)
        return count!!
    }
    //number of note items
    fun Count():Int{
        val count = " SELECT * FROM $tableName"
        val cursor:Cursor =sqlDB!!.rawQuery(count,null)
        return cursor.count
    }
}