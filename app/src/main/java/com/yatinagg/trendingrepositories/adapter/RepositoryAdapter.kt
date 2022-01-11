package com.yatinagg.trendingrepositories.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yatinagg.trendingrepositories.R
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
        holder.binding.repositoriesText.text = repository.username
        holder.binding.repositoriesText.setBackgroundColor(Color.WHITE)
        holder.binding.repositoriesName.text = repository.repositoryName
        holder.binding.repositoriesName.setBackgroundColor(Color.WHITE)
        holder.binding.repositoriesDescription.text = repository.description
        holder.binding.tvRepositoriesLanguage.text = repository.language
        if(repository.languageColor != null)
            holder.binding.ivRepositoriesLanguage.setBackgroundColor(Color.parseColor(repository.languageColor))
        holder.binding.tvRepositoriesStars.text = repository.totalStars.toString()
        holder.binding.tvRepositoriesForks.text = repository.forks.toString()
        holder.binding.cardView.setOnClickListener {
            if(holder.binding.expandableLayout.visibility == View.GONE)
                holder.binding.expandableLayout.visibility = View.VISIBLE
            else
                holder.binding.expandableLayout.visibility = View.GONE
        }
        Picasso.get().load(repository.builtBy[0].avatar).into(holder.binding.ivAvatar)

        println("MainActivity 1 ${repository.url} now $repository")
    }

    override fun getItemCount(): Int {
        return repositories.size
    }
}

class MainViewHolder(val binding: ListviewRepositoriesBinding) :
    RecyclerView.ViewHolder(binding.root) {

}

