package com.syiyi.videodesk

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.guoxiaoxing.phoenix.core.PhoenixOption
import kotlinx.android.synthetic.main.activity_main.*
import com.guoxiaoxing.phoenix.core.model.MimeType
import com.guoxiaoxing.phoenix.picker.Phoenix
import android.content.Intent
import com.bumptech.glide.Glide
import com.dingmouren.videowallpaper.VideoWallpaper


class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE: Int = 1000
    private var mVideoWallpaper: VideoWallpaper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Phoenix.config()
                .imageLoader { context, imageView, imagePath, _ ->
                    Glide.with(context)
                            .load(imagePath)
                            .into(imageView)
                }
        mVideoWallpaper = VideoWallpaper()
        open.setOnClickListener({
            Phoenix.with()
                    .theme(PhoenixOption.THEME_DEFAULT)// 主题
                    .fileType(MimeType.ofVideo())//显示的文件类型图片、视频、图片和视频
                    .maxPickNumber(1)// 最大选择数量
                    .minPickNumber(1)// 最小选择数量
                    .spanCount(4)// 每行显示个数
                    .enablePreview(true)// 是否开启预览
                    .enableCamera(false)// 是否开启拍照
                    .enableAnimation(true)// 选择界面图片点击效果
                    .enableCompress(false)// 是否开启压缩
                    .thumbnailHeight(160)// 选择界面图片高度
                    .thumbnailWidth(160)// 选择界面图片宽度
                    .enableClickSound(false)// 是否开启点击声音
                    .start(this, PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE);
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //返回的数据
            val result = Phoenix.result(data)
            if (result.size >= 1) {
                val mediaEntity = result[0]
                val localPath = mediaEntity.localPath
                mVideoWallpaper?.setToWallPaper(this, localPath)
                finish()
            }

        }
    }
}
