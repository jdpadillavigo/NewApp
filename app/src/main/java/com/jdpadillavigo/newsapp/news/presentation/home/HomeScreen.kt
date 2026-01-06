package com.jdpadillavigo.newsapp.news.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdpadillavigo.newsapp.news.presentation.home.components.CarouselCard
import com.jdpadillavigo.newsapp.news.presentation.home.components.EmptyScreen
import com.jdpadillavigo.newsapp.news.presentation.home.components.ErrorScreen
import com.jdpadillavigo.newsapp.news.presentation.home.components.LoadingScreen
import com.jdpadillavigo.newsapp.news.presentation.new_list.NewListAction
import com.jdpadillavigo.newsapp.news.presentation.new_list.NewListScreen
import com.jdpadillavigo.newsapp.news.presentation.new_list.NewListState
import com.jdpadillavigo.newsapp.news.presentation.new_list.components.previewNew
import com.jdpadillavigo.newsapp.ui.theme.NewsAppTheme
import com.jdpadillavigo.newsapp.ui.util.DeviceConfiguration

@Composable
fun HomeScreen(
    state: NewListState,
    onAction: (NewListAction) -> Unit,
    loadNews: String,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(loadNews) {
        onAction(NewListAction.OnLoadClick(loadNews))
    }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    if(state.isLoading) {
        LoadingScreen(modifier = modifier)
    } else if(state.errorMessage != null) {
        ErrorScreen(
            state = state,
            onAction = onAction,
            loadNews = loadNews,
            modifier = modifier
        )
    } else if(state.news.isEmpty()) {
        EmptyScreen(modifier = modifier)
    } else {
        when(deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.DESKTOP -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(top = 10.dp)
                            .align(Alignment.Center)
                    ) {
                        HomeBreakingNews(
                            state = state,
                            onAction = onAction,
                            horizontalPadding = 20.dp,
                            modifier = Modifier.fillMaxHeight(0.4f)
                        )
                        HomeRecommendation(
                            state = state,
                            onAction = onAction
                        )
                    }
                    ScreenGradient(modifier = Modifier.align(Alignment.Center))
                }
            }
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_LANDSCAPE -> {
                Row(
                    modifier = modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    HomeBreakingNews(
                        state = state,
                        onAction = onAction,
                        horizontalPadding = 0.dp,
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(horizontal = 20.dp)
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        HomeRecommendation(
                            state = state,
                            onAction = onAction
                        )
                        ScreenGradient(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeBreakingNews(
    state: NewListState,
    onAction: (NewListAction) -> Unit,
    horizontalPadding: Dp,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        HeaderLabel(
            headerLabel = "Breaking News",
            modifier = Modifier.padding(horizontal = horizontalPadding)
        )
        CarouselCard(
            news = state.news,
            onAction = onAction,
            horizontalPadding = horizontalPadding
        )
    }
}

@Composable
private fun HomeRecommendation(
    state: NewListState,
    onAction: (NewListAction) -> Unit
) {
    Column {
        HeaderLabel(
            headerLabel = "Recommendation",
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        NewListScreen(
            state = state,
            onAction = onAction,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun ScreenGradient(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Transparent,
                        Color.Transparent,
                        Color.Transparent,
                        Color.Transparent,
                        Color.Transparent,
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                    )
                )
            )
    )
}

@Composable
private fun HeaderLabel(
    headerLabel: String,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = headerLabel,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = {},
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "View all",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    NewsAppTheme {
        HomeScreen(
            state = NewListState(
                news = (1..10).map {
                    previewNew
                }
            ),
            onAction = {},
            loadNews = "top-headlines"
        )
    }
}