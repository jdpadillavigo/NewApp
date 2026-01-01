package com.example.newapp.news.presentation.discover.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.ui.theme.NewAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchComponent(
    searchTopics: MutableList<String>,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var searchHistory = remember {
        mutableStateListOf<String>()
    }
    val searchColor = MaterialTheme.colorScheme.onSurfaceVariant
    val searchContainerColor = MaterialTheme.colorScheme.surfaceVariant

    val searchModifier = if(active) {
        modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp
                )
            )
            .background(searchContainerColor)
            .padding(horizontal = 20.dp)
    } else {
        modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .padding(horizontal = 20.dp)
    }

    val searchTopicsList = searchTopics.drop(1)

    Box(modifier = searchModifier) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = text,
            onQueryChange = {
                text = it
            },
            onSearch = {
                active = false
                if (text.isNotBlank()) {
                    searchHistory.add(text)
                }
            },
            active = active,
            onActiveChange = {
                active = it
                text = ""
            },
            placeholder = {
                SearchText(
                    text = "Search",
                    color = searchColor
                )
            },
            leadingIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search news",
                        tint = searchColor
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Filter search",
                        tint = searchColor
                    )
                }
            },
            colors = SearchBarColors(
                containerColor = searchContainerColor,
                dividerColor = searchColor
            )
        ) {
            LazyColumn {
                if (searchHistory.isNotEmpty()) {
                    items(searchHistory.size) { item ->
                        if(text.isBlank() ||
                            searchHistory[item].contains(text, ignoreCase = true)) {
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                        .clickable {
                                            text = searchHistory[item]
                                        },
                                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.History,
                                        contentDescription = "Recent search",
                                        tint = searchColor
                                    )
                                    SearchText(
                                        text = searchHistory[item],
                                        color = searchColor
                                    )
                                }
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = searchColor
                                )
                            }
                        }
                    }
                }
                items(searchTopicsList.size) { item ->
                    if(text.isBlank() ||
                        searchTopicsList[item].contains(text, ignoreCase = true)) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .clickable {
                                        text = searchTopicsList[item]
                                    },
                                horizontalArrangement = Arrangement.spacedBy(15.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                                    contentDescription = "Trending search",
                                    tint = searchColor
                                )
                                SearchText(
                                    text = searchTopicsList[item],
                                    color = searchColor
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                color = searchColor
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
private fun SearchText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        fontSize = 16.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
private fun SearchComponentPreview() {
    NewAppTheme {
        SearchComponent(
            searchTopics = remember {
                mutableStateListOf(
                    "All",
                    "Politic",
                    "Sport",
                    "Education",
                    "Gaming",
                    "World"
                )
            }
        )
    }
}