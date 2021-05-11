package com.example.repoviewer.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

object GlideUtils {

    fun loadImage(context: Context, imgUrl: String?, imgView: ImageView, errorImg: Drawable?) {
        if (imgUrl != null && imgUrl.isNotEmpty()) {

            val options = RequestOptions().placeholder(errorImg).error(errorImg)
                .priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(context).load(imgUrl).thumbnail(0.1f).apply(options)
                .addListener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?, model: Any,
                        target: Target<Drawable?>, isFirstResource: Boolean
                    ): Boolean {
                        return if (e != null && e.message!!.contains("FileNotFoundException")) {
                            Log.e("GlideUtils", "Token refresh error")
                            true
                        } else false
                    }

                    override fun onResourceReady(
                        resource: Drawable?, model: Any, target: Target<Drawable?>,
                        dataSource: DataSource, isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(imgView)
        } else imgView.setImageDrawable(errorImg)
    }
}