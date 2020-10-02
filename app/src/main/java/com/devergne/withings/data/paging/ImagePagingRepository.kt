package com.devergne.withings.data.paging


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.devergne.withings.data.Image
import com.devergne.withings.data.repository.ImageRepository
import kotlinx.coroutines.flow.Flow

class ImagePagingRepository(
    private val imageRepository: ImageRepository
)
{
    fun getImagesStream() : Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImageDataSource(imageRepository) }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 50
    }
}