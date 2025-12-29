package com.example.newapp.news.data.networking

import com.example.newapp.BuildConfig
import com.example.newapp.core.data.networking.constructUrl
import com.example.newapp.core.data.networking.safeCall
import com.example.newapp.news.data.mappers.toNew
import com.example.newapp.news.domain.NewDataSource
import com.example.newapp.news.domain.NewResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.LocalDate

data class RemoteNewDataSource(
    private val httpClient: HttpClient
): NewDataSource {
    override suspend fun getEverything(
        query: String?,
        queryInTitle: String?,
        searchIn: List<String>?,
        sources: List<String>?,
        domains: List<String>?,
        excludeDomains: List<String>?,
        from: LocalDate?,
        to: LocalDate?,
        language: String?,
        sortBy: String?,
        pageSize: Int?,
        page: Int?
    ): NewResponse {
        return safeCall(
            execute = {
                httpClient.get(
                    urlString = constructUrl("/everything")
                ) {
                    parameter("apiKey", BuildConfig.API_KEY)
                    if(query != "" && query != null) parameter("q", query)
                    if(queryInTitle != "" && queryInTitle != null) parameter("qInTitle", queryInTitle)
                    if(searchIn != listOf("") && searchIn != null) parameter("searchIn", searchIn.joinToString(","))
                    if(sources != listOf("") && sources != null) parameter("sources", sources.joinToString(","))
                    if(domains != listOf("") && domains != null) parameter("domains", domains.joinToString(","))
                    if(excludeDomains != listOf("") && excludeDomains != null) parameter("excludeDomains", excludeDomains.joinToString(","))
                    if(from != null) parameter("from", from.toString())
                    if(to != null) parameter("to", to.toString())
                    if(language != "" && language != null) parameter("language", language)
                    if(sortBy != "" && sortBy != null) parameter("sortBy", sortBy)
                    if(pageSize != null) parameter("pageSize", pageSize)
                    if(page != null) parameter("page", page)
                }
            },
            onSuccess = { responseBody ->
                NewResponse(
                    status = responseBody.status,
                    totalResults = responseBody.totalResults,
                    articles = responseBody.articles?.map { it.toNew() }
                )
            }
        )
    }

    override suspend fun getTopHeadlines(
        country: String?,
        category: List<String>?,
        sources: List<String>?,
        query: String?,
        pageSize: Int?,
        page: Int?,
        language: String?
    ): NewResponse {
        var paramsQuantity = 0
        return safeCall(
            execute = {
                httpClient.get(
                    urlString = constructUrl("/top-headlines")
                ) {
                    parameter("apiKey", BuildConfig.API_KEY)
                    if(country != "" && country != null && paramsQuantity == 0) {
                        parameter("country", country)
                        paramsQuantity++
                    }
                    if(category != listOf("") && category != null && paramsQuantity == 0) {
                        parameter("category", category.joinToString(","))
                        paramsQuantity++
                    }
                    if(sources != listOf("") && sources != null && paramsQuantity == 0) {
                        parameter("sources", sources.joinToString(","))
                        paramsQuantity++
                    }
                    if(query != "" && query != null) parameter("q", query)
                    if(pageSize != null) parameter("pageSize", pageSize)
                    if(page != null) parameter("page", page)
                    if(language != "" && language != null) parameter("language", language)
                }
            },
            onSuccess = { responseBody ->
                NewResponse(
                    status = responseBody.status,
                    totalResults = responseBody.totalResults,
                    articles = responseBody.articles?.map { it.toNew() }
                )
            }
        )
    }
}