package com.xzh.eyevideoclient.ui.activity

import android.content.res.Configuration

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import android.view.View
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.xzh.eyevideoclient.R
import com.xzh.eyevideoclient.mvp.contract.VideoDetailContract
import com.xzh.eyevideoclient.mvp.contract.VideoListener
import com.xzh.eyevideoclient.mvp.model.bean.HomeBean.Issue.Item
import com.xzh.eyevideoclient.mvp.presenter.VideoDetailPresenter
import com.xzh.eyevideoclient.utils.Constants
import com.xzh.eyevideoclient.utils.GlideUtils
import com.xzh.eyevideoclient.utils.durationFormat
import com.xzh.eyevideoclient.utils.showToast
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.layout_video_detail_info.*


class VideoDetailActivity : AppCompatActivity(), VideoDetailContract.View {

    private val mPresenter by lazy { VideoDetailPresenter() }
    private lateinit var itemData: Item
    private var orientationUtils: OrientationUtils? = null
    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        initView()
    }

    init {
        mPresenter.attachView(this)
    }

    private fun initView() {
        itemData = intent.getSerializableExtra(Constants.BUNDLE_VIDEO_DATA) as Item
        initVideoViewConfig()
        mPresenter.loadVideoInfo(itemData)
    }

    private fun initVideoViewConfig() {
        //设置旋转
        orientationUtils = OrientationUtils(this, mVideoView)
        //是否旋转
        mVideoView.isRotateViewAuto = false
        //是否可以滑动调整
        mVideoView.setIsTouchWiget(true)
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        GlideUtils.display(this,imageView,itemData.data?.cover?.feed)
        mVideoView.thumbImageView = imageView
        //播放监听
        mVideoView.setStandardVideoAllCallBack(object : VideoListener {

            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                orientationUtils?.isEnable = true
                isPlay = true
            }

            override fun onPlayError(url: String, vararg objects: Any) {
                super.onPlayError(url, *objects)
                showToast("播放失败")
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils?.backToProtVideo()
            }
        })
        mVideoView.backButton.setOnClickListener({ onBackPressed() })
        //设置全屏按键功能
        mVideoView.fullscreenButton.setOnClickListener {
            orientationUtils?.resolveByClick()
            mVideoView.startWindowFullscreen(this, true, true)
        }
        //锁屏事件
        mVideoView.setLockClickListener(object : LockClickListener {
            override fun onClick(view: View?, lock: Boolean) {
                orientationUtils?.isEnable = !lock
            }
        })
    }

    override fun setVideo(url: String) {
        mVideoView.setUp(url, false, "")
        mVideoView.startPlayLogic()
    }

    override fun setVideoInfo(itemInfo: Item) {
        tv_title.setText(itemInfo.data?.title)
        tv_tag.setText("#"+itemInfo.data?.category+"/"+durationFormat(itemInfo.data?.duration))
        expandable_text.setText(itemInfo.data?.description)
        tv_action_favorites.setText("100")

    }

    override fun setBackground(url: String) {
        GlideUtils.display(this,mVideoBackground,url)
    }


    override fun setErrorMsg(errorMsg: String) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            mVideoView.fullWindowPlayer
        } else mVideoView
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            mVideoView.onConfigurationChanged(this, newConfig, orientationUtils)
        }
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(this))
            return
        mVideoView.setStandardVideoAllCallBack(null)
        GSYVideoPlayer.releaseAllVideos()
        finish()
        overridePendingTransition(R.anim.anim_out, R.anim.anim_in)
    }

    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
        isPause = false
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
        isPause = true
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.releaseListener()
        mPresenter.detachView()
    }
}