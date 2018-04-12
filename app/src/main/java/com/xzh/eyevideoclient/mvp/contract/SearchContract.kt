package com.xzh.eyevideoclient.mvp.contract

import com.xzh.eyevideoclient.base.IBaseView
import com.xzh.eyevideoclient.base.IPresenter
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean


interface SearchContract {

    interface View : IBaseView {
        //设置热门关键词数据
        fun setHotWordData(string: ArrayList<String>)
        //设置搜索关键词返回的结果
        fun setSearchResult(issue: HomeBean.Issue)
        fun closeSoftKeyboard()
        fun setEmptyView()
        fun showError(errorMsg: String,errorCode:Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestHotWordData()
        fun querySearchData(words:String)
        fun loadMoreData()
    }
}