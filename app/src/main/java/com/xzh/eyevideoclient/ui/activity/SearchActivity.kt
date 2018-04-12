package com.xzh.eyevideoclient.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.android.flexbox.*
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.http.exception.ErrorStatus
import com.xzh.eyevideoclient.mvp.contract.SearchContract
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue.Item
import com.xzh.eyevideoclient.mvp.presenter.SearchPresenter
import com.xzh.eyevideoclient.ui.adapter.SearchAdapter
import com.xzh.eyevideoclient.ui.adapter.SearchKeywordsAdapter
import com.xzh.eyevideoclient.utils.KeyBoardUtils
import com.xzh.eyevideoclient.utils.UiUtils
import com.xzh.eyevideoclient.utils.showToast
import kotlinx.android.synthetic.main.activity_search.*

/**
 * 搜索
 */
class SearchActivity : AppCompatActivity(), SearchContract.View {


    private val mPresenter by lazy { SearchPresenter() }
    private val mResultAdapter by lazy { SearchAdapter(this, itemList, R.layout.item_search) }
    private var mSearchKeywordsAdapter: SearchKeywordsAdapter? = null
    private var itemList = ArrayList<Item>()
    private var keyWords: String? = null
    private var loadingMore = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
        initData()
    }

    init {
        mPresenter.attachView(this)
    }

    private fun initView() {
        mRecyclerView_result.layoutManager = LinearLayoutManager(this)
        mRecyclerView_result.adapter = mResultAdapter
        mRecyclerView_result.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView_result.layoutManager.itemCount
                val lastVisibleItem = (mRecyclerView_result.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    mPresenter.loadMoreData()
                }
            }
        })

        //取消
        tv_cancel.setOnClickListener { onBackPressed() }
        et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    KeyBoardUtils.closeKeyboard(applicationContext,et_search)
                    keyWords = et_search.text.toString().trim()
                    if (keyWords.isNullOrEmpty()) {
                        showToast("请输入你感兴趣的关键词")
                    } else {
                        mPresenter.querySearchData(keyWords!!)
                    }
                }
                return false
            }

        })
    }


    private fun initData() {
        mPresenter.requestHotWordData()
    }

    override fun setHotWordData(string: ArrayList<String>) {
        showHotWordView()
        mSearchKeywordsAdapter = SearchKeywordsAdapter(this, string, R.layout.item_search_text)

        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW
        flexBoxLayoutManager.alignItems = AlignItems.CENTER
        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START

        mRecyclerView_hot.layoutManager = flexBoxLayoutManager
        mRecyclerView_hot.adapter = mSearchKeywordsAdapter
        //设置 Tag 的点击事件
        mSearchKeywordsAdapter?.setOnTagItemClickListener {
            closeSoftKeyboard()
            keyWords = it
            mPresenter.querySearchData(it)
        }
    }

    private fun showHotWordView() {
        layout_hot.visibility = View.VISIBLE
        layout_result.visibility = View.GONE
    }

    private fun showSearchResultView() {
        layout_hot.visibility = View.GONE
        layout_result.visibility = View.VISIBLE
    }

    override fun setSearchResult(issue: Issue) {
        loadingMore = false
        showSearchResultView()
        itemList = issue.itemList
        mResultAdapter.addData(issue.itemList)
        mResultAdapter.notifyDataSetChanged()
    }

    override fun closeSoftKeyboard() {
        KeyBoardUtils.closeKeyboard(applicationContext,et_search)
    }

    override fun setEmptyView() {
        showToast("抱歉，没有找到匹配的内容...")
        multipleStatusView.showEmpty()
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