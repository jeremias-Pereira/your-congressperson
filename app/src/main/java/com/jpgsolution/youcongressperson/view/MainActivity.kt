package com.jpgsolution.youcongressperson

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jpgsolution.youcongressperson.adapter.Adapter
import com.jpgsolution.youcongressperson.databinding.ActivityMainBinding
import com.jpgsolution.youcongressperson.model.CongressPerson
import com.jpgsolution.youcongressperson.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dataList: MutableList<CongressPerson> = mutableListOf()
    private lateinit var adapter: Adapter
    private  val repository = Repository()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        adapter = Adapter(dataList)
        repository.congressPerson.observe({lifecycle}){listCongressPerson ->
            listCongressPerson?.let {
                dataList.clear()
                dataList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
        repository.getCongressPerson()
        initRecycler()
    }

    private fun initRecycler(){
        binding.recycler.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = adapter
    }

}