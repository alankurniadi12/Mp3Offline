package com.mp3.offline.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Model (
    var id: Int,
    val title: String,
): Parcelable