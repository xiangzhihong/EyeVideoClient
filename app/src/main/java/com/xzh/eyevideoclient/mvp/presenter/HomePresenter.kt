package com.xzh.eyevideoclient.mvp.presenter


import com.xzh.eyevideoclient.http.exception.ExceptionHandle
import com.xzh.eyevideoclient.base.BasePresenter
import com.xzh.eyevideoclient.mvp.contract.HomeContract
import com.xzh.eyevideoclient.mvp.model.HomeModel
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean


class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private var homeBean: HomeBean? = null
    private var nextPageUrl: String? = null
    private val homeModel: HomeModel by lazy {
        HomeModel()
    }

    /**
     * 获取首页数据
     */
    override fun requestHomeData(num: Int) {
        checkViewAttached()
        val disposable = homeModel.requestHomeData(num)
                .flatMap({ homeBean ->
                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val bannerItemList = homeBean.issueList[0].itemList
                    bannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        //移除 item
                        bannerItemList.remove(item)
                    }
                    this.homeBean = homeBean
                    homeModel.loadMoreData(homeBean.nextPageUrl)
                })
                .subscribe({ homeBean ->
                    mRootView?.apply {
                        dismissLoading()
                        nextPageUrl = homeBean.nextPageUrl
                        //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                        val newBannerItemList = homeBean.issueList[0].itemList
                        newBannerItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            newBannerItemList.remove(item)
                        }
                        setHomeData(this@HomePresenter.homeBean!!)
                    }

                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    /**
     * 加载更多
     */
    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            homeModel.loadMoreData(it)
                    .subscribe({ homeBean ->
                        mRootView?.apply {
                            //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                            val newItemList = homeBean.issueList[0].itemList
                            newItemList.filter { item ->
                                item.type == "banner2" || item.type == "horizontalScrollCard"
                            }.forEach { item ->
                                newItemList.remove(item)
                            }
                            nextPageUrl = homeBean.nextPageUrl
                            setMoreData(newItemList)
                        }
                    }, { t ->
                        mRootView?.apply {
                            showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                        }
                    })
        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }
}




