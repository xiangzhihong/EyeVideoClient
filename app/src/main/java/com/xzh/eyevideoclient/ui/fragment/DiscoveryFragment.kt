package com.xzh.eyevideoclient.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment

import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.base.BaseFragment
import com.xzh.eyevideoclient.base.BaseFragmentAdapter
import com.xzh.eyevideoclient.utils.DisplayUtils
import com.xzh.eyevideoclient.view.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_discovery.*

/**
 * 发现
 */
class DiscoveryFragment : BaseFragment() {

    private val tabList = ArrayList<String>()
    private val fragments = ArrayList<Fragment>()

    companion object {
        fun getInstance(): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_discovery
    }

    override fun initView() {
        tabList.add("关注")
        tabList.add("分类")
        fragments.add(FollowFragment.getInstance())
        fragments.add(CategoryFragment.getInstance())

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
        mTabLayout.setupWithViewPager(mViewPager)
        TabLayoutHelper.setUpIndicatorWidth(mTabLayout)
    }

    override fun lazyLoad() {

    }

}