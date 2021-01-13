package com.mp3.offline.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mp3.offline.databinding.ItemBinding
import com.mp3.offline.model.Model
import com.mp3.offline.ui.CostumeOnItemClickCallback
import com.mp3.offline.ui.DetailPlayActivity
import kotlinx.android.synthetic.main.item.view.*

class ListAdapter(private val activity: Activity, private val listItem: ArrayList<Model>): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    inner class ListViewHolder(private val binding: ItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Model){
            binding.tvTitle.text = model.title
            binding.tvArtisName.text = model.artist
            binding.tvDuration.text = model.duration
            binding.imgCover.setImageResource(model.photo)

            binding.cardViewItem.setOnClickListener(CostumeOnItemClickCallback(object : CostumeOnItemClickCallback.OnItemClickCallback {
                override fun onItemClickCallback(v: View?) {
                    val intent = Intent(activity, DetailPlayActivity::class.java)
                    intent.putExtra("keyData", model)
                    activity.startActivity(intent)
                }
            }))
        }
    }
}