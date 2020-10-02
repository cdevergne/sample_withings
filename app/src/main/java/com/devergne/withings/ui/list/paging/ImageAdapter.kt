package com.devergne.withings.ui.list.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.devergne.withings.data.Image
import com.devergne.withings.databinding.ImageItemBinding
import com.devergne.withings.ui.list.ImageViewModel

class ImageAdapter(private val lifecycleOwner: LifecycleOwner) :
    PagingDataAdapter<ImageViewModel, ImageViewHolder>(
        IMAGE_COMPARATOR
    ) {

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<ImageViewModel>() {
            override fun areItemsTheSame(oldItem: ImageViewModel, newItem: ImageViewModel): Boolean {
                return oldItem.image.url == newItem.image.url
            }

            override fun areContentsTheSame(oldItem: ImageViewModel, newItem: ImageViewModel): Boolean =
                oldItem.image == newItem.image
        }
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val imageItemBinding = ImageItemBinding.inflate(LayoutInflater.from(parent.context))
        imageItemBinding.lifecycleOwner = lifecycleOwner
        return ImageViewHolder(
            imageItemBinding
        )
    }
}