package com.leon.koin.ui.repos

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.Toast
import com.leon.koin.R
import com.leon.koin.adapters.RepoAdapter
import com.leon.koin.di.ApplicationModule
import com.leon.koin.di.toggleKeyboard
import com.leon.koin.listeners.RecyclerViewListener
import com.leon.koin.model.Repo
import com.leon.koin.ui.repo.RepoActivity
import org.koin.android.contextaware.ContextAwareActivity
import org.koin.android.ext.android.inject




class ReposActivity : ContextAwareActivity(ApplicationModule.ReposActivity), ReposContract.View {
    
    
    
    override val presenter by inject<ReposContract.Presenter>()
    val searchView by lazy { findViewById<SearchView>(R.id.searcView) }
    val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    val repoAdapter by lazy { RepoAdapter() }
    
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        presenter.view = this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        
        
        recyclerView.adapter = repoAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    
        recyclerView.addOnItemTouchListener(object : RecyclerViewListener(this@ReposActivity, recyclerView) {
            override fun onSingleTapUp(child: View, position: Int) {
                val repo = repoAdapter.repos[position]
                val intent = Intent(this@ReposActivity, RepoActivity::class.java)
                intent.putExtra(EXTRA_REPO, repo)
                startActivity(intent)
            }
            
            
        })
        
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    presenter.onUserSelected(query)
                }
                
                toggleKeyboard()
                return true
            }
    
            override fun onQueryTextChange(newText: String?): Boolean = true
        })

    }
    
    
    
    override fun showRepos(repos: List<Repo>) {
        repoAdapter.repos = repos
    }
    
    override fun userRepsNotFound(username: String) {
        Toast.makeText(this, "$username is not found", Toast.LENGTH_LONG).show()
    }
    
    override fun onStart() {
        super.onStart()
        presenter.start()
    }
    
    override fun onStop() {
        super.onStop()
        presenter.stop()
    }
    
    companion object {
        val EXTRA_REPO = "extra_repo"
    }
    
}
