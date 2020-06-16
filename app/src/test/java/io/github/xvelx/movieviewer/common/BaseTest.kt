package io.github.xvelx.movieviewer.common

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseTest {

    @Rule
    @JvmField
    val instantExecutorRule = TestTaskExecutorRule()

    @Before
    open fun setUp() {
        MockKAnnotations.init(this)

        // Set Main Thread Schedulers
        mockkStatic(AndroidSchedulers::class)

        every {
            AndroidSchedulers.mainThread()
        } returns Schedulers.trampoline()
    }

    @After
    open fun tearDown() {
        unmockkStatic(AndroidSchedulers::class)
    }
}