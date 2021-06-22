package com.test.orangeocssample.ui.searchschedules

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(private val scheduler: TestScheduler) : SchedulerWrapper {
    override fun io() = scheduler
    override fun main() = scheduler
}