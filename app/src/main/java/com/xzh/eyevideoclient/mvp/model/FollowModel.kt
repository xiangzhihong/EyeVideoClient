package com.xzh.eyevideoclient.mvp.model


import com.xzh.eyevideoclient.http.RetrofitManager
import com.xzh.eyevideoclient.http.scheduler.SchedulerUtils
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean
import io.reactivex.Observable


class FollowModel {

    /**
     * 获取关注信息
     */
    fun requestFollowList(): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getFollowInfo()
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多关注信息
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }


}
