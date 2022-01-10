package com.yatinagg.trendingrepositories.repository

import com.yatinagg.trendingrepositories.model.RetrofitService
import javax.inject.Inject

class MainRepository @Inject constructor(private val retrofitService: RetrofitService) {
    fun getTrendingRepos() = retrofitService.getTrendingRepos()
}