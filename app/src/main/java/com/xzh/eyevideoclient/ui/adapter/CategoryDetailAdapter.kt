package com.xzh.eyevideoclient.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue.Item
import com.xzh.eyevideoclient.ui.activity.VideoDetailActivity
import com.xzh.eyevideoclient.utils.Constants
import com.xzh.eyevideoclient.utils.GlideUtils
import com.xzh.eyevideoclient.utils.durationFormat
import com.xzh.eyevideoclient.view.recyclerview.BasicAdapter
import com.xzh.eyevideoclient.view.recyclerview.ViewHolder


/**
 * 分类详情Adapter
 */
class CategoryDetailAdapter(context: Context, dataList: ArrayList<Item>, layoutId: Int)
    : BasicAdapter<Item>(context, dataList, layoutId) {

    fun addData(dataList: ArrayList<Item>) {
        this.mData.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: Item, position: Int) {
        setVideoItem(holder, data)
    }

    private fun setVideoItem(holder: ViewHolder, item:Item) {
        val itemData = item.data
        val cover = itemData?.cover?.feed?:""
        // 加载封页图
        GlideUtils.display(mContext,holder.getView(R.id.iv_image),cover)
        holder.setText(R.id.tv_title, itemData?.title?:"")
        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        holder.setText(R.id.tv_tag, "#${itemData?.category}/$timeFormat")

        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_image), item)
        })
    }

    /**
     * 跳转到视频详情页面播放
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        activity.startActivity(intent)
    }

}