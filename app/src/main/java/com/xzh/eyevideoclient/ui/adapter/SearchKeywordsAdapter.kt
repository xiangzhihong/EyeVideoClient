package com.xzh.eyevideoclient.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayoutManager
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.view.recyclerview.BasicAdapter
import com.xzh.eyevideoclient.view.recyclerview.ViewHolder


/**
 * 搜索Tag标签布局Adapter
 */
class SearchKeywordsAdapter(mContext: Context, mList: ArrayList<String>, layoutId: Int) :
        BasicAdapter<String>(mContext, mList, layoutId){

    var mOnTagItemClick: ((tag:String) -> Unit)? = null

    fun setOnTagItemClickListener(onTagItemClickListener:(tag:String) -> Unit) {
        this.mOnTagItemClick = onTagItemClickListener
    }

    override fun bindData(holder: ViewHolder, data: String, position: Int) {
        holder.setText(R.id.tv_keyword,data)
        val params = holder.getView<TextView>(R.id.tv_keyword).layoutParams
        if(params is FlexboxLayoutManager.LayoutParams){
            params.flexGrow = 1.0f
        }

        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                mOnTagItemClick?.invoke(data)
            }
        }

        )

    }


}