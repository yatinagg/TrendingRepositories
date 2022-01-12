package com.yatinagg.trendingrepositories.viewmodel

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yatinagg.trendingrepositories.model.TrendingRepositories
import com.yatinagg.trendingrepositories.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    private val TAG = "MainViewModel"


    val repositoryList = MutableLiveData<TrendingRepositories>()
    val responseSuccessful = MutableLiveData<String>()

    suspend fun getTrendingRepos() {
        responseSuccessful.postValue("loading")
        coroutineScope {
            withContext(Dispatchers.IO) {
                val response = repository.getTrendingRepos()
                response.enqueue(object : Callback<TrendingRepositories> {
                    override fun onResponse(
                        call: Call<TrendingRepositories>,
                        response: Response<TrendingRepositories>
                    ) {
                        repositoryList.postValue(response.body())
                        responseSuccessful.postValue("success")
                        Log.d(TAG, "check${response.body()}")
                    }

                    override fun onFailure(call: Call<TrendingRepositories>, t: Throwable) {
                        Log.d(TAG, "failure" + t.message)
                        responseSuccessful.postValue("failure")
                    }
                })
            }
        }
    }

}