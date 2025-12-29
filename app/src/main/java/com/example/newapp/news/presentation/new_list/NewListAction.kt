package com.example.newapp.news.presentation.new_list

import com.example.newapp.news.presentation.models.NewUi

sealed interface NewListAction {
    data class OnNewClick(val newUi: NewUi): NewListAction
    data class OnRetryClick(val load: String): NewListAction
}