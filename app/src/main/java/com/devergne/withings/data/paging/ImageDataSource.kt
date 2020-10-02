package com.devergne.withings.data.paging

import androidx.paging.rxjava2.RxPagingSource
import com.devergne.withings.data.Image
import com.devergne.withings.data.repository.ImageRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class ImageDataSource(
    private val imageRepository: ImageRepository
) : RxPagingSource<Int, Image>()
{
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Image>> {
        val pageNumber = params.key ?: 0
        return imageRepository.getImageWithFilter(null, page = pageNumber, itemsPerPage = params.loadSize)
            .subscribeOn(Schedulers.io())
            .map <LoadResult<Int, Image>> { result ->
                // Since 0 is the lowest page number, return null to signify no more pages should
                // be loaded before it.
                val prevKey = if (pageNumber > 0) pageNumber - 1 else null
                // This API defines that it's out of data when a page returns empty. When out of
                // data, we return `null` to signify no more pages should be loaded
                val nextKey = if (result.isNotEmpty()) pageNumber + 1 else null

                LoadResult.Page(
                    data = result,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            .onErrorReturn { e ->
                when (e) {
                    // Retrofit calls that return the body type throw either IOException for
                    // network failures, or HttpException for any non-2xx HTTP status codes.
                    // This code reports all errors to the UI, but you can inspect/wrap the
                    // exceptions to provide more context.
                    is IOException -> LoadResult.Error(e)
                    is HttpException -> LoadResult.Error(e)
                    else -> throw e
                }
            }
    }
}