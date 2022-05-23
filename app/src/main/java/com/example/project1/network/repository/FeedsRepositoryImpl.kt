package com.example.project1.network.repository

import com.example.project1.data.models.Category
import com.example.project1.data.models.Feeds
import com.example.project1.network.api.FeedApi

class FeedsRepositoryImpl(private val feedApi: FeedApi) : FeedsRepository {

    override suspend fun getFeeds(): Feeds? {
        return feedApi.getFeeds().await().body()
    }

    override suspend fun getCategories(): List<Category>? {
        return feedApi.getCategories().await().body()
    }
}