package com.example.project1.network.api

import com.example.project1.data.models.Category
import com.example.project1.data.models.Feeds
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface FeedApi {

    @GET("feed.json")
    fun getFeeds(): Deferred<Response<Feeds>>


    @GET("market.json")
    fun getCategories(): Deferred<Response<List<Category>>>

}