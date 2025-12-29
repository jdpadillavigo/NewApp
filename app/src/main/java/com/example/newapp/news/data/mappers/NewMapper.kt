package com.example.newapp.news.data.mappers

import com.example.newapp.news.data.networking.dto.NewDto
import com.example.newapp.news.data.networking.dto.SourceDto
import com.example.newapp.news.domain.New
import com.example.newapp.news.domain.Source

fun NewDto.toNew(): New {
    return New(
        source = (source ?: SourceDto()).toSource(),
        author = author.orEmpty(),
        title = title.orEmpty(),
        description = description.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        content = content.orEmpty()
    )
}

fun SourceDto.toSource(): Source {
    return Source(
        id = id,
        name = name
    )
}