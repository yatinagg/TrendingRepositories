package com.yatinagg.trendingrepositories.model

data class TrendingRepositoriesItem(
    val builtBy: List<BuiltBy>? = null,
    val description: String? = null,
    val forks: Int? = null,
    val language: String? = null,
    val languageColor: String,
    val rank: Int? = null,
    val repositoryName: String? = null,
    val since: String? = null,
    val starsSince: Int? = null,
    val totalStars: Int? = null,
    val url: String? = null,
    val username: String? = null
)