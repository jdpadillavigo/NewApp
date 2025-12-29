package com.example.newapp.news.presentation.new_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newapp.news.presentation.models.NewUi
import com.example.newapp.news.presentation.models.SourceUi
import com.example.newapp.news.presentation.models.toFormattedPublishedAt
import com.example.newapp.ui.theme.NewAppTheme

@Composable
fun NewListItem(
    newUi: NewUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(95.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if(newUi.urlToImage != "") {
            AsyncImage(
                newUi.urlToImage,
                contentDescription = "New's image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(15.dp))
            )
        } else {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = "New's image",
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .fillMaxHeight(),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Column {
            Text(
                text = newUi.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                lineHeight = 30.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                if(newUi.author != "") {
                    TextInfo(
                        modifier = Modifier.fillMaxWidth(0.35f),
                        text = newUi.author
                    )
                    if(newUi.publishedAt != "") {
                        TextInfo(text = "  •  ")
                    }
                }
                TextInfo(text = newUi.publishedAt.toFormattedPublishedAt())
            }
        }
    }
}

@Composable
fun TextInfo(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        fontSize = 18.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
fun NewListItemPreview() {
    NewAppTheme {
        NewListItem(
            newUi = previewNew,
            onClick = {},
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
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