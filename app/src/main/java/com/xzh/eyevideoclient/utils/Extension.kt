package com.xzh.eyevideoclient.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun Context.showToast(message: String): Toast {
    var toast: Toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
    return toast
}

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}

fun Context.dataFormat(total: Long): String {
    var result: String
    var speedReal: Int = (total / (1024)).toInt()
    result = if (speedReal < 512) {
        speedReal.toString() + " KB"
    } else {
        val mSpeed = speedReal / 1024.0
        (Math.round(mSpeed * 100) / 100.0).toString() + " MB"
    }
    return result
}

inline fun <reified T : Activity> Activity.newIntent() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}




