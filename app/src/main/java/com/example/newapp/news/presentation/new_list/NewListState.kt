package com.example.newapp.news.presentation.new_list

import androidx.compose.runtime.Immutable
import com.example.newapp.news.presentation.models.NewUi

@Immutable
data class NewListState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val news: List<NewUi> = emptyList(),
    val selectedNew: NewUi? = null
)