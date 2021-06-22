package com.test.orangeocssample.ui.searchschedules

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DefaultScheduler : SchedulerWrapper {
    override fun io() = Schedulers.io()
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}