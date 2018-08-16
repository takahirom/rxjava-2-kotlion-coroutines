package com.github.takahirom.rxjava2kotlincoroutines

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class Api {
  val retrofit = Retrofit.Builder()
      .baseUrl("https://randomuser.me/")
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  val api:Service = retrofit.create(Service::class.java)
  interface Service {
    @GET("api?inc=name,email&results=20&noinfo")
    fun listPersons(): Single<Response>
  }

  fun fetchPersons():Single<List<Person>> = api.listPersons().map { it.results }
}