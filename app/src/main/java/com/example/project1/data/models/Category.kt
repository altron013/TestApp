package com.example.project1.data.models

data class Category(
    var title: String?,
    var category_id: Int?,
    var color: String?,
    var url: String?,
    var image_path: String?,

    var type: String?,
    var order: Int?,
    var transparency: String?,
    var subcategories: List<Subcategory>?
)

data class CustomMainCategory(
    var cats: List<Subcategory>
)
