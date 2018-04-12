package com.xzh.eyevideoclient.mvp.contract

import com.xzh.eyevideoclient.base.IBaseView
import com.xzh.eyevideoclient.base.IPresenter
import com.xzh.eyevideoclient.mvp.model.bean.CategoryBean


interface CategoryContract {

    interface View : IBaseView {

        fun showCategory(categoryList: ArrayList<CategoryBean>)
        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Presenter: IPresenter<View> {
        fun getCategoryData()
    }
}