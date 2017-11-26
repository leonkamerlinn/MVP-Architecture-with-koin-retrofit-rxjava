package com.leon.koin.repository.remote

import com.leon.koin.model.Repo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Leon on 21.11.2017..
 */
interface GithubDataSource {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Observable<List<Repo>>
}