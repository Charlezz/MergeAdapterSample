package com.charlezz.mergeadaptersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.charlezz.mergeadaptersample.data.PostDataSourceFactory
import com.charlezz.mergeadaptersample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mergeAdapter: MergeAdapter

    private val dataSourceFactory = PostDataSourceFactory()
    private val headerAdapter = HeaderAdapter()
    private val postAdapter = PostAdapter()
    private val footerAdapter = FooterAdapter(View.OnClickListener { dataSourceFactory.currentDataSource.invalidate() })



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataSourceFactory.state.observe(this, Observer { footerAdapter.setState(it) })

        val pageConfig = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()

        LivePagedListBuilder(dataSourceFactory, pageConfig).build()
            .observe(this, Observer { postAdapter.submitList(it) })

        val config = MergeAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .setStableIdMode(MergeAdapter.Config.StableIdMode.NO_STABLE_IDS)
            .build()

        mergeAdapter = MergeAdapter(config, headerAdapter, postAdapter, footerAdapter)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = mergeAdapter

    }


}
