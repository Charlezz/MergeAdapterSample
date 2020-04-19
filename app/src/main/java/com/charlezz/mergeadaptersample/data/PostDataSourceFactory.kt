package com.charlezz.mergeadaptersample.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PostDataSourceFactory : DataSource.Factory<Int, PostItem>() {

    val state = MutableLiveData<State>()
    lateinit var currentDataSource: PostDataSource

    private val okhttp = OkHttpClient.Builder()
        .callTimeout(3000, TimeUnit.MILLISECONDS)
        .build()

    private val postService = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
//        .client(okhttp)
        .build()
        .create(PostService::class.java)

    override fun create(): DataSource<Int, PostItem> {
        currentDataSource = PostDataSource(postService, state)
        return currentDataSource
    }


}