package com.xzh.eyevideoclient.mvp.contract

import com.xzh.eyevideoclient.base.IBaseView
import com.xzh.eyevideoclient.base.IPresenter
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean


interface CategoryDetailContract {

    interface View: IBaseView {
        fun setCateDetailList(itemList:ArrayList<HomeBean.Issue.Item>)
        fun showError(errorMsg:String)
    }

    interface Presenter: IPresenter<View> {
        fun getCategoryDetailList(id:Long)
        fun loadMoreData()
    }
}