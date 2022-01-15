package com.yatinagg.trendingrepositories.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.yatinagg.trendingrepositories.R
import com.yatinagg.trendingrepositories.adapter.RepositoryAdapter
import com.yatinagg.trendingrepositories.database.AppDatabase
import com.yatinagg.trendingrepositories.databinding.ActivityMainBinding
import com.yatinagg.trendingrepositories.model.TrendingRepositories
import com.yatinagg.trendingrepositories.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter = RepositoryAdapter()
    private lateinit var mShimmerFrameLayout: ShimmerFrameLayout
    private lateinit var ivError: ImageView
    private lateinit var vError: View
    private lateinit var buttonRetry: Button
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var currentDisplay = "Home"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mShimmerFrameLayout = findViewById(R.id.shimmer_view_container)
        ivError = findViewById(R.id.iv_error)
        vError = findViewById(R.id.view_error)
        buttonRetry = findViewById(R.id.button_retry)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        // create  layoutManager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        // pass it to rvLists layoutManager
        binding.recyclerView.layoutManager = layoutManager
        // initialize the adapter,
        // and pass the required argument
        binding.recyclerView.adapter = adapter
//        viewModel.repositoryList.observe(this, Observer {

        setObserver(viewModel.repositoryList)

        getTrendingRepos()
        updateUIWithResponse()
        // retry button listener
        buttonRetry.setOnClickListener {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.itemAnimator = null
            getTrendingRepos()
        }
        // swipe refresh listener
        swipeRefreshLayout.setOnRefreshListener(refreshListener)

        val optionsMenu = findViewById<TextView>(R.id.options_menu)
        optionsMenu.setOnClickListener {
            val popupMenu = PopupMenu(this,optionsMenu)
            popupMenu.menuInflater.inflate(R.menu.custom_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_home -> {
                        currentDisplay = "Home"
                        Toast.makeText(
                            this@MainActivity,
                            "You Clicked : " + item.title,
                            Toast.LENGTH_SHORT
                        ).show()
                        setObserver(viewModel.repositoryList)
                    }
                    R.id.action_header -> {
                        currentDisplay = "Header"
                        Toast.makeText(
                            this@MainActivity,
                            "You Clicked : " + item.title,
                            Toast.LENGTH_SHORT
                        ).show()
                        setObserver(viewModel.repositoryListHeader)
                    }
                    R.id.action_stared -> {
                        currentDisplay = "Stared"
                        Toast.makeText(
                            this@MainActivity,
                            "You Clicked : " + item.title,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

    private fun setObserver(repositoryList: MutableLiveData<TrendingRepositories?>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        // initialize the adapter,
        // and pass the required argument
        binding.recyclerView.adapter = adapter
        binding.recyclerView.itemAnimator = null
        repositoryList.observe(this, {
            if (it != null) {
                adapter.setRepositoriesList(it)
            }
            Log.d(TAG, it.toString())
            Log.d(TAG, "how${adapter.repositories}")
        })
    }

    private fun updateUIWithResponse(){
        viewModel.responseSuccessful.observe(this, {
            when(it){
                "success" -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    mShimmerFrameLayout.visibility = View.GONE
                    ivError.visibility = View.GONE
                    vError.visibility = View.GONE
                    buttonRetry.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = false
                }
                "failure" -> {
                    mShimmerFrameLayout.visibility = View.GONE
                    ivError.visibility = View.VISIBLE
                    vError.visibility = View.VISIBLE
                    buttonRetry.visibility = View.VISIBLE
                    swipeRefreshLayout.isRefreshing = false
                }
                else -> {
                    mShimmerFrameLayout.visibility = View.VISIBLE
                    ivError.visibility = View.GONE
                    vError.visibility = View.GONE
                    buttonRetry.visibility = View.GONE
                }
            }
        })
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        swipeRefreshLayout.isRefreshing = true
        // call api to reload the screen
        binding.recyclerView.visibility = View.GONE
        binding.recyclerView.itemAnimator = null
        getTrendingRepos()
        when(currentDisplay){
            "Home" -> setObserver(viewModel.repositoryList)
            else -> setObserver(viewModel.repositoryListHeader)
        }
        Log.d(TAG,"current $currentDisplay")
    }

    private fun getTrendingRepos(){
        val database = AppDatabase.getInstance(this)
        lifecycleScope.launch {
            viewModel.getTrendingRepos(database)
        }
    }

    override fun onResume() {
        super.onResume()
        mShimmerFrameLayout.startShimmerAnimation()
    }

    override fun onStop() {
        super.onStop()
        mShimmerFrameLayout.stopShimmerAnimation()
    }
}