package com.example.newapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.ui.Modifier
import com.example.newapp.core.navigation.NavigationRoot
import com.example.newapp.news.presentation.new_list.NewListViewModel
import com.example.newapp.ui.theme.NewAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: NewListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewAppTheme {
                NavigationRoot(
                    modifier = Modifier
                        .consumeWindowInsets(WindowInsets.navigationBars),
                    viewModel = viewModel
                )
            }
        }
    }
}
