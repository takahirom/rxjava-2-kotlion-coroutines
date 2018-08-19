package com.github.takahirom.rxjava2kotlincoroutines

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class Api {
  val retrofit = Retrofit.Builder()
      .baseUrl("https://randomuser.me/")
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  val api: Service = retrofit.create(Service::class.java)

  interface Service {
    @GET("api?inc=name,email&results=20&noinfo")
    fun listPersons(): Deferred<Response>
  }

  suspend fun fetchPersons(): List<Person> = api.listPersons().await().results
}