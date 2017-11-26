package com.leon.koin.repository.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.leon.koin.repository.local.DatabaseContract.SearchQueryEntry

/**
 * Created by Leon on 22.11.2017..
 */
class SQLiteHelper(contenxt: Context): SQLiteOpenHelper(contenxt, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_SEARCH_QUEARY)
    }
    
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXIST ${SearchQueryEntry.TABLE_NAME}")
        onCreate(db)
    }
    
    companion object {
        private val DATABASE_NAME = "database.db"
        private val DATABASE_VERSION = 1
        
        private val SQL_CREATE_TABLE_SEARCH_QUEARY = "CREATE TABLE ${SearchQueryEntry.TABLE_NAME} (" +
            "${SearchQueryEntry._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "${SearchQueryEntry.COLUMN_NAME_NAME} TEXT NOT NULL UNIQUE, " +
            "${SearchQueryEntry.COLUMN_NAME_INDEX} INTEGER NOT NULL, " +
            "${SearchQueryEntry.COLUMN_NAME_TIMESTAMP} INTEGER NOT NULL);"
    }
}