package com.xzh.eyevideoclient.ui.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue.Item
import com.xzh.eyevideoclient.utils.GlideUtils
import com.xzh.eyevideoclient.view.recyclerview.MultipleType
import com.xzh.eyevideoclient.view.recyclerview.ViewHolder
import com.xzh.eyevideoclient.view.recyclerview.BasicAdapter

/**
 * desc: 关注 adapter
 */
class FollowAdapter(context: Context?, dataList: ArrayList<Item>)
    : BasicAdapter<Item>(context!!, dataList, object : MultipleType<Item> {

    override fun getLayoutId(item: Item, position: Int): Int {
        return when {
            item.type == "videoCollectionWithBrief" ->
                R.layout.item_follow
            else ->
                throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }
}) {

    fun addData(dataList: ArrayList<Item>) {
        this.mData.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: Item, position: Int) {
        when {
            data.type == "videoCollectionWithBrief" -> setAuthorInfo(data, holder)
        }
    }

    private fun setAuthorInfo(item: Item, holder: ViewHolder) {
        val headerData = item.data?.header

        holder.setImagePath(R.id.iv_avatar, object : ViewHolder.HolderImageLoader(headerData?.icon!!) {
            override fun loadImage(iv: ImageView, path: String) {
                GlideUtils.display(mContext,holder.getView(R.id.iv_avatar),path)
            }

        })

        holder.setText(R.id.tv_title, headerData.title)
        holder.setText(R.id.tv_desc, headerData.description)

        val recyclerView = holder.getView<RecyclerView>(R.id.fl_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(mContext as Activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = FollowHorizontalAdapter(mContext, item.data!!.itemList, R.layout.item_follow_horizontal)
    }

}