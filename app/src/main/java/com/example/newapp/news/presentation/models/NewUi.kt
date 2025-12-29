package com.example.newapp.news.presentation.models

import androidx.core.text.HtmlCompat
import com.example.newapp.news.domain.New
import com.example.newapp.news.domain.Source
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class NewUi(
    val source: SourceUi,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)

data class SourceUi(
    val id: String? = "",
    val name: String
)

fun New.toNewUi(): NewUi {
    fun formatContent(rawContent: String): String {
        val normalized = rawContent
            .replace("\\r\\n", "\n")
            .replace("\\n", "\n")
            .replace("\\r", "\r")
            .replace("\\t", "\t")

        val noJs = normalized
            .replace(
                Regex("\\{\\s*window\\.open[\\s\\S]*?\\}\\s*,?\\s*\\d*\\);?", RegexOption.IGNORE_CASE),
                ""
            )
            .replace(
                Regex("return[\\s\\n\\r]*false", RegexOption.IGNORE_CASE),
                ""
            )

        val noHtml = HtmlCompat.fromHtml(
            noJs,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()

        val clean = noHtml
            .replace(Regex("\\[\\+\\d+ chars\\]"), "")
            .replace(Regex(">"), "")
            .replace(Regex("\\s{2,}"), " ")

        return clean
    }
    val formattedContent = formatContent(content)

    return NewUi(
        source = source.toSourceUi(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = formattedContent
    )
}

fun Source.toSourceUi(): SourceUi {
    return SourceUi(
        id = id,
        name = name
    )
}

fun String.toTimeAgo(): String {
    val zonedDateTime = ZonedDateTime.parse(this)
    val now = ZonedDateTime.now()
    val duration = Duration.between(zonedDateTime, now)
    val timeAgo = when {
        duration.toMinutes() < 2 -> "Hace unos segundos"
        duration.toMinutes() < 60 -> "Hace ${duration.toMinutes()} minutos"
        duration.toHours() < 2 -> "Hace 1 hora"
        duration.toHours() < 24 -> "Hace ${duration.toHours()} horas"
        duration.toDays() < 2 -> "Hace 1 día"
        duration.toDays() < 7 -> "Hace ${duration.toDays()} días"
        duration.toDays() < 14 -> "Hace 1 semana"
        duration.toDays() < 30 -> "Hace ${duration.toDays() / 7} semanas"
        duration.toDays() < 60 -> "Hace 1 mes"
        duration.toDays() < 365 -> "Hace ${duration.toDays() / 30} meses"
        duration.toDays() < 730 -> "Hace 1 año"
        else -> "Hace ${duration.toDays() / 365} años"
    }
    return timeAgo
}

fun String.toFormattedPublishedAt(): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    val formattedPublishedAt = ZonedDateTime.parse(this)
        .format(formatter)
        .replaceFirstChar { it.titlecaseChar() }
    return formattedPublishedAt
}