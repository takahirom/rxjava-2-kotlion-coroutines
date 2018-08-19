package com.github.takahirom.rxjava2kotlincoroutines

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class ViewModel(
    private val api: Api = Api(),
    private val coroutineContext: CoroutineContext = CommonPool
) {
  private val mutablePersons = MutableLiveData<List<Person>>()
  val persons: LiveData<List<Person>> = mutablePersons

  fun onCreate() {
    launch(coroutineContext) {
      val persons = api.fetchPersons()
      mutablePersons.postValue(persons)
    }
  }
}