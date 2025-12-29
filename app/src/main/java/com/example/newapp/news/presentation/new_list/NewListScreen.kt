package com.example.newapp.news.presentation.new_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.core.domain.util.NetworkError
import com.example.newapp.core.presentation.util.toMessage
import com.example.newapp.news.presentation.models.NewUi
import com.example.newapp.news.presentation.models.SourceUi
import com.example.newapp.news.presentation.new_list.components.NewListItem
import com.example.newapp.ui.theme.NewAppTheme

@Composable
fun NewListScreen(
    state: NewListState,
    onAction: (NewListAction) -> Unit,
    loadNews: String,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(loadNews) {
        onAction(NewListAction.OnRetryClick(loadNews))
    }

    if(state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if(state.errorMessage != null) {
        val context = LocalContext.current

        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = NetworkError.UNKNOWN.toMessage(context),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { onAction(NewListAction.OnRetryClick(loadNews)) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = "Reintentar",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    } else if(state.news.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No se encontraron noticias.",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(state.news) { newUi ->
                NewListItem(
                    newUi = newUi,
                    onClick = {
                        onAction(NewListAction.OnNewClick(newUi))
                    }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun NewListScreenPreview() {
    NewAppTheme {
        NewListScreen(
            state = NewListState(
                news = (1..100).map {
                    previewNew
                }
            ),
            onAction = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface),
            loadNews = "everything"
        )
    }
}

internal val previewSource = SourceUi(
    id = "the-verge",
    name = "The Verge"
)

internal val previewNew = NewUi(
    source = previewSource,
    author = "David Pierce",
    title = "What Training Do Voleyball Players Need? What Training Do Voleyball Players Need?",
    description = "Hi, friends! Welcome to Installer No. 110, your guide to the best and Verge-iest stuff in the world. (If you're new here, welcome, happy holidays, and also you can read all the old editions at the Installer homepage.) This week, I've been reading about mall S…",
    url = "https://www.theverge.com/tech/848567/best-tech-movies-gadgets-2025-installer",
    urlToImage = "https://platform.theverge.com/wp-content/uploads/sites/2/2025/12/Installer-110.png?quality=90&strip=all&crop=0%2C10.711631919237%2C100%2C78.576736161526&w=1200",
    publishedAt = "2025-12-20T18:56:13Z",
    content = "As a tech department, we're usually pretty good at spotting tech that's out of the ordinary. During <ul><li></li><li></li><li></li></ul>\r\nIn this weeks Installer: All the things we loved to watch, read, play, build, and goof around with this year.\r\nIn this weeks Installer: All the things we loved t… [+15418 chars]"
)