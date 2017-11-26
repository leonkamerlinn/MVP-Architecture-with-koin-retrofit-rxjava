package com.leon.koin.repository.local

import android.database.sqlite.SQLiteDatabase
import com.leon.koin.model.SearchQuery
import com.leon.koin.repository.local.DatabaseContract.SearchQueryEntry
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.sql.SQLException

/**
 * Created by Leon on 22.11.2017..
 */
class SQLiteRepositry: SearchQueryDataSource, KoinComponent {
    private val db by inject<SQLiteDatabase>()
    
    
    override fun getAll(): Observable<List<SearchQuery>> {
        return Observable.create({
            try {
                val cursor = db.rawQuery("SELECT * FROM ${SearchQueryEntry.TABLE_NAME}", null)
                it.onNext(SearchQuery.getList(cursor))
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        })
    }
    
    override fun getWithLimit(limit: Int): Observable<List<SearchQuery>> {
        return Observable.create({
            try {
                val cursor = db.rawQuery("SELECT * FROM ${SearchQueryEntry.TABLE_NAME} LIMIT $limit", null)
                it.onNext(SearchQuery.getList(cursor))
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        })
    }
    
    override fun get(query: String): Observable<SearchQuery> {
        return Observable.create({
            try {
                val cursor = db.rawQuery("SELECT * FROM ${SearchQueryEntry.TABLE_NAME} WHERE ${SearchQueryEntry.COLUMN_NAME_NAME} LIKE ? LIMIT 1", arrayOf(query))
           
                cursor.moveToFirst()
                it.onNext(SearchQuery(cursor))
                cursor.close()
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        })
    }
    
    override fun incrementIndex(query: String): Observable<SearchQuery> {
        return Observable.create({ subscriber ->
            get(query)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe {
                    with(it) {
                        if (hasId) {
                            index++
                            try {
                                db.update(SearchQueryEntry.TABLE_NAME, contentValues, "${SearchQueryEntry.COLUMN_NAME_NAME} = ?", arrayOf(name))
                                subscriber.onNext(this)
                                subscriber.onComplete()
                            } catch (e: Exception) {
                                subscriber.onError(e)
                            }
                        }
                    }
                }
        })
    }
    
    
    override fun insert(query: SearchQuery): Observable<Long> {
        return Observable.create({
            try {
                it.onNext(db.insertOrThrow(SearchQueryEntry.TABLE_NAME, null, query.contentValues))
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        })
    }
    
    
}