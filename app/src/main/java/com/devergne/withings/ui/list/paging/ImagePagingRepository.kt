package com.devergne.withings.ui.list.paging


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.devergne.withings.data.repository.ImageRepository
import com.devergne.withings.ui.list.ImageViewModel
import kotlinx.coroutines.flow.Flow

class ImagePagingRepository(
    private val imageRepository: ImageRepository
) {
    fun getImagesStream(filter: String?): Flow<PagingData<ImageViewModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImageDataSource(imageRepository, filter) }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 25
    }
}