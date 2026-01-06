package com.jdpadillavigo.newsapp.news.presentation.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdpadillavigo.newsapp.news.presentation.discover.components.SearchComponent
import com.jdpadillavigo.newsapp.news.presentation.home.ScreenGradient
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
fun DiscoverScreen(
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

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.DESKTOP -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                DiscoverSearch(
                    onAction = onAction
                )
                DiscoverList(
                    state = state,
                    onAction = onAction,
                    loadNews = loadNews
                )
            }
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_LANDSCAPE -> {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                DiscoverSearch(
                    onAction = onAction,
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
                DiscoverList(
                    state = state,
                    onAction = onAction,
                    loadNews = loadNews
                )
            }
        }
    }
}

@Composable
private fun DiscoverSearch(
    onAction: (NewListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchTopics = remember {
        mutableStateListOf(
            "All",
            "Politic",
            "Sport",
            "Education",
            "Gaming",
            "World"
        )
    }

    var topicSelected by remember { mutableStateOf("All") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Discover",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
            Text(
                text = "News from all around the world",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )
        }
        SearchComponent(
            searchTopics = searchTopics,
            onAction = onAction
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.width(12.dp))
            }
            items(searchTopics.size) { item ->
                if(topicSelected == searchTopics[item]) {
                    TopicButton(
                        text = searchTopics[item],
                        onClick = {},
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                } else {
                    TopicButton(
                        text = searchTopics[item],
                        onClick = {
                            topicSelected = searchTopics[item]
                            onAction(NewListAction.OnLoadClick(searchTopics[item]))
                        },
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}

@Composable
private fun DiscoverList(
    state: NewListState,
    onAction: (NewListAction) -> Unit,
    loadNews: String,
    modifier: Modifier = Modifier
) {
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
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NewListScreen(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
            ScreenGradient(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun TopicButton(
    text: String,
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor,
            disabledContentColor = contentColor
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 16.sp
        )
    }
}

@PreviewLightDark
@Composable
private fun DiscoverScreenPreview() {
    NewsAppTheme {
        DiscoverScreen(
            state = NewListState(
                news = (1..10).map {
                    previewNew
                }
            ),
            onAction = {},
            loadNews = "everything"
        )
    }
}