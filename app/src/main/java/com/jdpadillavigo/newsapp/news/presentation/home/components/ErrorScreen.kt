package com.jdpadillavigo.newsapp.news.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.SignalWifiStatusbarConnectedNoInternet4
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdpadillavigo.newsapp.core.domain.util.NetworkError
import com.jdpadillavigo.newsapp.core.presentation.util.toMessage
import com.jdpadillavigo.newsapp.news.presentation.new_list.NewListAction
import com.jdpadillavigo.newsapp.news.presentation.new_list.NewListState
import com.jdpadillavigo.newsapp.ui.theme.NewsAppTheme

@Composable
fun ErrorScreen(
    state: NewListState,
    onAction: (NewListAction) -> Unit,
    loadNews: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(state.errorMessage == NetworkError.NO_INTERNET.toMessage(context)) {
                ErrorIcon(
                    imageVector = Icons.Default.SignalWifiStatusbarConnectedNoInternet4,
                    contentDescription = "No internet"
                )
                ErrorText(NetworkError.NO_INTERNET.toMessage(context))
            } else {
                ErrorIcon(
                    imageVector = Icons.Default.ErrorOutline,
                    contentDescription = "Unknown error"
                )
                ErrorText(NetworkError.UNKNOWN.toMessage(context))
            }
            Button(
                onClick = { onAction(NewListAction.OnLoadClick(loadNews)) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = "Retry",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ErrorIcon(
    imageVector: ImageVector,
    contentDescription: String
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.size(90.dp)
    )
}

@Composable
private fun ErrorText(
    text: String
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 18.sp,
        textAlign = TextAlign.Center
    )
}

@PreviewLightDark
@Composable
private fun ErrorScreenPreview() {
    NewsAppTheme {
        ErrorScreen(
            state = NewListState(
                errorMessage = "Error"
            ),
            onAction = {},
            loadNews = "everything",
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
        )
    }
}