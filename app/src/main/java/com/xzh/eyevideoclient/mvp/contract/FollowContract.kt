package com.xzh.eyevideoclient.mvp.contract

import com.xzh.eyevideoclient.base.IBaseView
import com.xzh.eyevideoclient.base.IPresenter
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean


interface FollowContract {

    interface View : IBaseView {
        fun setFollowInfo(issue: HomeBean.Issue)
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestFollowList()
        fun loadMoreData()
    }
}