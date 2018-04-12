package com.xzh.eyevideoclient


import android.app.Application
import android.content.Context
import com.xzh.eyevideoclient.utils.DisplayUtils
import kotlin.properties.Delegates

class MyApplication : Application(){

    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context=this
        DisplayUtils.init(this)
    }


}
