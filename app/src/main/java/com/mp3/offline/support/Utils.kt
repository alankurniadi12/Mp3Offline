package com.mp3.offline.support

import com.mp3.offline.R
import com.mp3.offline.model.Model
import java.lang.reflect.Field

object Utils {

    fun getMp3File(): List<Model> {
        val mp3: MutableList<Model> = ArrayList()
        val fields: Array<Field> = R.raw::class.java.fields
        for (i in fields.indices) {
            try {
                val title =fields[i].name.replace("_", " ")
                mp3.add(
                    Model(
                        id = fields[i].getInt(fields[i]),
                        title = changeTitleForItemList(title))
                )
            }catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
        return mp3
    }

    private fun changeTitleForItemList(title: String): String {
        val words = title.trim { it <= ' ' }.split(" ").toTypedArray()
        val ret = StringBuilder()
        for (i in words.indices) {
            if (words[i].trim { it <= ' ' }.isNotEmpty()) {
                ret.append(Character.toUpperCase(words[i].trim { it <= ' ' }[0]))
                ret.append(words[i].trim { it <= ' ' }.substring(1))
                if (i < words.size - 1) {
                    ret.append(' ')
                }
            }
        }
        return ret.toString()
    }

}