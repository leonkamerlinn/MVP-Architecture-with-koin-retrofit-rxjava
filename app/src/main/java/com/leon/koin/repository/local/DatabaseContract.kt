package com.leon.koin.repository.local

import android.database.Cursor
import android.provider.BaseColumns

/**
 * Created by Leon on 22.11.2017..
 */
object DatabaseContract {
    class SearchQueryEntry {
        companion object {
            val TABLE_NAME = "search_query"
            val _ID = BaseColumns._ID
            val COLUMN_NAME_NAME = "name"
            val COLUMN_NAME_INDEX = "_index"
            val COLUMN_NAME_TIMESTAMP = "timestamp"
        }
    }
    
  
}

/* Helpers to retrieve column values */
fun Cursor.getColumnString(columnName: String): String = getString(getColumnIndex(columnName))

fun Cursor.getColumnInt(columnName: String): Int = getInt(getColumnIndex(columnName))

fun Cursor.getColumnLong(columnName: String): Long = getLong(getColumnIndex(columnName))