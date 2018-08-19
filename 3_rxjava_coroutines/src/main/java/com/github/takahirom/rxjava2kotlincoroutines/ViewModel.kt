package com.github.takahirom.rxjava2kotlincoroutines

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.rx2.rxSingle
import kotlin.coroutines.experimental.CoroutineContext

class ViewModel(
    private val api: Api = Api(),
    private val scheduler: Scheduler = Schedulers.io(),
    val coroutineContext: CoroutineContext = CommonPool
) {
  private val mutablePersons = MutableLiveData<List<Person>>()
  val persons: LiveData<List<Person>> = mutablePersons

  fun onCreate() {
    rxSingle(coroutineContext) { api.fetchPersons() }
        .subscribeOn(scheduler)
        .subscribe(mutablePersons::postValue)
  }
}