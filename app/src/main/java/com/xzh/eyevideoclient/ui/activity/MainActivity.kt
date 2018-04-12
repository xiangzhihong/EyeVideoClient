package com.xzh.eyevideoclient.ui.activity

import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.hazz.kotlinmvp.mvp.model.bean.TabEntity
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.ui.fragment.DiscoveryFragment
import com.xzh.eyevideoclient.ui.fragment.HomeFragment
import com.xzh.eyevideoclient.ui.fragment.HotFragment
import com.xzh.eyevideoclient.utils.UiUtils
import com.xzh.eyevideoclient.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mTitles = arrayOf("首页", "发现", "热门")
    // 未被选中的图标
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_home_normal, R.mipmap.ic_discovery_normal, R.mipmap.ic_hot_normal)
    // 被选中的图标
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_home_selected, R.mipmap.ic_discovery_selected, R.mipmap.ic_hot_selected)
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private var mHomeFragment: HomeFragment? = null
    private var mDiscoveryFragment: DiscoveryFragment? = null
    private var mHotFragment: HotFragment? = null
    private var mIndex = 0
    private var mExitTime: Long = 0
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTab()
        tab_layout.currentTab = mIndex
        switchFragment(mIndex)
    }

    private fun initTab() {
        (0 until mTitles.size)
                .mapTo(mTabEntities) { TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        //为Tab赋值
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }
            override fun onTabReselect(position: Int) {

            }
        })
    }

    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 // 首页
            -> mHomeFragment?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance().let {
                mHomeFragment = it
                transaction.add(R.id.fl_container, it, "home")
            }
            1  //发现
            -> mDiscoveryFragment?.let {
                transaction.show(it)
            } ?: DiscoveryFragment.getInstance().let {
                mDiscoveryFragment = it
                transaction.add(R.id.fl_container, it, "discovery")
            }
            2  //热门
            -> mHotFragment?.let {
                transaction.show(it)
            } ?: HotFragment.getInstance().let {
                mHotFragment = it
                transaction.add(R.id.fl_container, it, "hot")
            }

        }
        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mDiscoveryFragment?.let { transaction.hide(it) }
        mHotFragment?.let { transaction.hide(it) }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 3000) {
                finish()
                toast!!.cancel()
            } else {
                mExitTime = System.currentTimeMillis()
                toast = showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
