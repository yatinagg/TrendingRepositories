package com.yatinagg.trendingrepositories.repository

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yatinagg.trendingrepositories.model.BuiltBy

@Entity
data class LocalRepository(@PrimaryKey(autoGenerate = true) val id: Int? = null,
//                           @Embedded
//                           val builtBy: List<BuiltBy>? = null,
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
                           val stared: Boolean? = null)
