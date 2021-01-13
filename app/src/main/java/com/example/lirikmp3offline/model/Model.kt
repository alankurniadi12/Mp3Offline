package com.example.lirikmp3offline.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Model (
    var id: Int,
    var title: String,
    var artist: String,
    var duration: String,
    var photo: Int,
    var mp3: Int
): Parcelable