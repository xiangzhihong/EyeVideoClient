package com.xzh.eyevideoclient.mvp.model


import com.xzh.eyevideoclient.http.RetrofitManager
import com.xzh.eyevideoclient.http.scheduler.SchedulerUtils
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean
import io.reactivex.Observable


class HomeModel{

    /**
     * 获取首页数据
     */
    fun requestHomeData(num:Int):Observable<HomeBean>{
        return RetrofitManager.service.getFirstHomeData(num)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url:String):Observable<HomeBean>{
        return RetrofitManager.service.getMoreHomeData(url)
                .compose(SchedulerUtils.ioToMain())
    }
}