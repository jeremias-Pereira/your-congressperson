package com.jpgsolution.youcongressperson.model.datasource.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jpgsolution.youcongressperson.model.datamodel.*
import com.jpgsolution.youcongressperson.model.datamodel.congressPersonDetail.CongressPersonDetails
import com.jpgsolution.youcongressperson.model.datasource.remote.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(){

    private val retrofit = RetrofitService.retrofit()
    private val _congressPerson: MutableLiveData<List<CongressPerson>> by lazy(::MutableLiveData)
    val congressPerson: LiveData<List<CongressPerson>> = _congressPerson
    private val _congressPersonDetail: MutableLiveData<CongressPersonDetails> by lazy(::MutableLiveData)
    val congressPersonDetail: LiveData<CongressPersonDetails> = _congressPersonDetail

    fun getCongressPerson(){
        val endPoint = retrofit.create(EndPoint::class.java)
        val callBack = endPoint.getAllCongressPerson()
        callBack.enqueue(object : Callback<CongressPersons>{
            override fun onResponse(
                call: Call<CongressPersons>,
                response: Response<CongressPersons>
            ) {
                when ( response.code()){
                    200 -> _congressPerson.postValue(response.body()!!.dados)
                }
            }
            override fun onFailure(call: Call<CongressPersons>, t: Throwable) {
            }
        })
    }

    fun getCongressPersonDetails(id: String){
        val endPoint = retrofit.create(EndPoint::class.java)
        val callback = endPoint.getCongressPersonDetail(id)

        callback.enqueue(object : Callback<CongressPersonDetails>{
            override fun onResponse(
                call: Call<CongressPersonDetails>,
                response: Response<CongressPersonDetails>
            ) {
                _congressPersonDetail.postValue(response.body())

            }

            override fun onFailure(call: Call<CongressPersonDetails>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}