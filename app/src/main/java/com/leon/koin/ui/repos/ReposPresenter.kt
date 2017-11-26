package com.leon.koin.ui.repos

import com.leon.koin.di.currentTimestamp
import com.leon.koin.model.Repo
import com.leon.koin.model.SearchQuery
import com.leon.koin.repository.local.SearchQueryDataSource
import com.leon.koin.repository.remote.GithubRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.HttpException

/**
 * Created by Leon on 21.11.2017..
 */
class ReposPresenter : ReposContract.Presenter, KoinComponent {
   
    
    override lateinit var view: ReposContract.View
    private val githubRepository by inject<GithubRepository>()
    private val searchQueryDataSource by inject<SearchQueryDataSource>()
    
    override fun start() {
    
    }
    
    override fun stop() {
    
    }
    
    override fun onUserSelected(username: String) {
        
        
        githubRepository.getUserRepos(username).subscribeWith(object: DisposableObserver<List<Repo>>() {
            
            override fun onError(e: Throwable?) {
                if (e != null && e is HttpException) {
                    view.userRepsNotFound(username)
                }
            }
    
            override fun onNext(value: List<Repo>?) {
                if (value != null) {
                    view.showRepos(value)
                    
                    searchQueryDataSource.insert(SearchQuery(name = username, index = 1, timestamp = currentTimestamp))
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object: DisposableObserver<Long>() {
                            override fun onNext(value: Long?) {
                            
                            
                            }
    
                            override fun onError(e: Throwable?) {
                                searchQueryDataSource.incrementIndex(username)
                                    .subscribeOn(Schedulers.computation())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(object: DisposableObserver<SearchQuery>(){
                                        override fun onError(e: Throwable?) {
                                        
                                        }
    
                                        override fun onComplete() {
                                        
                                        }
    
                                        override fun onNext(value: SearchQuery?) {
                                            println(value.toString())
                                        }
                                    })
                            }
    
                            override fun onComplete() {
                            
                            }
                        })
                    
                    
                }
            }
    
            override fun onComplete() {
            
            }
        })
        
        
    } // onUserSelected
    
    
    
    
}