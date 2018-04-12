package com.xzh.eyevideoclient.mvp.presenter

import android.app.Activity
import android.hardware.display.DisplayManager
import com.xzh.eyevideoclient.MyApplication
import com.xzh.eyevideoclient.base.BasePresenter
import com.xzh.eyevideoclient.http.exception.ExceptionHandle
import com.xzh.eyevideoclient.mvp.contract.VideoDetailContract
import com.xzh.eyevideoclient.mvp.model.VideoDetailModel
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue.Item
import com.xzh.eyevideoclient.utils.DisplayUtils
import com.xzh.eyevideoclient.utils.NetworkUtil
import com.xzh.eyevideoclient.utils.dataFormat
import com.xzh.eyevideoclient.utils.showToast


/**
 * 视频详情Presenter
 */
class VideoDetailPresenter : BasePresenter<VideoDetailContract.View>(), VideoDetailContract.Presenter {

//    private val videoDetailModel by lazy {
//        VideoDetailModel()
//    }

    /**
     * 加载视频
     */
    override fun loadVideoInfo(itemInfo: Item) {
        val playInfo = itemInfo.data?.playInfo
        val netType = NetworkUtil.isWifi(MyApplication.context)
        // 检测是否绑定 View
        checkViewAttached()
        if (playInfo!!.size > 1) {
            // 当前网络是 Wifi环境下选择高清的视频
            if (netType) {
                for (i in playInfo) {
                    if (i.type == "high") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        break
                    }
                }
            } else {
                //否则就选标清的视频
                for (i in playInfo) {
                    if (i.type == "normal") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        (mRootView as Activity).showToast("本视频需要消耗${(mRootView as Activity)
                                .dataFormat(i.urlList[0].size)}流量")
                        break
                    }
                }
            }
        } else {
            mRootView?.setVideo(itemInfo.data.playUrl)
        }
        //设置背景
        val backgroundUrl = itemInfo.data.cover.blurred + "/thumbnail/${DisplayUtils.getScreenHeight()!! - DisplayUtils.dip2px(250f)!!}x${DisplayUtils.getScreenWidth()}"
        backgroundUrl.let { mRootView?.setBackground(it) }
        mRootView?.setVideoInfo(itemInfo)
    }


    /**
     * 请求相关的视频数据
     */
//    override fun requestRelatedVideo(id: Long) {
//        mRootView?.showLoading()
//        val disposable = videoDetailModel.requestRelatedData(id)
//                .subscribe({ issue ->
//                    mRootView?.apply {
//                        dismissLoading()
////                        setRecentRelatedVideo(issue.itemList)
//                    }
//                }, { t ->
//                    mRootView?.apply {
//                        dismissLoading()
//                        setErrorMsg(ExceptionHandle.handleException(t))
//                    }
//                })
//        addSubscription(disposable)
//    }


}