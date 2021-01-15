package com.mp3.offline.ui

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mp3.offline.R
import com.mp3.offline.adapter.ListAdapter
import com.mp3.offline.databinding.ActivityMainBinding
import com.mp3.offline.model.Model
import kotlinx.android.synthetic.main.activity_main.*

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

        rv_item.setHasFixedSize(true)

        rv_item.layoutManager = LinearLayoutManager(this)
        listAdapter = ListAdapter(this)
        listAdapter.setData(data as ArrayList<Model>)
        rv_item.adapter = listAdapter

        setSearch()
    }

    private fun setSearch(): Boolean {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

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
