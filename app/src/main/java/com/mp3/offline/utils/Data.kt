package com.mp3.offline.utils

import com.mp3.offline.R
import com.mp3.offline.model.Model

object Data {

    fun getDataMp3(): List<Model> {
        val dataMp3 = ArrayList<Model>()

        dataMp3.add(
            Model(1, "Ya Habibal Qolbi", "Nissa", R.drawable.nissa1, R.raw.sabyan_ibadallah)
        )

        dataMp3.add(
            Model(2, "Rahman ya Rahman", "Nissa", R.drawable.nissa2, R.raw.sabyanalma_al_musthofa)
        )

        dataMp3.add(
            Model(3, "Ya Asyiqol Musthofa", "Nissa", R.drawable.nissa3, R.raw.sabyan_ibadallah)
        )

        dataMp3.add(
            Model(4, "Ya Maulana", "Nissa", R.drawable.nissa4, R.raw.sabyanalma_al_musthofa)
        )

        dataMp3.add(
            Model(5, "Deen Assalam", "Nissa", R.drawable.nissa5, R.raw.sabyan_ibadallah)
        )

        dataMp3.add(
            Model(6, "Ya Jamalu", "Nissa", R.drawable.nissa6, R.raw.sabyanalma_al_musthofa)
        )

        dataMp3.add(
            Model(7, "Law Kana Bainanal Habib Ya Salam Alhamdu", "Nissa", R.drawable.nissa7, R.raw.sabyan_ibadallah)
        )

        dataMp3.add(
            Model(8, "Ahmad Ya Habibi", "Nissa", R.drawable.nissa8, R.raw.sabyanalma_al_musthofa)
        )

        dataMp3.add(
            Model(9, "Ya Taiba", "Nissa", R.drawable.nissa9, R.raw.sabyan_ibadallah)
        )

        dataMp3.add(
            Model(10, "Qomarun", "Nissa", R.drawable.nissa10, R.raw.sabyanalma_al_musthofa)
        )

        return dataMp3
    }
}