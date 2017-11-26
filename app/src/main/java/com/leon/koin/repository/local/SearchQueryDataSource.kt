package com.leon.koin.repository.local

import com.leon.koin.model.SearchQuery
import io.reactivex.Observable

/**
 * Created by Leon on 22.11.2017..
 */
interface SearchQueryDataSource {
    fun insert(query: SearchQuery): Observable<Long>
    fun get(query: String): Observable<SearchQuery>
    fun incrementIndex(query: String): Observable<SearchQuery>
    fun getAll(): Observable<List<SearchQuery>>
    fun getWithLimit(limit: Int): Observable<List<SearchQuery>>
    
}