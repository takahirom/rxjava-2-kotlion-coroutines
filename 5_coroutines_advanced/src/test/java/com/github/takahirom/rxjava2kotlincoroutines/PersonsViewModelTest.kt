package com.github.takahirom.rxjava2kotlincoroutines

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.Delay
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.timeunit.TimeUnit
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.experimental.CoroutineContext

class PersonsViewModelTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @Test fun onCreate() = runBlocking {
    val api: Api = mock()
    val sampleData = listOf(
        Person(
            Name("mr", "FirstName", "LastName"),
            "jon.smith@example.com"
        )
    )
    whenever(api.fetchPersons())
        .doReturn(
            sampleData
        )
    val presenter = PersonsViewModel(api = api, coroutineContext = DirectCoroutineContext())
    val personObserver: Observer<List<Person>> = mock()

    presenter.persons.observeForever(personObserver)

    verify(personObserver).onChanged(sampleData)
  }

  @Test fun onCreateWhenError() = runBlocking {
    val api: Api = mock()
    val sampleException = RuntimeException("Sample exception")
    whenever(api.fetchPersons())
        .doThrow(
            sampleException
        )
    val presenter = PersonsViewModel(api = api, coroutineContext = DirectCoroutineContext())
    val personObserver: Observer<List<Person>> = mock()
    val errorObserver: Observer<Exception> = mock()

    presenter.persons.observeForever(personObserver)
    presenter.errors.observeForever(errorObserver)

    verify(personObserver, never()).onChanged(any())
    verify(errorObserver).onChanged(sampleException)
  }


  class DirectCoroutineContext : CoroutineDispatcher(), Delay {
    override fun scheduleResumeAfterDelay(time: Long, unit: TimeUnit,
        continuation: CancellableContinuation<Unit>) {
      continuation.resume(Unit)
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
      block.run()
    }
  }
}