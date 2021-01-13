package com.mp3.offline.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mp3.offline.adapter.ListAdapter
import com.mp3.offline.databinding.ActivityMainBinding
import com.mp3.offline.model.Model
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rv_item.setHasFixedSize(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        val data = viewModel.getDataMp3()

        rv_item.layoutManager = LinearLayoutManager(this)
        val listViewAdapter = ListAdapter(this, data as ArrayList<Model>)
        rv_item.adapter = listViewAdapter
    }


}
