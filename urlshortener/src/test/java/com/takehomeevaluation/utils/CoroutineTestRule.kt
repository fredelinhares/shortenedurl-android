package com.takehomeevaluation.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestRule : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}