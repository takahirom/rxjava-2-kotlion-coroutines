package com.github.takahirom.rxjava2kotlincoroutines

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test

class ViewModelTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @Test fun onCreate() {
    val api: Api = mock()
    val sampleData = listOf(
        Person(
            Name("mr", "FirstName", "LastName"),
            "jon.smith@example.com"
        )
    )
    whenever(api.fetchPersons())
        .doReturn(
            Single.just(
                sampleData
            )
        )
    val viewModel = ViewModel(api = api, scheduler = Schedulers.trampoline())
    val personObserver: Observer<List<Person>> = mock()
    viewModel.persons.observeForever(personObserver)

    viewModel.onCreate()

    verify(personObserver).onChanged(sampleData)
  }
}