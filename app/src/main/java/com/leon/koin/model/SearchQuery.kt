package com.leon.koin.model

import android.content.ContentValues
import android.database.Cursor
import com.leon.koin.repository.local.DatabaseContract.SearchQueryEntry
import com.leon.koin.repository.local.getColumnInt
import com.leon.koin.repository.local.getColumnLong
import com.leon.koin.repository.local.getColumnString

/**
 * Created by Leon on 22.11.2017..
 */
class SearchQuery {
    
    val id: Long
    val name: String
    var index: Int
    val timestamp: Long
    
   
    
    constructor(id: Long = NO_FIELD, name: String = "", index: Int = 0, timestamp: Long = NO_FIELD) {
        this.id = id
        this.name = name
        this.index = index
        this.timestamp = timestamp
    }
    
    constructor(cursor: Cursor) {
     
        id = cursor.getColumnLong(SearchQueryEntry._ID)
        name = cursor.getColumnString(SearchQueryEntry.COLUMN_NAME_NAME)
        index = cursor.getColumnInt(SearchQueryEntry.COLUMN_NAME_INDEX)
        timestamp = cursor.getColumnLong(SearchQueryEntry.COLUMN_NAME_TIMESTAMP)
       
    }
    
    val hasId: Boolean
        get() = id != NO_FIELD
    
    val contentValues: ContentValues
        get() {
            val values = ContentValues()
            values.put(SearchQueryEntry.COLUMN_NAME_NAME, name)
            values.put(SearchQueryEntry.COLUMN_NAME_INDEX, index)
            values.put(SearchQueryEntry.COLUMN_NAME_TIMESTAMP, timestamp)
            return values
        }
        
    
    
  
    
    companion object {
        val NO_FIELD = Long.MIN_VALUE
        
        
        fun getList(cursor: Cursor): List<SearchQuery> {
            val list = arrayListOf<SearchQuery>()
            if (cursor.moveToFirst()) {
                do {
                    val sq = SearchQuery(cursor)
                    list.add(sq)
                } while (cursor.moveToNext())
            }
            cursor.close()
            
            return list
        }
    }
    
    override fun toString(): String = "id: $id, name: $name, index: $index, timestamp: $timestamp"
}