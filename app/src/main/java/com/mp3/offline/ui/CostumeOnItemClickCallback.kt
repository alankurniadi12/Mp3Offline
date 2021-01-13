package com.mp3.offline.ui

import android.view.View

class CostumeOnItemClickCallback(private val onItemClickCallback: OnItemClickCallback): View.OnClickListener {
    override fun onClick(v: View?) {
        onItemClickCallback.onItemClickCallback(v)
    }

    interface OnItemClickCallback {
        fun onItemClickCallback(v: View?)
    }
}