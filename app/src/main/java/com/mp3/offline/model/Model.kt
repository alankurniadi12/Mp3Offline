package com.mp3.offline.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Model (
    var id: Int,
    var title: String,
    var artist: String,
    var photo: Int,
    var mp3: Int
): Parcelable