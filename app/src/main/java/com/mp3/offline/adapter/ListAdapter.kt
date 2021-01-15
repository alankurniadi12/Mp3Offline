package com.mp3.offline.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mp3.offline.databinding.ItemBinding
import com.mp3.offline.model.Model
import com.mp3.offline.ui.CostumeOnItemClickCallback
import com.mp3.offline.ui.DetailPlayActivity
import java.util.*
import kotlin.collections.ArrayList

class ListAdapter(private val activity: Activity):
    RecyclerView.Adapter<ListAdapter.ListViewHolder>(),
    Filterable {


    var data = ArrayList<Model>()
    val dataFilter = ArrayList<Model>()

    @JvmName("setData1")
    fun setData(items: ArrayList<Model>) {
        data.clear()
        data.addAll(items)
        dataFilter.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ListViewHolder(private val binding: ItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Model){
            binding.tvTitle.text = model.title
            binding.tvArtisName.text = model.artist
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

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResult = FilterResults()
                if (constraint == null || constraint.length < 0) {
                    filterResult.count = dataFilter.size
                    filterResult.values = dataFilter
                } else {
                    val search = constraint.toString().toLowerCase()
                    val itemModel = ArrayList<Model>()
                    for (i in dataFilter) {
                        if (i.title.toLowerCase().contains(search) || i.artist.toLowerCase().contains(search)) {
                            itemModel.add(i)
                        }
                    }
                    filterResult.count = itemModel.size
                    filterResult.values = itemModel
                }
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                data = results!!.values as ArrayList<Model>
                notifyDataSetChanged()
            }

        }
    }
}