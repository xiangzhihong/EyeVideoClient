package com.xzh.eyevideoclient.base


interface IPresenter<in V: IBaseView> {

    fun attachView(mRootView: V)
    fun detachView()

}
