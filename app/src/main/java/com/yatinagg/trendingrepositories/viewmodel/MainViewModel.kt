package com.yatinagg.trendingrepositories.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yatinagg.trendingrepositories.model.TrendingRepositories
import com.yatinagg.trendingrepositories.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    private val TAG = "MainViewModel"

    val repositoryList = MutableLiveData<TrendingRepositories>()

    fun getTrendingRepos() {
        val response = repository.getTrendingRepos()
        response.enqueue(object : Callback<TrendingRepositories> {
            override fun onResponse(
                call: Call<TrendingRepositories>,
                response: Response<TrendingRepositories>
            ) {
                repositoryList.postValue(response.body())
                Log.d(TAG, "check${response.body().toString()}")
            }

            override fun onFailure(call: Call<TrendingRepositories>, t: Throwable) {
                Log.d(TAG, "failure" + t.message)
            }
        })
    }
}