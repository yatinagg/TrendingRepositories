package com.yatinagg.trendingrepositories.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yatinagg.trendingrepositories.R
import com.yatinagg.trendingrepositories.adapter.RepositoryAdapter
import com.yatinagg.trendingrepositories.databinding.ActivityMainBinding
import com.yatinagg.trendingrepositories.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter = RepositoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // create  layoutManager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        // pass it to rvLists layoutManager
        binding.recyclerView.layoutManager = layoutManager
        // initialize the adapter,
        // and pass the required argument
        binding.recyclerView.adapter = adapter
        viewModel.repositoryList.observe(this, Observer {
            adapter.setRepositoriesList(it)
            Log.d(TAG, it.toString())
            Log.d(TAG, "how${adapter.repositories}")
        })
        lifecycleScope.launch {
            viewModel.getTrendingRepos()
        }

    }
}