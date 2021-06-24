package com.test.orangeocssample.utils.rxjava

import io.reactivex.Scheduler

interface SchedulerWrapper {
    fun io(): Scheduler
    fun main(): Scheduler
}
