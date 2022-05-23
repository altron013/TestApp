package com.example.project1.network.repository

import com.example.project1.data.models.Category
import com.example.project1.data.models.Feeds

interface FeedsRepository {

    suspend fun getFeeds(): Feeds?

    suspend fun getCategories(): List<Category>?

}