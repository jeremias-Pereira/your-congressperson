package com.jpgsolution.youcongressperson.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Window
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jpgsolution.youcongressperson.R
import com.jpgsolution.youcongressperson.databinding.ActivityMainBinding
import com.jpgsolution.youcongressperson.di.DaggerDataSourceComponent
import com.jpgsolution.youcongressperson.model.datamodel.CongressPerson
import com.jpgsolution.youcongressperson.usecase.home.Home
import com.jpgsolution.youcongressperson.usecase.home.presentation.HomePresenter
import com.jpgsolution.youcongressperson.view.adapter.Adapter

class MainActivity : AppCompatActivity(), Home.ViewHome{

    private lateinit var binding: ActivityMainBinding
    private val dataList: MutableList<CongressPerson> = mutableListOf()
    private lateinit var adapter: Adapter
    private lateinit var presenter: HomePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpToolbar()
        setUpPresenter()
        setDataInRecyclerView()
        actionRefresh()
    }

    override fun onStart() {
        super.onStart()
        presenter.checkNetwork()
    }

    private fun setUpToolbar(){
//        binding.appBarLayout.toolbar.title = "Seus Parlamentares"
        setSupportActionBar(binding.appBarLayout.toolbar)
    }

    private fun setUpPresenter(){
        val repository = DaggerDataSourceComponent
            .builder()
            .build()
            .remoteDataSource()
        presenter = HomePresenter(this, repository, applicationContext)
    }

    private fun setDataInRecyclerView(){
        adapter = Adapter(dataList, presenter)
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = adapter
    }

    override fun showNetWorkError(message: String) {
        Log.d("STATE", message)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showCongressPerson(congressPerson: List<CongressPerson>) {
        congressPerson.let {
            dataList.clear()
            dataList.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    private fun actionRefresh(){
        binding.refresh.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.refresh.isRefreshing = false
                presenter.getCongressPerson()
            },5000)
        }
    }
}