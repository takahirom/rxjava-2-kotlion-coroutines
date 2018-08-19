package com.github.takahirom.rxjava2kotlincoroutines

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class ViewModel(
    private val api: Api = Api(),
    private val scheduler: Scheduler = Schedulers.io()
) {
  private val mutablePersons = MutableLiveData<List<Person>>()
  val persons: LiveData<List<Person>> = mutablePersons

  fun onCreate() {
    api.fetchPersons()
        .subscribeOn(scheduler)
        .subscribe(mutablePersons::postValue)
  }
}