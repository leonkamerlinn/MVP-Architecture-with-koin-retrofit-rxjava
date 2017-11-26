package com.leon.koin.ui.repo

import org.koin.standalone.KoinComponent

/**
 * Created by Leon on 26.11.2017..
 */
class RepoPresenter : RepoContract.Presenter, KoinComponent {
    override lateinit var view: RepoContract.View
    override fun start() {
        println("start")
    }
    
    override fun stop() {
        println("stop")
    }
}