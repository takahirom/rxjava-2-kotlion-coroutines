package com.github.takahirom.rxjava2kotlincoroutines

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.Delay
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.timeunit.TimeUnit
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.experimental.CoroutineContext

class PresenterTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @Test fun onCreate() = runBlocking<Unit> {
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
    val presenter = Presenter(
        api = api,
        scheduler = Schedulers.trampoline(),
        coroutineContext = DirectCoroutineContext()
    )
    val personObserver: Observer<List<Person>> = mock()
    presenter.persons.observeForever(personObserver)

    presenter.onCreate()

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