package com.devergne.withings.ui.list

import com.devergne.withings.data.Image

interface DataSelectionCallback {
    fun onDataSelected(image: Image)
}