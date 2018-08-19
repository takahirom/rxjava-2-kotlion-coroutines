package com.github.takahirom.rxjava2kotlincoroutines

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.shopify.livedataktx.SingleLiveData
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class PersonsViewModel(
    private val api: Api = Api(),
    coroutineContext: CoroutineContext = CommonPool
) : ViewModel() {
  private val compositeJob = Job()
  private val mutablePersons = MutableLiveData<List<Person>>()
  val persons: LiveData<List<Person>> = mutablePersons

  private val singleError: SingleLiveData<Exception> = SingleLiveData()
  val errors: LiveData<Exception> = singleError

  init {
    launch(coroutineContext, parent = compositeJob) {
      try {
        val persons = api.fetchPersons()
        mutablePersons.postValue(persons)
      } catch (e: Exception) {
        singleError.postValue(e)
      }
    }
  }

  override fun onCleared() {
    compositeJob.cancel()
  }
}