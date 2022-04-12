package com.jpgsolution.youcongressperson.usecase.home

import com.jpgsolution.youcongressperson.model.datamodel.CongressPerson

interface Home {

    interface Presenter{
        fun getCongressPerson()
        fun checkNetwork()
        fun onStop()
    }

    interface ViewHome{
        fun showNetWorkError(message: String)
        fun showCongressPerson(congressPerson: List<CongressPerson>)
    }
}