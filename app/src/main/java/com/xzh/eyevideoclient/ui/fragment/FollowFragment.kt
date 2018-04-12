package com.xzh.eyevideoclient.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.base.BaseFragment
import com.xzh.eyevideoclient.http.exception.ErrorStatus
import com.xzh.eyevideoclient.mvp.contract.FollowContract
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue.Item
import com.xzh.eyevideoclient.mvp.presenter.FollowPresenter
import com.xzh.eyevideoclient.ui.adapter.FollowAdapter
import com.xzh.eyevideoclient.utils.UiUtils.showToast
import com.xzh.eyevideoclient.view.MultipleStatusView
import kotlinx.android.synthetic.main.fragment_follow.*

/**
 * 关注
 */
class FollowFragment : BaseFragment(), FollowContract.View{


    private var itemList = ArrayList<Item>()
    private var loadingMore = false
    private val mPresenter by lazy { FollowPresenter() }
    private val mFollowAdapter by lazy { FollowAdapter(activity,itemList) }

    init {
        mPresenter.attachView(this)
    }

    companion object {
        fun getInstance(): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_follow
    }

    override fun initView() {
        mMultipleStatusView = multipleStatusView as MultipleStatusView?
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mFollowAdapter
        //mRecyclerView监听
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager.itemCount
                val lastVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    mPresenter.loadMoreData()
                }
            }
        })
    }

    override fun lazyLoad() {
        mPresenter.requestFollowList()
    }

    override fun setFollowInfo(issue: Issue) {
        loadingMore = false
        itemList = issue.itemList
        mFollowAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mMultipleStatusView?.showNoNetwork()
        } else {
            mMultipleStatusView?.showError()
        }
    }

    override fun showLoading() {
        mMultipleStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mMultipleStatusView?.showContent()
    }

}