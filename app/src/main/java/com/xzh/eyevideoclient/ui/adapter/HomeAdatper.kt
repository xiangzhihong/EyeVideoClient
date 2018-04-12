package com.xzh.eyevideoclient.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent

import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.util.Pair
import android.view.View
import android.view.ViewGroup
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue.Item
import com.xzh.eyevideoclient.ui.activity.VideoDetailActivity
import com.xzh.eyevideoclient.utils.Constants
import com.xzh.eyevideoclient.utils.GlideUtils

import com.xzh.eyevideoclient.utils.durationFormat
import com.xzh.eyevideoclient.view.recyclerview.BasicAdapter
import com.xzh.eyevideoclient.view.recyclerview.ViewHolder


class HomeAdapter(context: Context?, data: ArrayList<HomeBean.Issue.Item>)
    : BasicAdapter<Item>(context!!, data, -1) {


    fun addItemData(itemList: ArrayList<Item>) {
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: Item, position: Int) {
        bindItemData(holder, mData[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflaterView(R.layout.item_home, parent))
    }

    private fun inflaterView(mLayoutId: Int, parent: ViewGroup): View {
        val view = mInflater?.inflate(mLayoutId, parent, false)
        return view!!
    }

    private fun bindItemData(holder: ViewHolder, item: Item) {
        val itemData = item.data
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"

        // 加载封页图
        if (cover!=null){
            GlideUtils.display(mContext,holder.getView(R.id.iv_cover), cover)
        }
        if (avatar!=null){
            GlideUtils.display(mContext, holder.getView(R.id.iv_avatar), avatar)
        }
        holder.setText(R.id.tv_title, itemData?.title ?: "")
        //遍历标签（取其中4个）
        itemData?.tags?.take(4)?.forEach {
            tagText += (it.name + "/")
        }
        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)
        tagText += timeFormat
        holder.setText(R.id.tv_tag, tagText!!)

        holder.setText(R.id.tv_category, "#" + itemData?.category)
        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover), item)
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
