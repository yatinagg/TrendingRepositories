package com.yatinagg.trendingrepositories

import com.yatinagg.trendingrepositories.model.RetrofitService
import com.yatinagg.trendingrepositories.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideTrendingRepositoryRepository(): MainRepository =
        MainRepository(RetrofitService.getInstance())
}