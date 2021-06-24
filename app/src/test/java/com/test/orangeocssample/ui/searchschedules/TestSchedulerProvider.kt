package com.test.orangeocssample.ui.searchschedules

import com.test.orangeocssample.utils.rxjava.SchedulerWrapper
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(private val scheduler: TestScheduler) : SchedulerWrapper {
    override fun io() = scheduler
    override fun main() = scheduler
}