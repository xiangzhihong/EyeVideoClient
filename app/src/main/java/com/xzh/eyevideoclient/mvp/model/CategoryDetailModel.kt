package com.xzh.eyevideoclient.mvp.model


import com.xzh.eyevideoclient.http.RetrofitManager
import com.xzh.eyevideoclient.http.scheduler.SchedulerUtils
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean
import io.reactivex.Observable

/**
 * 分类详情的 Model
 */
class CategoryDetailModel {

    /**
     * 获取分类下的 List 数据
     */
    fun getCategoryDetailList(id: Long): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getCategoryDetailList(id)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }
}