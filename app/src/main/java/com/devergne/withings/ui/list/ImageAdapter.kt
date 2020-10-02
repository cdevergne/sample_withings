package com.devergne.withings.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.devergne.withings.data.Image
import com.devergne.withings.databinding.ImageItemBinding

class ImageAdapter :
    PagingDataAdapter<Image, ImageViewHolder>(Image_COMPARATOR) {

    companion object {
        private val Image_COMPARATOR = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(ImageViewModel(it))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val imageItemBinding = ImageItemBinding.inflate(LayoutInflater.from(parent.context))
        return ImageViewHolder(imageItemBinding)
    }
}