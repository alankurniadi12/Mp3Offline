package com.example.lirikmp3offline.ui

import androidx.lifecycle.ViewModel
import com.example.lirikmp3offline.model.Model
import com.example.lirikmp3offline.utils.Data

class MainViewModel: ViewModel() {
    fun getDataMp3(): List<Model> = Data.getDataMp3()
}