package com.charlezz.mergeadaptersample.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PostService {
    @GET("posts")
    Call<List<PostItem>> getPosts(@Query("_page") int page);

    @GET("posts?_page=1")
    Call<List<PostItem>> getTopPosts();

}
