package com.xzh.eyevideoclient.utils

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast
import com.xzh.eyevideoclient.MyApplication
import java.util.*


object UiUtils{


    fun Fragment.showToast(content: String): Toast {
        val toast = Toast.makeText(MyApplication.context, content, Toast.LENGTH_SHORT)
        toast.show()
        return toast
    }

    fun Context.showToast(content: String): Toast {
        val toast = Toast.makeText(MyApplication.context, content, Toast.LENGTH_SHORT)
        toast.show()
        return toast
    }


    fun getToday(): String {
        var list = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        var data: Date = Date()
        var calendar: Calendar = Calendar.getInstance()
        calendar.time = data
        var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0) {
            index = 0
        }
        return list[index]
    }
}