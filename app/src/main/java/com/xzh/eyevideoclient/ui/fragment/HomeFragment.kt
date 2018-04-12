package com.xzh.eyevideoclient.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.xzh.eyevideoclient.mvp.contract.HomeContract
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.base.BaseFragment
import com.xzh.eyevideoclient.http.exception.ErrorStatus
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue.Item
import com.xzh.eyevideoclient.mvp.presenter.HomePresenter
import com.xzh.eyevideoclient.ui.activity.SearchActivity
import com.xzh.eyevideoclient.ui.adapter.HomeAdapter
import com.xzh.eyevideoclient.utils.UiUtils.showToast
import kotlinx.android.synthetic.main.fragment_home.*


import kotlin.collections.ArrayList

class HomeFragment : BaseFragment(), HomeContract.View {

    private val mPresenter by lazy { HomePresenter() }
    private var num: Int = 1
    private var mAdapter: HomeAdapter? = null
    private var loadingMore = false
    private var isRefresh = false

    companion object {
        fun getInstance(): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun lazyLoad() {
        mPresenter.requestHomeData(num)
    }

    override fun initView() {
        refreshLayout.setOnRefreshListener {
            isRefresh = true
            mPresenter.requestHomeData(num)
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE ) {
                    val childCount = recyclerView.childCount
                    val itemCount = recyclerView.layoutManager.itemCount
                    val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }
        })
        iv_search.setOnClickListener { openSearchActivity() }
    }


    override fun setHomeData(homeBean: HomeBean) {
        mAdapter = HomeAdapter(activity, homeBean.issueList[0].itemList)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun setMoreData(itemList: ArrayList<Item>) {
        loadingMore = false
        mAdapter?.addItemData(itemList)
    }

    //显示错误信息
    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mMultipleStatusView?.showNoNetwork()
        } else {
            mMultipleStatusView?.showError()
        }
    }

    //显示加载视图
    override fun showLoading() {
        if (!isRefresh) {
            isRefresh = false
            mMultipleStatusView?.showLoading()
        }
    }

    //隐藏加载动画
    override fun dismissLoading() {
        refreshLayout.isRefreshing=false
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    private fun openSearchActivity() {
        startActivity(Intent(activity, SearchActivity::class.java))
    }
}