package com.afrakhteh.noteapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class DBManager {
    var dbName = "notedatabase"
    var tableName = "notetable"
    var dbVersion = 1

    var id = "ID"
    var title = "TITLE"
    var description = "DESCRIPTION"
    var date = "DATE"

    var sqlDB: SQLiteDatabase? = null
    var creatingTable = "CREATE TABLE IF NOT EXISTS $tableName (" +
            id + "INTEGER PRIMARY KEY," +
            title + "TEXT," +
            description + "Text," +
            date + "Text)"

    constructor(context: Context) {
        var db = sqlliteNote(context)
        sqlDB = db.writableDatabase
    }

    inner class sqlliteNote : SQLiteOpenHelper {
        var context: Context

        constructor(context: Context) : super(context, dbName, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(creatingTable)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL(" DROP TABLE IF EXISTS $tableName")
        }

    }

    //Insert Data
    fun Insert(values:ContentValues):Long{
        val id = sqlDB?.insert(tableName, "",values)
        return id!!
    }

    // get all notes and show them
    fun Query(projection:Array<String> ,selection:String , selectionArgs:Array<String> ,sortOrder:String):Cursor{
        val qb = SQLiteQueryBuilder()
        qb.tables = tableName
        val cursor =qb.query(sqlDB,projection,selection,selectionArgs,null,null,date)
        return cursor
    }

    //delete note
    fun Delete(selection: String,selectionArgs: Array<String>):Int{
        val dlt =sqlDB?.delete(tableName,selection,selectionArgs)
        return dlt!!
    }

    //update note
    fun Update(values: ContentValues,selection: String,selectionArgs: Array<String>):Int{
        val updt = sqlDB?.update(tableName,values,selection,selectionArgs)
        return updt!!
    }
}