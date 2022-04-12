package com.jpgsolution.youcongressperson.usecase.congressPersonDetail.presentation

import androidx.lifecycle.Observer
import com.jpgsolution.youcongressperson.model.datamodel.congressPersonDetail.CongressPersonDetails
import com.jpgsolution.youcongressperson.model.datasource.remote.RemoteDataSource
import com.jpgsolution.youcongressperson.usecase.congressPersonDetail.Detail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailPresenter @Inject constructor(
    private val view: Detail.View,
    private val dataSource: RemoteDataSource
): Detail.Presenter {

    override fun getCongressPersonDetail(id: String) {
        dataSource.congressPersonDetail.observeForever(observer)
        CoroutineScope(IO).launch {
            dataSource.getCongressPersonDetails(id)
        }
    }

    override fun onStop() {
        dataSource.congressPersonDetail.removeObserver(observer)
    }

    private val observer = Observer<CongressPersonDetails> { congressPersonDetails ->
        val listSocialMediaPrefix = listOf(
            "https://www.facebook.com/",
            "https://www.instagram.com/",
            "https://www.twitter.com/",
            "https://www.youtube.com/")
        congressPersonDetails.dados.redeSocial.let {list ->
            for ((index, item) in list.withIndex()){
                if (!item.contains("https://"))
                    list[index] = "${listSocialMediaPrefix[index]}${item}"
            }
        }

        view.showCongressPersonDetail(congressPersonDetails)
    }
}