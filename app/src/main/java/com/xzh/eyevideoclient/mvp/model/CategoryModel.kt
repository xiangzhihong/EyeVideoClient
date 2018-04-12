package com.xzh.eyevideoclient.mvp.model

import com.xzh.eyevideoclient.http.RetrofitManager
import com.xzh.eyevideoclient.http.scheduler.SchedulerUtils
import com.xzh.eyevideoclient.mvp.model.bean.CategoryBean
import io.reactivex.Observable


class CategoryModel {
    /**
     * 获取分类信息
     */
    fun getCategoryData(): Observable<ArrayList<CategoryBean>> {
        return RetrofitManager.service.getCategory()
                .compose(SchedulerUtils.ioToMain())
    }
}