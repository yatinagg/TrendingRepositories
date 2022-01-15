package com.yatinagg.trendingrepositories.database

import androidx.room.*
import com.yatinagg.trendingrepositories.model.TrendingRepositories
import com.yatinagg.trendingrepositories.repository.LocalRepository

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepository(repository: List<LocalRepository>)

    @Query("Select * from LocalRepository")
    fun getAllRepositories(): List<LocalRepository>

}