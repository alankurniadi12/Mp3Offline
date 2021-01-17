package com.mp3.offline.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.mp3.offline.R
import com.mp3.offline.adapter.ListAdapter
import com.mp3.offline.databinding.ActivityMainBinding
import com.mp3.offline.model.Model
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: ListAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //admob code
        MobileAds.initialize(this) {}
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = resources.getString(R.string.AdMob_interstitial_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        val data = viewModel.getDataMp3()

        setRecyclerView(data)
        setSearch()

    }

    private fun setRecyclerView(data: List<Model>) {
        binding.rvItem.layoutManager = LinearLayoutManager(this)
        listAdapter = ListAdapter(this)
        listAdapter.setData(data as ArrayList<Model>)
        binding.rvItem.adapter = listAdapter

        listAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Model) {

                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                }

                val intent = Intent(this@MainActivity, DetailPlayActivity::class.java)
                intent.putExtra("keyData", data)
                startActivity(intent)
            }
        })
    }

    private fun setSearch(): Boolean {
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager

        binding.search.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        binding.search.queryHint = resources.getString(R.string.search_title)
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                listAdapter.filter.filter(query)
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                listAdapter.filter.filter(newText)
                return true
            }
        })
        return true
    }
}
