package com.jpgsolution.youcongressperson.model.datasource.remote

import com.jpgsolution.youcongressperson.model.datamodel.congressPersonDetail.CongressPersonDetails
import com.jpgsolution.youcongressperson.model.datamodel.CongressPersons
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPoint {

    @GET("/api/v2/deputados")
    fun getAllCongressPerson(): Call<CongressPersons>


    @GET("/api/v2/deputados/{id}")
    fun getCongressPersonDetail(@Path("id")id: String): Call<CongressPersonDetails>
}