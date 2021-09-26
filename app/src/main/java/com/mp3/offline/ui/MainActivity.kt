package com.mp3.offline.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.mp3.offline.R
import com.mp3.offline.adapter.ListAdapter
import com.mp3.offline.databinding.ActivityMainBinding
import com.mp3.offline.model.Model
import com.mp3.offline.utils.AdmobAd
import java.util.*

class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: ListAdapter
    private lateinit var viewModel: MainViewModel
    private var mInterstitialAd: InterstitialAd? = null
    private var backToast: Toast? = null
    private var backPress: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //admob init
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        val data = viewModel.getDataMp3()

        setRecyclerView(data, adRequest)
        setSearch()
    }

    private fun setRecyclerView( data: List<Model>, adRequest: AdRequest) {
        binding.rvItem.layoutManager = LinearLayoutManager(this)
        listAdapter = ListAdapter(this, adRequest)
        listAdapter.setData(data as ArrayList<Model>)
        binding.rvItem.adapter = listAdapter

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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        backToast = Toast.makeText(this, "Press back again to exit!", Toast.LENGTH_SHORT)
        if (backPress + 2000 > System.currentTimeMillis()) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            backToast?.cancel()
            return super.onKeyDown(keyCode, event)
        } else {
            backToast?.show()
        }
        backPress = System.currentTimeMillis()
        return true

    }
}
