package com.xzh.eyevideoclient.utils

import android.content.Context
import android.util.DisplayMetrics


object DisplayUtils {


    private var displayMetrics: DisplayMetrics? = null
    private var screenWidth: Int? = null
    private var screenHeight: Int? = null
    private var screenDpi: Int? = null

    fun init(context: Context) {
        displayMetrics = context.resources.displayMetrics
        screenWidth = displayMetrics?.widthPixels
        screenHeight = displayMetrics?.heightPixels
        screenDpi = displayMetrics?.densityDpi
    }

    //UI图的大小
    private val STANDARD_WIDTH = 1080
    private val STANDARD_HEIGHT = 1920


    fun getScreenWidth(): Int? {
        return screenWidth
    }

    fun getScreenHeight(): Int? {
        return screenHeight
    }


    fun getPaintSize(size: Int): Int? {
        return getRealHeight(size)
    }


    fun getRealWidth(px: Int): Int? {
        //ui图的宽度
        return getRealWidth(px, STANDARD_WIDTH.toFloat())
    }


    fun getRealWidth(px: Int, parentWidth: Float): Int? {
        return (px / parentWidth * getScreenWidth()!!).toInt()
    }


    fun getRealHeight(px: Int): Int? {
        //ui图的宽度
        return getRealHeight(px, STANDARD_HEIGHT.toFloat())
    }


    fun getRealHeight(px: Int, parentHeight: Float): Int? {
        return (px / parentHeight * getScreenHeight()!!).toInt()
    }

    fun dip2px(dipValue: Float): Int? {
        val scale = displayMetrics?.density
        return (dipValue * scale!! + 0.5f).toInt()
    }

    fun dip2px(context: Context,dipValue:Float):Float{
        val scale :Float = context.resources.displayMetrics.density
        return dipValue * scale + 0.5f
    }
    fun px2dip(context: Context,pxValue : Float) :Int{
        val scale : Float = context.resources.displayMetrics.density
        return (pxValue/scale+0.5f).toInt()
    }
}