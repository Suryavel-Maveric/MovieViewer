package io.github.xvelx.movieviewer.common

import io.mockk.MockKAnnotations
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
    }

    @After
    open fun tearDown() = Unit
}