package com.xzh.eyevideoclient.view.recyclerview

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mView: SparseArray<View>?=null

    init {
        mView = SparseArray()
    }

    fun <T : View> getView(viewId: Int): T {
        var view: View? = mView?.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mView?.put(viewId, view)
        }
        return view as T
    }


    @SuppressLint("SetTextI18n")
    //通用的功能进行封装  设置文本 设置条目点击事件  设置图片
    fun setText(viewId: Int, text: CharSequence): ViewHolder {
        val view = getView<TextView>(viewId)
        view.text = "" + text
        //希望可以链式调用
        return this
    }


    /**
     * 设置本地图片
     *
     * @param viewId
     * @param resId
     * @return
     */
    fun setImageResource(viewId: Int, resId: Int): ViewHolder {
        val iv = getView<ImageView>(viewId)
        iv.setImageResource(resId)
        return this
    }

    /**
     * 加载图片资源路径
     *
     * @param viewId
     * @param imageLoader
     * @return
     */
    fun setImagePath(viewId: Int, imageLoader: HolderImageLoader): ViewHolder {
        val iv = getView<ImageView>(viewId)
        imageLoader.loadImage(iv, imageLoader.path)
        return this
    }

    abstract class HolderImageLoader(val path: String) {
        abstract fun loadImage(iv: ImageView, path: String)
    }

    /**
     * 设置View的Visibility
     */
    fun setViewVisibility(viewId: Int, visibility: Int): ViewHolder {
        getView<View>(viewId).visibility = visibility
        return this
    }

    /**
     * 设置条目点击事件
     */
    fun setOnItemClickListener(listener: View.OnClickListener) {
        itemView.setOnClickListener(listener)
    }

    /**
     * 设置条目长按事件
     */
    fun setOnItemLongClickListener(listener: View.OnLongClickListener) {
        itemView.setOnLongClickListener(listener)
    }

}