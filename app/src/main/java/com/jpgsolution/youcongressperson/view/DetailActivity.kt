package com.jpgsolution.youcongressperson.view

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jpgsolution.youcongressperson.R
import com.jpgsolution.youcongressperson.databinding.ActivityDetailBinding
import com.jpgsolution.youcongressperson.di.DaggerDataSourceComponent
import com.jpgsolution.youcongressperson.model.datamodel.congressPersonDetail.CongressPersonDetails
import com.jpgsolution.youcongressperson.usecase.congressPersonDetail.Detail
import com.jpgsolution.youcongressperson.usecase.congressPersonDetail.presentation.DetailPresenter
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity(), Detail.View{

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailPresenter: DetailPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()
        setUpPresenter()
        binding.showDetails.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.details,AutoTransition())
            binding.details.visibility = if ( binding.details.visibility == View.GONE ) View.VISIBLE else View.GONE

        }
    }

    override fun onStart() {
        super.onStart()
        val id = intent.extras?.getString("congressPersonId")
        id?.let {
            detailPresenter.getCongressPersonDetail(it)
        }
    }

    private fun setUpToolbar(){
        setSupportActionBar(binding.appBarLayout.toolbar)
        binding.appBarLayout.toolbar.title = " Detalhes"
        binding.appBarLayout.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.appBarLayout.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpPresenter(){
        val remoteDataSource = DaggerDataSourceComponent.builder().build().remoteDataSource()
        detailPresenter = DetailPresenter(this, remoteDataSource)
    }

    override fun showCongressPersonDetail(congressPerson: CongressPersonDetails) {
        Picasso.get().load(congressPerson.dados.ultimoStatus.urlFoto).into(binding.photo)

        binding.congressPerson = congressPerson
    }
}