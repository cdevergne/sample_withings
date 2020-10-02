package com.devergne.withings.ui.list

import com.devergne.withings.data.Image

interface ImageSelectionValidationCallback {
    fun onSelectionValidated(imageList: List<Image>)
}