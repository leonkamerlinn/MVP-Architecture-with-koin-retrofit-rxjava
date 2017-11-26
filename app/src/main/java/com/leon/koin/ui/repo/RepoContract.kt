package com.leon.koin.ui.repo

import com.leon.koin.BasePresenter
import com.leon.koin.BaseView
import com.leon.koin.model.Repo

/**
 * Created by Leon on 26.11.2017..
 */
interface RepoContract {
    interface View: BaseView<Presenter> {
    
    }
    interface Presenter: BasePresenter<View> {
    
    }
}