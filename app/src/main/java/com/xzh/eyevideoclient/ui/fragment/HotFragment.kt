package com.xzh.eyevideoclient.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.base.BaseFragment
import com.xzh.eyevideoclient.base.BaseFragmentAdapter
import com.xzh.eyevideoclient.bean.TabInfoBean
import com.xzh.eyevideoclient.http.exception.ErrorStatus
import com.xzh.eyevideoclient.mvp.contract.HotTabContract
import com.xzh.eyevideoclient.mvp.presenter.HotTabPresenter
import com.xzh.eyevideoclient.utils.UiUtils.showToast
import kotlinx.android.synthetic.main.fragment_hot.*


/**
 * 热门
 */
class HotFragment : BaseFragment(), HotTabContract.View {

    private val mPresenter by lazy { HotTabPresenter() }
    private val mTabTitleList = ArrayList<String>()
    private val mFragmentList = ArrayList<Fragment>()

    companion object {
        fun getInstance(): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot
    }

    override fun initView() {
        mMultipleStatusView = multipleStatusView
    }

    override fun lazyLoad() {
        mPresenter.getTabInfo()
    }

    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        mMultipleStatusView?.showContent()
        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList) { it.name }
        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) {
            RankFragment.getInstance(it.apiUrl)
        }
        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, mFragmentList, mTabTitleList)
        mTabLayout.setupWithViewPager(mViewPager)
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
        mMultipleStatusView?.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}