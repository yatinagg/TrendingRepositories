package com.yatinagg.trendingrepositories.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yatinagg.trendingrepositories.model.TrendingRepositories
import com.yatinagg.trendingrepositories.model.TrendingRepositoriesItem
import java.lang.reflect.Type

class ResultConverter {
    @TypeConverter
    fun getObjectFromString(value: String): ArrayList<TrendingRepositoriesItem> {
        val listType: Type? = object : TypeToken<List<TrendingRepositoriesItem>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun getStringFromObject(results: List<TrendingRepositoriesItem>): String {
        val gson = Gson()
        return gson.toJson(results)
    }


    @TypeConverter
    fun getStringFromObject(results: TrendingRepositoriesItem): String {
        val gson = Gson()
        return gson.toJson(results)
    }


    @TypeConverter
    fun getStringFromObject(results: TrendingRepositories): String {
        val gson = Gson()
        return gson.toJson(results)
    }


    @TypeConverter
    fun getStringFromObject(results: Any): String {
        val gson = Gson()
        return gson.toJson(results)
    }
}