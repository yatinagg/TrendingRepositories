package com.yatinagg.trendingrepositories.repository

import com.yatinagg.trendingrepositories.database.AppDatabase
import com.yatinagg.trendingrepositories.model.BuiltBy
import com.yatinagg.trendingrepositories.model.RetrofitService
import com.yatinagg.trendingrepositories.model.TrendingRepositories
import com.yatinagg.trendingrepositories.model.TrendingRepositoriesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(private val retrofitService: RetrofitService) {
    fun getTrendingRepos() = retrofitService.getTrendingRepos()

    suspend fun getTrendingReposFromDB(
        database: AppDatabase,
    ): TrendingRepositories = coroutineScope {
        withContext(Dispatchers.IO) {

            val repositoryDao = database.repositoryDao()
            val trendingRepositoriesItem: List<TrendingRepositoriesItem> =
                repositoryDao.getAllRepositories().map {
                    TrendingRepositoriesItem(description = it.description,
                        language = it.language,
                        forks = it.forks,
                        languageColor = it.languageColor,
                        rank = it.rank,
                        repositoryName = it.repositoryName,
                        since = it.since,
                        starsSince = it.starsSince,
                        totalStars = it.totalStars,
                        url = it.url,
                        username = it.username,
                        stared = it.stared,
                        builtBy = listOf(BuiltBy(it.dp.toString())))
                }
            TrendingRepositories(trendingRepositoriesItem)
        }
    }

    suspend fun insertRepositories(
        trendingRepositories: TrendingRepositories,
        database: AppDatabase,
    ) {
        withContext(Dispatchers.IO) {
            val repositoryDao = database.repositoryDao()
            repositoryDao.insertRepository(trendingRepositories.map {
                LocalRepository(description = it.description,
                    language = it.language,
                    forks = it.forks,
                    languageColor = it.languageColor,
                    rank = it.rank,
                    repositoryName = it.repositoryName,
                    since = it.since,
                    starsSince = it.starsSince,
                    totalStars = it.totalStars,
                    url = it.url,
                    username = it.username,
                    stared = it.stared,
                    dp = it.builtBy?.get(0)?.avatar)
            })
        }
    }
}