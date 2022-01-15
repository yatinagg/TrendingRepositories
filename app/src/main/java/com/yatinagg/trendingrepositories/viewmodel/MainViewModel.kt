package com.yatinagg.trendingrepositories.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yatinagg.trendingrepositories.database.AppDatabase
import com.yatinagg.trendingrepositories.model.TrendingRepositories
import com.yatinagg.trendingrepositories.model.TrendingRepositoriesItem
import com.yatinagg.trendingrepositories.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.collections.set

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val repositoryList = MutableLiveData<TrendingRepositories?>()
    val responseSuccessful = MutableLiveData<String>()
    var repositoryMapList = HashMap<String, TrendingRepositories?>()
    val repositoryListHeader = MutableLiveData<TrendingRepositories?>()

    suspend fun getTrendingRepos(database: AppDatabase) {

        val trendingRepositories: TrendingRepositories = repository.getTrendingReposFromDB(database)
        repositoryList.postValue(trendingRepositories)

        responseSuccessful.postValue("loading")
        coroutineScope {
            withContext(Dispatchers.IO) {
                val response = repository.getTrendingRepos()
                response.enqueue(object : Callback<TrendingRepositories> {
                    override fun onResponse(
                        call: Call<TrendingRepositories>,
                        response: Response<TrendingRepositories>,
                    ) {
                        viewModelScope.launch {
                            if (response.body() != null) {
                                repository.insertRepositories(
                                    response.body()!!,
                                    database
                                )
                            }
                        }

                        repositoryList.postValue(null)
                        repositoryList.postValue(response.body())
                        responseSuccessful.postValue("success")
                        repositoryMapList = HashMap()
                        response.body()?.forEach {
                            if (!(repositoryMapList.containsKey(it.language))) {
                                val repositoryMapList1: HashMap<String, TrendingRepositories?> =
                                    repositoryMapList
                                repositoryMapList1[it.language.toString()] =
                                    TrendingRepositories()
                                repositoryMapList1[it.language]?.add(it)
                                repositoryMapList = repositoryMapList1
                            } else {
                                val repositoryMapList1: HashMap<String, TrendingRepositories?> =
                                    repositoryMapList
                                repositoryMapList1[it.language]?.add(it)
                                repositoryMapList = repositoryMapList1
                            }
                        }

                        val listHeader = TrendingRepositories()
                        for (i in repositoryMapList) {
                            if (repositoryMapList[i.key] == null || repositoryMapList[i.key]!!.size == 0)
                                continue
                            listHeader.add(TrendingRepositoriesItem(language = i.key,
                                languageColor = repositoryMapList[i.key]!![0].languageColor))
                            for (j in repositoryMapList[i.key]!!) {
                                listHeader.add(j)
                            }
                        }
                        repositoryList.value = null
                        repositoryListHeader.value = listHeader

                    }

                    override fun onFailure(call: Call<TrendingRepositories>, t: Throwable) {
                        Log.d("MainViewModel", "failure" + t.message)
                        responseSuccessful.postValue("failure")
                    }
                })
            }
        }
    }

}