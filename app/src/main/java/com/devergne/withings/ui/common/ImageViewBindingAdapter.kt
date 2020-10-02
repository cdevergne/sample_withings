package com.devergne.withings.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.devergne.withings.R

@BindingAdapter("userImageUrl")
fun ImageView.bindUserImageUrl(userImageUrl : String?) {
    Glide.with(this)
        .load(userImageUrl)
        .centerCrop()
        .placeholder(R.drawable.ic_launcher_foreground)
        .error(R.drawable.ic_launcher_foreground)
        .fallback(R.drawable.ic_launcher_foreground)
        .into(this)
}