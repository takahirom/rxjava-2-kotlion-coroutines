package com.github.takahirom.rxjava2kotlincoroutines

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
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

class ViewModelTest {
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
    val viewModel = ViewModel(api = api, coroutineContext = DirectCoroutineContext())
    val personObserver: Observer<List<Person>> = mock()
    viewModel.persons.observeForever(personObserver)

    viewModel.onCreate()

    verify(personObserver).onChanged(sampleData)
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