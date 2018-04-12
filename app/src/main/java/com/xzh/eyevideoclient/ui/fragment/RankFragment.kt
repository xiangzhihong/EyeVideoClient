package com.xzh.eyevideoclient.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.base.BaseFragment
import com.xzh.eyevideoclient.http.exception.ErrorStatus
import com.xzh.eyevideoclient.mvp.contract.RankContract
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean
import com.xzh.eyevideoclient.mvp.presenter.RankPresenter
import com.xzh.eyevideoclient.ui.adapter.RankAdapter
import com.xzh.eyevideoclient.utils.UiUtils.showToast
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 * 排行
 */
class RankFragment : BaseFragment(),RankContract.View {


    private val mPresenter by lazy { RankPresenter() }
    private val mAdapter by lazy { RankAdapter(activity, itemList, R.layout.item_rank) }
    private var itemList = ArrayList<HomeBean.Issue.Item>()
    private var apiUrl: String? = null

    companion object {
        fun getInstance(apiUrl: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.apiUrl=apiUrl
            return fragment
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_rank
    }

    override fun initView() {
        mPresenter.attachView(this)
        mMultipleStatusView =multipleStatusView
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter
    }

    override fun lazyLoad() {
        if (!apiUrl.isNullOrEmpty()) {
            mPresenter.requestRankList(apiUrl!!)
        }
    }

    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        mMultipleStatusView?.showContent()
        mAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun showLoading() {
        mMultipleStatusView?.showLoading()
    }

    override fun dismissLoading() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}