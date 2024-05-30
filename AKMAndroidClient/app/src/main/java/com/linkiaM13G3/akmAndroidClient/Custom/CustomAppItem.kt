package com.linkiaM13G3.akmAndroidClient.Custom

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.linkiaM13G3.akmAndroidClient.R

class CustomAppItem(context: Context) : LinearLayout(context) {
    init { inflate(context, R.layout.item_app, this) }

    fun setAppName(name: String) {
        val textView: TextView = findViewById(R.id.tvAppName)
        textView.text = name
    }

    fun setAppIcon(url: String) {
        val appIconImageView: ImageView = findViewById(R.id.ivAppIcon)
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.logo_default)
                .error(R.drawable.logo_default)
                .into(appIconImageView)
    }
}