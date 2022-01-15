package com.yatinagg.trendingrepositories.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalRepository(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val description: String? = null,
    val forks: Int? = null,
    val language: String? = null,
    val languageColor: String?,
    val rank: Int? = null,
    val repositoryName: String? = null,
    val since: String? = null,
    val starsSince: Int? = null,
    val totalStars: Int? = null,
    val url: String? = null,
    val username: String? = null,
    val stared: Boolean? = null,
    val dp: String? = null,
)
