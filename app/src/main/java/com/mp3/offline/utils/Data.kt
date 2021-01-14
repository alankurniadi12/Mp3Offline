package com.mp3.offline.utils

import com.mp3.offline.R
import com.mp3.offline.model.Model

object Data {

    fun getDataMp3(): List<Model> {
        val dataMp3 = ArrayList<Model>()

        dataMp3.add(
            Model(1, "Title Lagu 1 Disini", "Nissa", R.drawable.nissa1, R.raw.sabyan_ibadallah)
        )

        dataMp3.add(
            Model(2, "Title Lagu 2 Disini", "Nissa", R.drawable.nissa2, R.raw.sabyanalma_al_musthofa)
        )

        dataMp3.add(
            Model(3, "Title Lagu 3 Disini", "Nissa", R.drawable.nissa3, R.raw.sabyan_ibadallah)
        )

        dataMp3.add(
            Model(4, "Title Lagu 4 Disini", "Nissa", R.drawable.nissa4, R.raw.sabyanalma_al_musthofa)
        )

        dataMp3.add(
            Model(5, "Title Lagu  5Disini", "Nissa", R.drawable.nissa5, R.raw.sabyan_ibadallah)
        )

        dataMp3.add(
            Model(6, "Title Lagu 6 Disini", "Nissa", R.drawable.nissa6, R.raw.sabyanalma_al_musthofa)
        )

        dataMp3.add(
            Model(7, "Title Lagu 7 Disini", "Nissa", R.drawable.nissa7, R.raw.sabyan_ibadallah)
        )

        dataMp3.add(
            Model(8, "Title Lagu 8 Disini", "Nissa", R.drawable.nissa8, R.raw.sabyanalma_al_musthofa)
        )

        dataMp3.add(
            Model(9, "Title Lagu 9 Disini", "Nissa", R.drawable.nissa9, R.raw.sabyan_ibadallah)
        )

        dataMp3.add(
            Model(10, "Title Lagu 10 Disini", "Nissa", R.drawable.nissa10, R.raw.sabyanalma_al_musthofa)
        )

        return dataMp3
    }
}