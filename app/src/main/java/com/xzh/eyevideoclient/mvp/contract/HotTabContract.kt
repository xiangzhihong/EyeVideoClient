package com.xzh.eyevideoclient.mvp.contract

import com.xzh.eyevideoclient.base.IBaseView
import com.xzh.eyevideoclient.base.IPresenter
import com.xzh.eyevideoclient.bean.TabInfoBean


interface HotTabContract {

    interface View: IBaseView {
        fun setTabInfo(tabInfoBean: TabInfoBean)
        fun showError(errorMsg:String,errorCode:Int)
    }


    interface Presenter: IPresenter<View> {
        fun getTabInfo()
    }
}