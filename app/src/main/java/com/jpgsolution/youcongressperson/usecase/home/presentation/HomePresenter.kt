package com.jpgsolution.youcongressperson.usecase.home.presentation

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import com.jpgsolution.youcongressperson.model.datamodel.CongressPerson
import com.jpgsolution.youcongressperson.model.datasource.remote.RemoteDataSource
import com.jpgsolution.youcongressperson.usecase.home.Home
import com.jpgsolution.youcongressperson.util.CheckNetWorkStatus
import com.jpgsolution.youcongressperson.view.DetailActivity
import com.jpgsolution.youcongressperson.view.adapter.Adapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val view: Home.ViewHome,
    private val dataSource: RemoteDataSource,
    private val context: Context
): Home.Presenter, Adapter.SelectedCongressPerson{

    private val netWorkStatus = CheckNetWorkStatus

    override fun getCongressPerson() {
        dataSource.congressPerson.observeForever(dataSourceObserver)
        CoroutineScope(IO).launch {
            dataSource.getCongressPerson()
        }
    }

    override fun checkNetwork() {
        netWorkStatus.getNetwork(context)
        netWorkStatus.netWorkStatus.observeForever(netWorkObserver)
    }

    override fun onStop() {
        dataSource.congressPerson.removeObserver(dataSourceObserver)
        netWorkStatus.netWorkStatus.removeObserver(netWorkObserver)
        netWorkStatus.unRegisterCallBack()
    }

    override fun selected(id: String) {
        if (netWorkStatus.netWorkStatus.value == true) {
            val intent = Intent(context, DetailActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra("congressPersonId", id)
            }
            startActivity(context, intent, null)
        }else view.showNetWorkError("Nao tem rede")
    }

    private val netWorkObserver = Observer<Boolean> {
        if (it) this.getCongressPerson()
        else view.showNetWorkError("Not NetWork Available")
    }

    private val dataSourceObserver = Observer<List<CongressPerson>> {
        view.showCongressPerson(it)
    }

}