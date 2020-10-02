package com.devergne.withings.ui.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devergne.withings.ui.list.paging.ImageAdapter

@BindingAdapter("imageAdapter")
fun RecyclerView.setupUserAdapter(imageAdapter: ImageAdapter) {
    this.adapter = imageAdapter
}