package com.mp3.offline.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mp3.offline.databinding.ItemBinding
import com.mp3.offline.model.Model
import com.mp3.offline.ui.DetailPlayActivity
import com.mp3.offline.utils.AdmobAd

class ListAdapter(
    private val activity: Activity)
    : RecyclerView.Adapter<ListAdapter.ListViewHolder>(),
    Filterable {

    private val TAG = "ListAdapter"
    private var data = ArrayList<Model>()
    private val dataFilter = ArrayList<Model>()
    private var admobAd = AdmobAd(activity)

    @SuppressLint("NotifyDataSetChanged")
    @JvmName("setData1")
    fun setData(items: ArrayList<Model>) {
        data.clear()
        data.addAll(items)
        dataFilter.addAll(items)
        notifyDataSetChanged()
        //Load Admob Ad
        admobAd.interstitialLoad()
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

            binding.cardViewItem.setOnClickListener {
                Log.d(TAG, "card View Item Clicked")

                //show Admob interstitial Ad
                admobAd.showInterstitialAd()

                //Pindah ke Detail dan Membawa data
                val intent = Intent(activity, DetailPlayActivity::class.java)
                intent.putExtra("keyData", model)
                activity.startActivity(intent)
            }

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