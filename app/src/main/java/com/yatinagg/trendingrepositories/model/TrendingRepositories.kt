package com.yatinagg.trendingrepositories.model

data class TrendingRepositories(
    var repos: List<TrendingRepositoriesItem> = arrayListOf(),
) : ArrayList<TrendingRepositoriesItem>()