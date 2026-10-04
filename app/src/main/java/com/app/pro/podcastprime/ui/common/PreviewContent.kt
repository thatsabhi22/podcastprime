package com.app.pro.podcastprime.ui.common

import androidx.compose.runtime.Composable
import com.app.pro.podcastprime.ui.navigation.ProvideNavHostController
import com.app.pro.podcastprime.ui.theme.PodcastAppTheme
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun PreviewContent(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    PodcastAppTheme(darkTheme = darkTheme) {
        ProvideWindowInsets {
            ProvideNavHostController {
                content()
            }
        }
    }
}