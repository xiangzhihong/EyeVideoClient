package com.xzh.eyevideoclient.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import android.widget.ImageView
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue.Item
import com.xzh.eyevideoclient.ui.activity.VideoDetailActivity
import com.xzh.eyevideoclient.utils.Constants
import com.xzh.eyevideoclient.utils.GlideUtils
import com.xzh.eyevideoclient.utils.durationFormat
import com.xzh.eyevideoclient.view.recyclerview.BasicAdapter
import com.xzh.eyevideoclient.view.recyclerview.ViewHolder


/**
 * desc: 关注水平的 RecyclerViewAdapter
 */
class FollowHorizontalAdapter(mContext: Context, categoryList: ArrayList<Item>, layoutId: Int) :
        BasicAdapter<Item>(mContext, categoryList, layoutId) {


    override fun bindData(holder: ViewHolder, data: Item, position: Int) {
        val horizontalItemData = data.data
        holder.setImagePath(R.id.iv_cover_feed, object : ViewHolder.HolderImageLoader(data.data?.cover?.feed!!) {
            override fun loadImage(iv: ImageView, path: String) {
                GlideUtils.display(mContext,holder.getView(R.id.iv_cover_feed),path)
            }

        })

        holder.setText(R.id.tv_title, horizontalItemData?.title ?: "")
        // 格式化时间
        val timeFormat = durationFormat(horizontalItemData?.duration)
        //标签
        with(holder) {
            if (horizontalItemData?.tags != null && horizontalItemData.tags.size > 0) {
                setText(R.id.tv_tag, "#${horizontalItemData.tags[0].name} / $timeFormat")
            }else{
                setText(R.id.tv_tag,"#$timeFormat")
            }

            setOnItemClickListener(listener = View.OnClickListener {
                goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), data)
            })
        }
    }

    /**
     * 跳转到视频详情页面播放
     */
    private fun goToVideoPlayer(activity: Activity, view: View, item: Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, item)
        activity.startActivity(intent)
    }

}
