package com.jdpadillavigo.newsapp.news.presentation.new_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdpadillavigo.newsapp.core.data.networking.HttpClientFactory.create
import com.jdpadillavigo.newsapp.news.data.networking.RemoteNewDataSource
import com.jdpadillavigo.newsapp.news.presentation.models.NewUi
import com.jdpadillavigo.newsapp.news.presentation.models.toNewUi
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class NewListViewModel: ViewModel() {
    private val remoteNewDataSource = RemoteNewDataSource(
        httpClient = create(CIO)
    )

    private val _state = MutableStateFlow(NewListState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            NewListState()
        )

    private fun loadEverything(
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
    ) {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true,
                errorMessage = null
            )}

            val response = remoteNewDataSource.getEverything(
                query = query,
                queryInTitle = queryInTitle,
                searchIn = searchIn,
                sources = sources,
                domains = domains,
                excludeDomains = excludeDomains,
                from = from,
                to = to,
                language = language,
                sortBy = sortBy,
                pageSize = pageSize,
                page = page
            )
            if (response.status == "ok") {
                _state.update {
                    it.copy(
                        isLoading = false,
                        news = response.articles?.map { new -> new.toNewUi() } ?: emptyList()
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = response.message
                    )
                }
            }
        }
    }

    private fun loadTopHeadlines(
        country: String? = "",
        category: List<String>? = emptyList(),
        sources: List<String>? = emptyList(),
        query: String? = "",
        pageSize: Int? = null,
        page: Int? = null,
        language: String? = ""
    ) {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true,
                errorMessage = null
            )}

            val response = remoteNewDataSource.getTopHeadlines(
                country = country,
                category = category,
                sources = sources,
                query = query,
                pageSize = pageSize,
                page = page,
                language = language
            )
            if (response.status == "ok") {
                _state.update {
                    it.copy(
                        isLoading = false,
                        news = response.articles?.map { new -> new.toNewUi() } ?: emptyList()
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = response.message
                    )
                }
            }
        }
    }

    private fun selectNew(newUi: NewUi) {
        _state.update { it.copy(selectedNew = newUi) }
    }

    fun onAction(action: NewListAction) {
        when(action) {
            is NewListAction.OnNewClick -> {
                selectNew(action.newUi)
            }
            is NewListAction.OnLoadClick -> {
                if(action.load == "everything") {
                    loadEverything(
                        query = "All"
                    )
                } else if (action.load == "top-headlines") {
                    loadTopHeadlines(
                        language = "en"
                    )
                } else {
                    loadEverything(
                        query = action.load
                    )
                }
            }
        }
    }
}