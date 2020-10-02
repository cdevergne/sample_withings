package com.devergne.withings.ui.list.paging

import androidx.recyclerview.widget.RecyclerView
import com.devergne.withings.databinding.ImageItemBinding
import com.devergne.withings.ui.list.ImageViewModel


class ImageViewHolder(private val imageItemBinding: ImageItemBinding) :
    RecyclerView.ViewHolder(imageItemBinding.root) {

    fun bind(imageViewModel: ImageViewModel) {
        imageItemBinding.viewModel = imageViewModel
    }
}
