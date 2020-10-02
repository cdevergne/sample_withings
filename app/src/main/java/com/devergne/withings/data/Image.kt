package com.devergne.withings.data

import java.io.Serializable

data class Image(
    val thumbnailUrl : String,
    val url : String
) : Serializable