package com.example.newapp.news.domain

import java.time.LocalDate

interface NewDataSource {
    suspend fun getEverything(
        query: String? = "",
        queryInTitle: String? = "",
        searchIn: List<String>? = emptyList(),
        sources: List<String>? = emptyList(),
        domains: List<String>? = emptyList(),
        excludeDomains: List<String>? = emptyList(),
        from: LocalDate? = null,
        to: LocalDate? = null,
        language: String? = "",
        sortBy: String? = "",
        pageSize: Int? = null,
        page: Int? = null
    ): NewResponse

    suspend fun getTopHeadlines(
        country: String? = "",
        category: List<String>? = emptyList(),
        sources: List<String>? = emptyList(),
        query: String? = "",
        pageSize: Int? = null,
        page: Int? = null,
        language: String? = ""
    ): NewResponse
}