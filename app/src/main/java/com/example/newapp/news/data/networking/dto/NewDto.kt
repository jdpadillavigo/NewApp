package com.example.newapp.news.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class NewDto(
    val source: SourceDto? = SourceDto(),
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String? = "",
    val content: String? = ""
)

@Serializable
data class SourceDto(
    val id: String? = "",
    val name: String = ""
)