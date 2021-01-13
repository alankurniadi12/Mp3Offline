package com.mp3.offline.ui

import androidx.lifecycle.ViewModel
import com.mp3.offline.model.Model
import com.mp3.offline.utils.Data

class MainViewModel: ViewModel() {
    fun getDataMp3(): List<Model> = Data.getDataMp3()
}