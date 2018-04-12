package com.xzh.eyevideoclient.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class BasePresenter<T : IBaseView> : IPresenter<T> {

    var mRootView: T? = null
        private set

    private var compositeDisposable = CompositeDisposable()

    /**
     * 绑定视图
     */
    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    /**
     * 解绑视图
     */
    override fun detachView() {
        mRootView = null
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }

    }

    private val isViewAttached: Boolean
        get() = mRootView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw ViewNotAttachedException()
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private class ViewNotAttachedException internal constructor() : RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")


}