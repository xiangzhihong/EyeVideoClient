package com.xzh.eyevideoclient.mvp.contract

import com.xzh.eyevideoclient.base.IBaseView
import com.xzh.eyevideoclient.base.IPresenter
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean


interface RankContract {

    interface View: IBaseView {
        fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)
        fun showError(errorMsg:String,errorCode:Int)
    }


    interface Presenter: IPresenter<View> {

        fun requestRankList(apiUrl:String)
    }
}