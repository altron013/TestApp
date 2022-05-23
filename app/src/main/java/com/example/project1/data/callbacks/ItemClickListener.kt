package com.example.project1.data.callbacks

import com.example.project1.data.models.Todo

interface ItemClickListener {
    fun onItemClick(item: Todo)
    fun onItemMenuClick(item: Todo)
}