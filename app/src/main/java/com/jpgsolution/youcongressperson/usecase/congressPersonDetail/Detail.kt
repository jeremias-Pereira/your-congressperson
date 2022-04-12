package com.jpgsolution.youcongressperson.usecase.congressPersonDetail

import com.jpgsolution.youcongressperson.model.datamodel.congressPersonDetail.CongressPersonDetails

interface Detail {

    interface Presenter{
        fun getCongressPersonDetail(id: String)
        fun onStop()
    }

    interface View{
        fun showCongressPersonDetail(congressPerson: CongressPersonDetails)
    }
}