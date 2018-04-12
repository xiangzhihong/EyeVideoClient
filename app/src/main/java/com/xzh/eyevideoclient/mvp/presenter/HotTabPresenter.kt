package com.xzh.eyevideoclient.mvp.presenter

import com.xzh.eyevideoclient.base.BasePresenter
import com.xzh.eyevideoclient.http.exception.ExceptionHandle
import com.xzh.eyevideoclient.mvp.contract.HotTabContract
import com.xzh.eyevideoclient.mvp.model.HotTabModel


class HotTabPresenter: BasePresenter<HotTabContract.View>(),HotTabContract.Presenter {

    private val hotTabModel by lazy { HotTabModel() }


    override fun getTabInfo() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = hotTabModel.getTabInfo()
                .subscribe({
                    tabInfo->
                    mRootView?.setTabInfo(tabInfo)
                },{
                    throwable->  //异常处理
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                })
        addSubscription(disposable)
    }
}