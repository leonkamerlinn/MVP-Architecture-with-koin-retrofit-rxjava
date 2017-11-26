package com.leon.koin.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leon.koin.R
import com.leon.koin.model.Repo

/**
 * Created by Leon on 22.11.2017..
 */
class RepoAdapter: RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    var repos: List<Repo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onBindViewHolder(holder: RepoViewHolder?, position: Int) {
        val repo = repos[position]
        with(holder!!) {
            nameTextView.text = repo.name
        }
    }
    
    override fun getItemCount(): Int = repos.size
    
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RepoViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_repo, parent, false)
        return RepoViewHolder(itemView)
    }
    
    inner class RepoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.textView)
    }
}