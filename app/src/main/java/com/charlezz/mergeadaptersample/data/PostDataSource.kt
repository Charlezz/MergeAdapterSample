package com.charlezz.mergeadaptersample.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class PostDataSource(
    private val postService: PostService,
    private val state: MutableLiveData<State>
) : PageKeyedDataSource<Int, PostItem>() {
    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("\\b_page=(\\d+)")
        val cachedItems = ArrayList<PostItem>()
        var cachedNextPage:Int? = null
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PostItem>
    ) {
        if(cachedItems.isNotEmpty()){
            callback.onResult(cachedItems, null, cachedNextPage)
        }else{
            val request = postService.topPosts
            try {
                state.postValue(State.LOADING)
                Thread.sleep(2000)
                val response = request.execute()
                val items = response.body()!!
                cachedItems.addAll(items)
                val nextPage = getNextPage(response)
                cachedNextPage = nextPage
                callback.onResult(items, null, nextPage)
                state.postValue(State.NONE)
            } catch (e: Exception) {
                e.printStackTrace()
                state.postValue(State.ERROR)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PostItem>) {
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PostItem>
    ) {
        val request = postService.getPosts(params.key)
        try {
            state.postValue(State.LOADING)
            Thread.sleep(2000)
            val response = request.execute()
            val items = response.body()!!
            cachedItems.addAll(items)
            val nextPage = getNextPage(response)
            cachedNextPage = nextPage
            callback.onResult(items, nextPage)
            state.postValue(State.NONE)
        } catch (e: Exception) {
            e.printStackTrace()
            state.postValue(State.ERROR)
        }
    }

    private fun extractLink(str: String?): HashMap<String, String> {
        val matcher = LINK_PATTERN.matcher(str)
        val links = HashMap<String, String>()
        while (matcher.find()) {
            val count = matcher.groupCount()
            if (count == 2) {
                links[matcher.group(2)] = matcher.group(1)
            }
        }
        return links
    }

    private fun getNextPage(response: Response<List<PostItem>>): Int? {
        val headers = response.headers()
        val linkString = headers["link"]
        val map: HashMap<*, *> = extractLink(linkString)
        val nextUrl = map["next"] as String? ?: return null
        val matcher = PAGE_PATTERN.matcher(nextUrl)
        return if (!matcher.find() || matcher.groupCount() != 1) {
            null
        } else {
            try {
                matcher.group(1).toInt()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                null
            }
        }
    }

}