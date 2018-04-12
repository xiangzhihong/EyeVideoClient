package com.xzh.eyevideoclient.mvp.presenter

import com.xzh.eyevideoclient.base.BasePresenter
import com.xzh.eyevideoclient.http.exception.ExceptionHandle
import com.xzh.eyevideoclient.mvp.contract.SearchContract
import com.xzh.eyevideoclient.mvp.model.SearchModel


/**
 * 搜索Presenter
 */
class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {

    private var nextPageUrl: String? = null
    private val searchModel by lazy { SearchModel() }

    /**
     * 获取热门关键词
     */
    override fun requestHotWordData() {
        checkViewAttached()
        checkViewAttached()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(disposable = searchModel.requestHotWordData()
                .subscribe({ string ->
                    mRootView?.apply {
                        setHotWordData(string)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                }))
    }

    /**
     * 查询关键词
     */
    override fun querySearchData(words: String) {
        checkViewAttached()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(disposable = searchModel.getSearchResult(words)
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        if (issue.count > 0 && issue.itemList.size > 0) {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        } else
                            setEmptyView()
                    }
                }, { throwable ->
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                })
        )

    }

    /**
     * 加载更多数据
     */
    override fun loadMoreData() {
        checkViewAttached()
        nextPageUrl?.let {
            addSubscription(disposable = searchModel.loadMoreData(it)
                    .subscribe({ issue ->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        }
                    }, { throwable ->
                        mRootView?.apply {
                            showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                        }
                    }))
        }

    }


}