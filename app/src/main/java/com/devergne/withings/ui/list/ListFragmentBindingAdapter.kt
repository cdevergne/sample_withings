package com.devergne.withings.ui.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("imageAdapter")
fun RecyclerView.setupUserAdapter(imageAdapter: ImageAdapter) {
    this.adapter = imageAdapter
}