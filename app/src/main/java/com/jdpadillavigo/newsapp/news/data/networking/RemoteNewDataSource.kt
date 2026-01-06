package com.jdpadillavigo.newsapp.news.data.networking

import com.jdpadillavigo.newsapp.BuildConfig
import com.jdpadillavigo.newsapp.core.data.networking.constructUrl
import com.jdpadillavigo.newsapp.core.data.networking.safeCall
import com.jdpadillavigo.newsapp.news.data.mappers.toNew
import com.jdpadillavigo.newsapp.news.domain.NewDataSource
import com.jdpadillavigo.newsapp.news.domain.NewResponse
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
                    if(!query.isNullOrBlank()) parameter("q", query)
                    if(!queryInTitle.isNullOrBlank()) parameter("qInTitle", queryInTitle)
                    if(!searchIn.isNullOrEmpty()) parameter("searchIn", searchIn.joinToString(","))
                    if(!sources.isNullOrEmpty()) parameter("sources", sources.joinToString(","))
                    if(!domains.isNullOrEmpty()) parameter("domains", domains.joinToString(","))
                    if(!excludeDomains.isNullOrEmpty()) parameter("excludeDomains", excludeDomains.joinToString(","))
                    if(from != null) parameter("from", from.toString())
                    if(to != null) parameter("to", to.toString())
                    if(!language.isNullOrBlank()) parameter("language", language)
                    if(!sortBy.isNullOrBlank()) parameter("sortBy", sortBy)
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
                    if(!country.isNullOrBlank() && paramsQuantity == 0) {
                        parameter("country", country)
                        paramsQuantity++
                    }
                    if(!category.isNullOrEmpty() && paramsQuantity == 0) {
                        parameter("category", category.joinToString(","))
                        paramsQuantity++
                    }
                    if(!sources.isNullOrEmpty() && paramsQuantity == 0) {
                        parameter("sources", sources.joinToString(","))
                        paramsQuantity++
                    }
                    if(!query.isNullOrBlank()) parameter("q", query)
                    if(pageSize != null) parameter("pageSize", pageSize)
                    if(page != null) parameter("page", page)
                    if(!language.isNullOrBlank()) parameter("language", language)
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