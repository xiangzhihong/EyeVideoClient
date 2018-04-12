package com.xzh.eyevideoclient.mvp.model


import com.xzh.eyevideoclient.bean.TabInfoBean
import com.xzh.eyevideoclient.http.RetrofitManager
import com.xzh.eyevideoclient.http.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * 热门Model
 */
class HotTabModel {

    fun getTabInfo(): Observable<TabInfoBean> {

        return RetrofitManager.service.getRankList()
                .compose(SchedulerUtils.ioToMain())
    }

}
