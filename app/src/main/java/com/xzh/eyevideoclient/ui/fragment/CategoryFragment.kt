package com.xzh.eyevideoclient.ui.fragment

import android.graphics.Rect
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.base.BaseFragment
import com.xzh.eyevideoclient.http.exception.ErrorStatus
import com.xzh.eyevideoclient.mvp.contract.CategoryContract
import com.xzh.eyevideoclient.mvp.model.bean.CategoryBean
import com.xzh.eyevideoclient.mvp.presenter.CategoryPresenter
import com.xzh.eyevideoclient.ui.adapter.CategoryAdapter
import com.xzh.eyevideoclient.utils.DisplayUtils
import com.xzh.eyevideoclient.utils.UiUtils.showToast
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * 分类
 */
class CategoryFragment : BaseFragment(), CategoryContract.View  {

    private val mPresenter by lazy { CategoryPresenter() }
    private val mAdapter by lazy { CategoryAdapter(activity, mCategoryList, R.layout.item_category) }
    private var mCategoryList = ArrayList<CategoryBean>()

    companion object {
        fun getInstance(): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_category
    }

    override fun initView() {
        mPresenter.attachView(this)
        mMultipleStatusView = multipleStatusView
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = GridLayoutManager(activity,2)
        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                val position = parent.getChildPosition(view)
                val offset = DisplayUtils.dip2px(2f)!!
                outRect.set(if (position % 2 == 0) 0 else offset, offset,
                        if (position % 2 == 0) offset else 0, offset)
            }
        })
    }

    override fun lazyLoad() {
        mPresenter.getCategoryData()
    }

    override fun showCategory(categoryList: ArrayList<CategoryBean>) {
        mCategoryList = categoryList
        mAdapter.setData(mCategoryList)
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

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}