package com.xzh.eyevideoclient.mvp.model


import com.xzh.eyevideoclient.http.RetrofitManager
import com.xzh.eyevideoclient.http.scheduler.SchedulerUtils
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue
import io.reactivex.Observable

/**
 * 视频详情Model
 */
class VideoDetailModel {

    /**
     * 获取视频详情数据
     */
    fun requestRelatedData(id:Long):Observable<Issue>{
        return RetrofitManager.service.getRelatedData(id)
                .compose(SchedulerUtils.ioToMain())
    }

}