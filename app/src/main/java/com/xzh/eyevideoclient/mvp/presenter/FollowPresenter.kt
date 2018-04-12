package com.xzh.eyevideoclient.mvp.presenter

import com.xzh.eyevideoclient.base.BasePresenter
import com.xzh.eyevideoclient.http.exception.ExceptionHandle
import com.xzh.eyevideoclient.mvp.contract.FollowContract
import com.xzh.eyevideoclient.mvp.model.FollowModel


/**
 * 获取关注信息请求
 */
class FollowPresenter : BasePresenter<FollowContract.View>(), FollowContract.Presenter {

    private val followModel by lazy { FollowModel() }
    private var nextPageUrl: String? = null

    override fun requestFollowList() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = followModel.requestFollowList()
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        nextPageUrl = issue.nextPageUrl
                        setFollowInfo(issue)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        //处理异常
                        showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    /**
     * 加载更多
     */
    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            followModel.loadMoreData(it)
                    .subscribe({ issue ->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setFollowInfo(issue)
                        }
                    }, { t ->
                        mRootView?.apply {
                            showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                        }
                    })
        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }
}