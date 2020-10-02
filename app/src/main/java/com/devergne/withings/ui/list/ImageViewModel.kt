package com.devergne.withings.ui.list

import androidx.lifecycle.ViewModel
import com.devergne.withings.data.Image

class ImageViewModel(val image : Image, private val dataSelectionCallback: DataSelectionCallback): ViewModel() {
    fun select() {
        dataSelectionCallback.onDataSelected(image)
    }
}