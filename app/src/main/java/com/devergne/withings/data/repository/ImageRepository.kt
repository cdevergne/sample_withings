package com.devergne.withings.data.repository

import com.devergne.withings.data.Image
import io.reactivex.Single

interface ImageRepository {
    fun getAllImage() : Single<List<Image>>

    /**
     * @param filter providing a null string will request all image
     */
    fun getImageWithFilter(filter : String?) : Single<List<Image>>
}