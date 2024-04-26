package com.sample.pagination.data.model

data class PostData(
    val userId : Int,
    val id : Int,
    val title: String,
    val body: String = ""
)
