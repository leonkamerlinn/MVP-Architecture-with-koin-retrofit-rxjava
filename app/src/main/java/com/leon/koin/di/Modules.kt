package com.leon.koin.di

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.leon.koin.repository.local.SQLiteHelper
import com.leon.koin.repository.local.SQLiteRepositry
import com.leon.koin.repository.local.SearchQueryDataSource
import com.leon.koin.repository.remote.GithubDataSource
import com.leon.koin.repository.remote.GithubRepository
import com.leon.koin.ui.repo.RepoContract
import com.leon.koin.ui.repo.RepoPresenter
import com.leon.koin.ui.repos.ReposContract
import com.leon.koin.ui.repos.ReposPresenter
import org.koin.android.module.AndroidModule
import org.koin.dsl.context.Context
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Leon on 20.11.2017..
 */

fun appModules() = listOf(ApplicationModule())
val currentTimestamp: Long
    get() = System.currentTimeMillis()/1000
fun Activity.toggleKeyboard(): Boolean {
    if (currentFocus != null) {
        val imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        return true
    }
    
    return false
}

class ApplicationModule : AndroidModule() {
    override fun context(): Context = applicationContext {
        context(name = ReposActivity) {
            provide { ReposPresenter() } bind ReposContract.Presenter::class
        }
        
        context(name = RepoActivity) {
            provide { RepoPresenter() } bind RepoContract.Presenter::class
            provide { this } bind RepoContract.View::class
        }
    
    
        provide {
            Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GithubDataSource::class.java)
        }
        
        provide { GithubRepository() }
        
        
        provide {
            val sqliteHelper = SQLiteHelper(get())
            return@provide sqliteHelper.writableDatabase
        }
        
        provide { SQLiteRepositry() } bind SearchQueryDataSource::class
        
    }
    
    companion object {
        val ReposActivity = "ReposActivity"
        val RepoActivity = "RepoActivity"
    }
}

