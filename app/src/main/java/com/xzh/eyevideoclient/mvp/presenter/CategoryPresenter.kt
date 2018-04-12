package com.xzh.eyevideoclient.mvp.presenter

import com.xzh.eyevideoclient.base.BasePresenter
import com.xzh.eyevideoclient.http.exception.ExceptionHandle
import com.xzh.eyevideoclient.mvp.contract.CategoryContract
import com.xzh.eyevideoclient.mvp.model.CategoryModel


class CategoryPresenter : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {

    private val categoryModel: CategoryModel by lazy {
        CategoryModel()
    }

    /**
     * 获取分类数据
     */
    override fun getCategoryData() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = categoryModel.getCategoryData()
                .subscribe({ categoryList ->
                    mRootView?.apply {
                        dismissLoading()
                        showCategory(categoryList)
                    }
                }, { t ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                    }

                })

        addSubscription(disposable)
    }
}