package com.mp3.offline.ui

import android.app.SearchManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        val data = viewModel.getDataMp3()

        binding.rvItem.setHasFixedSize(true)

        binding.rvItem.layoutManager = LinearLayoutManager(this)
        listAdapter = ListAdapter(this)
        listAdapter.setData(data as ArrayList<Model>)
        binding.rvItem.adapter = listAdapter

        setSearch()

        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
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
