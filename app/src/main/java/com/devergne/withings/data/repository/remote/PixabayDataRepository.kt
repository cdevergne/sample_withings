package com.devergne.withings.data.repository.remote

import com.devergne.withings.data.Image
import com.devergne.withings.data.repository.ImageRepository
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class PixabayDataRepository(private val pixabayImageConverter: PixabayImageConverter) : ImageRepository {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://pixabay.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(OkHttpClient())
        .build()

    private interface RemoteDataApi {
        @GET("api/?key=5511001-7691b591d9508e60ec89b63c4")
        fun fetchImageList(): Single<PixabayResponse>

        @GET("api/?key=5511001-7691b591d9508e60ec89b63c4")
        fun fetchImageListWithFilter(
            @Query("q") filter : String?
        ): Single<PixabayResponse>

        @GET("api/?key=5511001-7691b591d9508e60ec89b63c4")
        fun fetchImageListWithFilter(
            @Query("q") filter : String?,
            @Query("page") page : Int,
            @Query("per_page") itemsPerPage : Int
        ): Single<PixabayResponse>
    }

    private val remoteDataApi = retrofit.create(RemoteDataApi::class.java)

    override fun getAllImage(): Single<List<Image>> =
        remoteDataApi.fetchImageList()
            .map { it.hits }
            .map { list -> list.map { pixabayImage -> pixabayImageConverter.convert(pixabayImage) } }

    override fun getImageWithFilter(filter: String?): Single<List<Image>> =
        remoteDataApi.fetchImageListWithFilter(filter)
            .map { it.hits }
            .map { list -> list.map { pixabayImage -> pixabayImageConverter.convert(pixabayImage) } }

    override fun getImageWithFilter(
        filter: String?,
        page: Int,
        itemsPerPage: Int
    ): Single<List<Image>> {
        //pixabay pages starts at 1
        return remoteDataApi.fetchImageListWithFilter(filter, page + 1, itemsPerPage)
            .map { it.hits }
            .map { list -> list.map { pixabayImage -> pixabayImageConverter.convert(pixabayImage) } }
    }
}