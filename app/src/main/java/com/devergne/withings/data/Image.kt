package com.devergne.withings.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val thumbnailUrl : String,
    val url : String
) : Parcelable