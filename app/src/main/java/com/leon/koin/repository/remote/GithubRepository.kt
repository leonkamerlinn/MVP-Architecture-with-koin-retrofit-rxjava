package com.leon.koin.repository.remote

import com.leon.koin.model.Repo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * Created by Leon on 21.11.2017..
 */
class GithubRepository: KoinComponent {
    private val githubDatasource by inject<GithubDataSource>()
    
    fun getUserRepos(username: String): Observable<List<Repo>> {
        
        return githubDatasource.listRepos(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        
    }
}