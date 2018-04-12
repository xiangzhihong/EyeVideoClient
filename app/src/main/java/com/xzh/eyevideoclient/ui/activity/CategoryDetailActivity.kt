package com.xzh.eyevideoclient.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.mvp.contract.CategoryDetailContract
import com.xzh.eyevideoclient.mvp.model.bean.CategoryBean
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue.Item
import com.xzh.eyevideoclient.mvp.presenter.CategoryDetailPresenter
import com.xzh.eyevideoclient.ui.adapter.CategoryDetailAdapter
import com.xzh.eyevideoclient.utils.Constants
import com.xzh.eyevideoclient.utils.GlideUtils
import kotlinx.android.synthetic.main.activity_category_detail.*

class CategoryDetailActivity : AppCompatActivity(), CategoryDetailContract.View {


    private val mPresenter by lazy { CategoryDetailPresenter() }
    private val mAdapter by lazy { CategoryDetailAdapter(this, itemList, R.layout.item_category_detail) }
    private var categoryData: CategoryBean? = null
    private var itemList = ArrayList<HomeBean.Issue.Item>()
    private var loadingMore = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detail)
        initView()
        initData()
    }

    init {
        mPresenter.attachView(this)
    }

    private fun initView() {
        //获取序列化数据
        categoryData = intent.getSerializableExtra(Constants.BUNDLE_CATEGORY_DATA) as CategoryBean
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // 加载headerImage
        GlideUtils.display(this,iv_toolbar,categoryData?.headerImage)

        tv_category_desc.text ="#${categoryData?.description}#"

        collapsing_toolbar_layout.title = categoryData?.name
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE)
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
        //实现自动加载
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

    private fun initData() {
        mPresenter.getCategoryDetailList(categoryData?.id!!)
    }

    override fun setCateDetailList(itemList: ArrayList<Item>) {
        loadingMore = false
        mAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String) {
        multipleStatusView.showError()
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}