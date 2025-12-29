package com.example.newapp.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.newapp.news.presentation.bookmark.BookmarkScreen
import com.example.newapp.news.presentation.discover.DiscoverScreen
import com.example.newapp.news.presentation.home.HomeScreen
import com.example.newapp.news.presentation.new_detail.NewDetailScreen
import com.example.newapp.news.presentation.new_list.NewListAction
import com.example.newapp.news.presentation.new_list.NewListViewModel
import com.example.newapp.news.presentation.profile.ProfileScreen

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    viewModel: NewListViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navigationState = rememberNavigationState(
        startRoute = Route.Home,
        topLevelRoutes = TOP_LEVEL_DESTINATIONS.keys
    )
    val navigator = remember {
        Navigator(navigationState)
    }

    val currentStack = navigationState.backStacks[navigationState.topLevelRoute]
    val currentRoute = currentStack?.lastOrNull() ?: navigationState.topLevelRoute
    val shouldShowBars = currentRoute != Route.NewDetail

    Scaffold(
        modifier = modifier,
        topBar = {
            if (shouldShowBars) {
                TopNavBar(
                    currentRoute = navigationState.topLevelRoute
                )
            }
        },
        bottomBar = {
            if (shouldShowBars) {
                BottomNavBar(
                    selectedKey = navigationState.topLevelRoute,
                    onSelectKey = {
                        navigator.navigate(it)
                    }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onBack = navigator::goBack,
            entries = navigationState.toEntries(
                entryProvider {
                    entry<Route.NewDetail> {
                        NewDetailScreen(
                            state = state,
                            onBack = navigator::goBack,
                        )
                    }
                    entry<Route.Home> {
                        HomeScreen(
                            state = state,
                            onAction = { action ->
                                viewModel.onAction(action)
                                if (action is NewListAction.OnNewClick) {
                                    navigator.navigate(Route.NewDetail)
                                }
                            }
                        )
                    }
                    entry<Route.Discover> {
                        DiscoverScreen(
                            state = state,
                            onAction = { action ->
                                viewModel.onAction(action)
                                if (action is NewListAction.OnNewClick) {
                                    navigator.navigate(Route.NewDetail)
                                }
                            }
                        )
                    }
                    entry<Route.Bookmark> {
                        BookmarkScreen()
                    }
                    entry<Route.Profile> {
                        ProfileScreen()
                    }
                }
            )
        )
    }
}