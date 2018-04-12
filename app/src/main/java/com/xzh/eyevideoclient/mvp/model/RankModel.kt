package com.xzh.eyevideoclient.mvp.model


import com.xzh.eyevideoclient.http.RetrofitManager
import com.xzh.eyevideoclient.http.scheduler.SchedulerUtils
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean
import io.reactivex.Observable

/**
 *  排行榜 Model
 */
class RankModel {

    /**
     * 获取排行榜
     */
    fun requestRankList(apiUrl:String): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getIssueData(apiUrl)
                .compose(SchedulerUtils.ioToMain())
    }

}
