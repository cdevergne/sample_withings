package com.devergne.withings.ui.list

import com.devergne.withings.data.Image

interface ImageSelectionCallback {
    fun onDataSelected(image: Image)
}