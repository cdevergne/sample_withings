package com.devergne.withings.ui.list

import androidx.recyclerview.widget.RecyclerView
import com.devergne.withings.databinding.ImageItemBinding

class ImageViewHolder(private val imageItemBinding: ImageItemBinding) :
    RecyclerView.ViewHolder(imageItemBinding.root) {

    fun bind(imageViewModel: ImageViewModel) {
        imageItemBinding.viewModel = imageViewModel
    }
}