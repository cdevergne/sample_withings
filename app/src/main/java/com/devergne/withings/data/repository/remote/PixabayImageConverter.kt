package com.devergne.withings.data.repository.remote

import com.devergne.withings.data.Image

class PixabayImageConverter {
    fun convert(pixabayImage: PixabayImage) : Image = Image(pixabayImage.previewURL, pixabayImage.largeImageURL)
}