package com.app.pro.podcastprime.ui.common

import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.text.HtmlCompat

@Composable
fun EmphasisText(
    text: String,
    modifier: Modifier = Modifier,
    contentAlpha: Float = ContentAlpha.medium,
    style: TextStyle = MaterialTheme.typography.body2,
) {
    val annotatedString = remember(text) { parseHtmlToAnnotatedString(text) }

    CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
        Text(
            text = annotatedString,
            modifier = modifier,
            style = style,
        )
    }
}

fun parseHtmlToAnnotatedString(html: String): AnnotatedString {
    if (html.isEmpty()) return AnnotatedString("")
    val spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    val plainText = spanned.toString().trimEnd()

    return buildAnnotatedString {
        append(plainText)

        val length = plainText.length
        if (length == 0) return@buildAnnotatedString

        val styleSpans = spanned.getSpans(0, spanned.length, StyleSpan::class.java)
        for (span in styleSpans) {
            val start = spanned.getSpanStart(span).coerceIn(0, length)
            val end = spanned.getSpanEnd(span).coerceIn(0, length)
            if (start < end) {
                when (span.style) {
                    Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                    Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                    Typeface.BOLD_ITALIC -> addStyle(
                        SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic),
                        start,
                        end
                    )
                }
            }
        }

        val underlineSpans = spanned.getSpans(0, spanned.length, UnderlineSpan::class.java)
        for (span in underlineSpans) {
            val start = spanned.getSpanStart(span).coerceIn(0, length)
            val end = spanned.getSpanEnd(span).coerceIn(0, length)
            if (start < end) {
                addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
            }
        }

        val strikethroughSpans = spanned.getSpans(0, spanned.length, StrikethroughSpan::class.java)
        for (span in strikethroughSpans) {
            val start = spanned.getSpanStart(span).coerceIn(0, length)
            val end = spanned.getSpanEnd(span).coerceIn(0, length)
            if (start < end) {
                addStyle(SpanStyle(textDecoration = TextDecoration.LineThrough), start, end)
            }
        }

        val foregroundColorSpans = spanned.getSpans(0, spanned.length, ForegroundColorSpan::class.java)
        for (span in foregroundColorSpans) {
            val start = spanned.getSpanStart(span).coerceIn(0, length)
            val end = spanned.getSpanEnd(span).coerceIn(0, length)
            if (start < end) {
                addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
            }
        }

        val urlSpans = spanned.getSpans(0, spanned.length, URLSpan::class.java)
        for (span in urlSpans) {
            val start = spanned.getSpanStart(span).coerceIn(0, length)
            val end = spanned.getSpanEnd(span).coerceIn(0, length)
            if (start < end) {
                addLink(
                    url = LinkAnnotation.Url(
                        url = span.url,
                        styles = TextLinkStyles(
                            style = SpanStyle(
                                color = Color(0xFF1E88E5),
                                textDecoration = TextDecoration.Underline
                            )
                        )
                    ),
                    start = start,
                    end = end,
                )
            }
        }
    }
}
