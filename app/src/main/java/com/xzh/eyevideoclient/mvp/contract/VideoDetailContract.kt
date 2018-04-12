package com.xzh.eyevideoclient.mvp.contract

import com.xzh.eyevideoclient.base.IBaseView
import com.xzh.eyevideoclient.base.IPresenter
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue.Item


interface VideoDetailContract {

    interface View : IBaseView {
        //设置视频播放源
        fun setVideo(url: String)
        //设置视频信息
        fun setVideoInfo(itemInfo: Item)
        //设置背景
        fun setBackground(url: String)
        //设置最新相关视频
//        fun setRecentRelatedVideo(itemList: ArrayList<Item>)
        //设置错误信息
        fun setErrorMsg(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {
        //加载视频信息
        fun loadVideoInfo(itemInfo: Item)
        //请求相关的视频数据
//        fun requestRelatedVideo(id: Long)
    }

}