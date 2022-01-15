package com.yatinagg.trendingrepositories.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yatinagg.trendingrepositories.repository.LocalRepository

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepository(repository: List<LocalRepository>)

    @Query("Select * from LocalRepository")
    fun getAllRepositories(): List<LocalRepository>

}