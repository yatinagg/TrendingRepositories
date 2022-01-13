package com.yatinagg.trendingrepositories.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yatinagg.trendingrepositories.databinding.ListviewRepositoriesBinding
import com.yatinagg.trendingrepositories.model.TrendingRepositories
import com.yatinagg.trendingrepositories.model.TrendingRepositoriesItem
import java.lang.Exception

class RepositoryAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var repositories = mutableListOf<TrendingRepositoriesItem>()

    fun setRepositoriesList(repositories: TrendingRepositories) {
        this.repositories = repositories.toMutableList()
        notifyItemRangeInserted(0,repositories.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        println("MainActivity create view")
        val binding = ListviewRepositoriesBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val repository = repositories[holder.adapterPosition]
        println("MainActivity 1 ${repository.username == null}   ${repository.username} now")

        if(repository.username == null){
            holder.setIsRecyclable(false)
            holder.binding.cardView.setCardBackgroundColor(Color.parseColor(repository.languageColor))
            holder.binding.repositoriesText.setBackgroundColor(Color.parseColor(repository.languageColor))
            holder.binding.repositoriesText.setTextColor(Color.parseColor("#FFFFFF"))
            val layoutParams = holder.binding.cardView.layoutParams
            layoutParams.height = 100
            holder.binding.ivAvatar.visibility = View.GONE
            holder.binding.repositoriesText.text = repository.language
            return
        }
        else {
            holder.binding.repositoriesText.text = repository.username
            holder.binding.repositoriesText.setBackgroundColor(Color.WHITE)
            holder.binding.repositoriesName.text = repository.repositoryName
            holder.binding.repositoriesName.setBackgroundColor(Color.WHITE)
            holder.binding.repositoriesDescription.text = repository.description
            try {
                holder.binding.ivRepositoriesLanguage.setBackgroundColor(Color.parseColor(repository.languageColor))
                holder.binding.tvRepositoriesLanguage.text = repository.language
            } catch (e: Exception) {
                holder.binding.tvRepositoriesLanguage.text = "None"
            }
            holder.binding.tvRepositoriesStars.text = repository.totalStars.toString()
            holder.binding.tvRepositoriesForks.text = repository.forks.toString()
            holder.binding.cardView.setOnClickListener {
                if (holder.binding.expandableLayout.visibility == View.GONE)
                    holder.binding.expandableLayout.visibility = View.VISIBLE
                else
                    holder.binding.expandableLayout.visibility = View.GONE
            }
            Picasso.get().load(repository.builtBy?.get(0)?.avatar).into(holder.binding.ivAvatar)
        }
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

class MainViewHolder(val binding: ListviewRepositoriesBinding) :
    RecyclerView.ViewHolder(binding.root) {
}

