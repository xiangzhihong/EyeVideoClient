package com.xzh.eyevideoclient.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.xzh.eyevideoclient.MyApplication
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.mvp.model.bean.CategoryBean
import com.xzh.eyevideoclient.ui.activity.CategoryDetailActivity
import com.xzh.eyevideoclient.utils.Constants
import com.xzh.eyevideoclient.utils.GlideUtils
import com.xzh.eyevideoclient.view.recyclerview.BasicAdapter
import com.xzh.eyevideoclient.view.recyclerview.ViewHolder


/**
 *  分类的 Adapter
 */
class CategoryAdapter(mContext: Context?, categoryList: ArrayList<CategoryBean>, layoutId: Int) :
        BasicAdapter<CategoryBean>(mContext!!, categoryList, layoutId) {

    fun setData(categoryList: ArrayList<CategoryBean>){
        mData.clear()
        mData = categoryList
        notifyDataSetChanged()
    }


    override fun bindData(holder: ViewHolder, data: CategoryBean, position: Int) {
        holder.setText(R.id.tv_category_name, "#${data.name}")

        holder.setImagePath(R.id.iv_category, object : ViewHolder.HolderImageLoader(data.bgPicture) {
            override fun loadImage(iv: ImageView, path: String) {
                GlideUtils.display(mContext,iv,path)
            }
        })
        openCategoryDetail(holder,data)
    }

    fun openCategoryDetail(holder: ViewHolder,data: CategoryBean){
        holder.setOnItemClickListener(View.OnClickListener {
            val intent = Intent(mContext as Activity, CategoryDetailActivity::class.java)
            intent.putExtra(Constants.BUNDLE_CATEGORY_DATA,data)
            mContext.startActivity(intent)
        })
    }
}
