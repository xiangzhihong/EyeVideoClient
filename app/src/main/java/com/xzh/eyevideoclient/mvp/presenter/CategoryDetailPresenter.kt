package com.xzh.eyevideoclient.mvp.presenter

import com.xzh.eyevideoclient.base.BasePresenter
import com.xzh.eyevideoclient.mvp.contract.CategoryDetailContract
import com.xzh.eyevideoclient.mvp.model.CategoryDetailModel


/**
 * desc:分类详情
 */
class CategoryDetailPresenter : BasePresenter<CategoryDetailContract.View>(), CategoryDetailContract.Presenter {

    private val categoryDetailModel by lazy {
        CategoryDetailModel()
    }

    private var nextPageUrl: String? = null

    /**
     * 获取分类详情的列表信息
     */
    override fun getCategoryDetailList(id: Long) {
        checkViewAttached()
        val disposable = categoryDetailModel.getCategoryDetailList(id)
                .subscribe({ issue ->
                    mRootView?.apply {
                        nextPageUrl = issue.nextPageUrl
                        setCateDetailList(issue.itemList)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        showError(throwable.toString())
                    }
                })
        addSubscription(disposable)
    }

    /**
     * 加载更多数据
     */
    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            categoryDetailModel.loadMoreData(it)
                    .subscribe({ issue ->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setCateDetailList(issue.itemList)
                        }
                    }, { throwable ->
                        mRootView?.apply {
                            showError(throwable.toString())
                        }
                    })
        }
        disposable?.let { addSubscription(it) }
    }
}