package com.xzh.eyevideoclient.base

import android.os.Bundle
import android.support.annotation.LayoutRes

import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xzh.eyevideoclient.view.MultipleStatusView


abstract class BaseFragment: Fragment() {

    private var isViewCreated = false
    private var isDataLoaded = false
    //页面底图
    protected var mMultipleStatusView: MultipleStatusView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayoutId(),null)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        initView()
        lazyLoadDataIfPrepared()
        mMultipleStatusView?.setOnClickListener(mRetryClickListener)
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewCreated && !isDataLoaded) {
            lazyLoad()
            isDataLoaded = true
        }
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }

    abstract fun getLayoutId():Int

    abstract fun initView()

    abstract fun lazyLoad()

}