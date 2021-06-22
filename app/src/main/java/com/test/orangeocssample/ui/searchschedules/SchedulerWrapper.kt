package com.test.orangeocssample.ui.searchschedules

import io.reactivex.Scheduler

interface SchedulerWrapper {
    fun io(): Scheduler
    fun main(): Scheduler
}
