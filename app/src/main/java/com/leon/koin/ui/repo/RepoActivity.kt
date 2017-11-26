package com.leon.koin.ui.repo

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import com.leon.koin.R
import com.leon.koin.di.ApplicationModule
import com.leon.koin.model.Repo
import com.leon.koin.ui.repos.ReposActivity
import org.koin.android.contextaware.ContextAwareActivity
import org.koin.android.ext.android.inject
import org.koin.standalone.KoinComponent


class RepoActivity : ContextAwareActivity(ApplicationModule.RepoActivity), RepoContract.View, KoinComponent {
    override val presenter by inject<RepoContract.Presenter>()
    val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    val nameTextView by lazy { findViewById<TextView>(R.id.name) }
    val fullNameTextView by lazy { findViewById<TextView>(R.id.fullName) }
    val idTextView by lazy { findViewById<TextView>(R.id.id) }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        presenter.view = this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val repo = intent.getParcelableExtra<Repo>(ReposActivity.EXTRA_REPO)
        supportActionBar?.title = repo.name
        
        val name = repo.name
        val fullName = repo.fullName
        val id: String = repo.id.toString()
        
        nameTextView.text = name
        fullNameTextView.text = fullName
        idTextView.text = id
        
        
    
    }
    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    
    override fun onStart() {
        super.onStart()
        presenter.start()
    }
    
    override fun onStop() {
        super.onStop()
        presenter.stop()
    }
    
    
}
