package com.yatinagg.trendingrepositories.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class TrendingRepositories(
    var repos: List<TrendingRepositoriesItem> = arrayListOf()
) : ArrayList<TrendingRepositoriesItem>()