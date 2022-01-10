package com.yatinagg.trendingrepositories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yatinagg.trendingrepositories.databinding.ListviewRepositoriesBinding
import com.yatinagg.trendingrepositories.model.TrendingRepositories
import com.yatinagg.trendingrepositories.model.TrendingRepositoriesItem

class RepositoryAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var repositories = mutableListOf<TrendingRepositoriesItem>()

    fun setRepositoriesList(repositories: TrendingRepositories) {
        this.repositories = repositories.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        println("MainActivity create view")
        val binding = ListviewRepositoriesBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val repository = repositories[holder.adapterPosition]
        holder.binding.repositoriesText.text = repository.toString()
        println("MainActivity 1$repository")
    }

    override fun getItemCount(): Int {
        return repositories.size
    }
}

class MainViewHolder(val binding: ListviewRepositoriesBinding) :
    RecyclerView.ViewHolder(binding.root) {
}