package com.yatinagg.trendingrepositories.viewmodel

import android.util.Log
import androidx.lifecycle.*
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

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    private val TAG = "MainViewModel"

    val repositoryList = MutableLiveData<TrendingRepositories?>()
    val responseSuccessful = MutableLiveData<String>()
    var repositoryMapList = HashMap<String,TrendingRepositories?>()
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
                        response: Response<TrendingRepositories>
                    ) {
                        Log.d("MainViewModel","response outside viewmodel insert ${response.body()}")
                            viewModelScope.launch {
                                if(response.body() != null) {
                                    Log.d("MainViewModel","response insert ${response.body()}")
                                    repository.insertSubmission(
                                        response.body()!!,
                                        database
                                    )
                                }
                            }
                        Log.d("MainViewModel","response after viewmodel insert ${response.body()}")

                        repositoryList.postValue(null)
                        repositoryMapList = HashMap()
                        repositoryList.postValue(response.body())
                        responseSuccessful.postValue("success")
                        response.body()?.forEach {
                            Log.d(TAG, "check1234${it.language} $it")
                            if(repositoryMapList != null && !(repositoryMapList!!.containsKey(it.language))){
                                val repositoryMapList1: HashMap<String,TrendingRepositories?> = repositoryMapList
                                repositoryMapList1!![it.language.toString()] = TrendingRepositories()
                                repositoryMapList1!![it.language]?.add(it)
                                repositoryMapList = repositoryMapList1!!
                            } else if(repositoryMapList != null){
                                val repositoryMapList1: HashMap<String,TrendingRepositories?> = repositoryMapList
                                repositoryMapList1!![it.language]?.add(it)
                                repositoryMapList = repositoryMapList1!!
                            }
                            else{
                                val repositoryMapList1 = HashMap<String,TrendingRepositories?>()
                                repositoryMapList1[it.language.toString()] = TrendingRepositories()
                                repositoryMapList1[it.language]?.add(it)
//                                Log.d(TAG, "check123${repositoryMapList1} $i")
                                repositoryMapList = repositoryMapList1
                            }
                            Log.d(TAG, "check12w${repositoryMapList?.size}")
                        }

                        Log.d("MainViewModel","response end viewmodel insert ${response.body()}")
                        val listHeader = TrendingRepositories()
                        for(i in repositoryMapList){
                            Log.d(TAG, "check123$ ${i.key}${repositoryMapList[i.key]!!.size}")
                            if(repositoryMapList[i.key] == null || repositoryMapList[i.key]!!.size == 0)
                                continue
                            listHeader.add(TrendingRepositoriesItem(language = i.key, languageColor = repositoryMapList[i.key]!![0].languageColor))
                            for(j in repositoryMapList[i.key]!!){
                                listHeader.add(j)
                            }
                        }
                        repositoryList.value = null
                        repositoryListHeader.value = listHeader
                        Log.d(TAG, "check12${repositoryMapList}")
                        Log.d(TAG, "check123${listHeader}")
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