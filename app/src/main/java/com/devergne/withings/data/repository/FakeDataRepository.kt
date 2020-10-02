package com.devergne.withings.data.repository

import com.devergne.withings.data.Image
import io.reactivex.Single

class FakeDataRepository : ImageRepository {

    private val images = listOf(
        Image("thumbnailUrl1", "url1"),
        Image("thumbnailUrl2", "url2"),
        Image("thumbnailUrl3", "url3"),
        Image("thumbnailUrl4", "url4"),
        Image("thumbnailUrl5", "url5"),
        Image("thumbnailUrl6", "url6"),
        Image("thumbnailUrl7", "url7"),
        Image("thumbnailUrl8", "url8"),
        Image("thumbnailUrl9", "url9"),
        Image("thumbnailUrl10", "url10")
    )

    override fun getAllImage(): Single<List<Image>> = Single.just(images)

    override fun getImageWithFilter(filter: String?) = Single.just(images)

}