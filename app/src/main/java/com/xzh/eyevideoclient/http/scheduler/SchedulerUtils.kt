package com.xzh.eyevideoclient.http.scheduler


object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}
