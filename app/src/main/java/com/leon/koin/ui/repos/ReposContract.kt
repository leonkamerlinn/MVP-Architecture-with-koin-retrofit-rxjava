package com.leon.koin.ui.repos

import com.leon.koin.BasePresenter
import com.leon.koin.BaseView
import com.leon.koin.model.Repo

/**
 * Created by Leon on 21.11.2017..
 */
interface ReposContract {
    interface View: BaseView<Presenter> {
        fun showRepos(repos: List<Repo>)
        fun userRepsNotFound(username: String)
    }
    interface Presenter: BasePresenter<View> {
        fun onUserSelected(username: String)
    }
}