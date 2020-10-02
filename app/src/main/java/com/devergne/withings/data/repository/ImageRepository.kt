package com.devergne.withings.data.repository

import com.devergne.withings.data.Image
import io.reactivex.Single

interface ImageRepository {
    fun getAllImage() : Single<List<Image>>
}