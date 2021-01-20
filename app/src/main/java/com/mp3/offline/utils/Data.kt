package com.mp3.offline.utils

import com.mp3.offline.R
import com.mp3.offline.model.Model

object Data {

    fun getDataMp3(): List<Model> {
        val dataMp3 = ArrayList<Model>()

        dataMp3.add(
            Model(1, "Ibadallah", "Nissa", R.drawable.nissa1, R.raw.sabyan_ibadallah)
        )

        dataMp3.add(
            Model(2, "Al Musthofa", "Nissa X Alma", R.drawable.nissa2, R.raw.sabyanalma_al_musthofa)
        )

        dataMp3.add(
            Model(3, "Huwannur", "Nissa", R.drawable.nissa3, R.raw.huwannur)
        )

        dataMp3.add(
            Model(4, "Ya Muhaimin", "Nissa", R.drawable.nissa4, R.raw.ya_muhaimin)
        )

        dataMp3.add(
            Model(5, "Ala Bali", "Nissa", R.drawable.nissa5, R.raw.ala_bali)
        )

        dataMp3.add(
            Model(6, "Albi Nadak", "Nissa", R.drawable.nissa6, R.raw.albi_nadak)
        )

        dataMp3.add(
            Model(7, "Teman Sejati", "Nissa X Tasya Rosmala", R.drawable.nissa7, R.raw.teman_sejati)
        )

        dataMp3.add(
            Model(8, "Fatimah Az Zahra", "Nissa", R.drawable.nissa8, R.raw.fatimah_az_zahra)
        )

        dataMp3.add(
            Model(9, "Aisyah", "Nissa", R.drawable.nissa9, R.raw.aisyah)
        )

        dataMp3.add(
            Model(10, "Addinu Lana", "Nissa", R.drawable.nissa10, R.raw.addinu_lana)
        )

        return dataMp3
    }
}