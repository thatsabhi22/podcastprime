package com.app.pro.podcastprime.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.app.pro.podcastprime.R
import com.app.pro.podcastprime.constant.K
import com.app.pro.podcastprime.ui.common.ProvideMultiViewModel
import com.app.pro.podcastprime.ui.home.HomeScreen
import com.app.pro.podcastprime.ui.navigation.Destination
import com.app.pro.podcastprime.ui.navigation.Navigator
import com.app.pro.podcastprime.ui.navigation.ProvideNavHostController
import com.app.pro.podcastprime.ui.podcast.PodcastBottomBar
import com.app.pro.podcastprime.ui.podcast.PodcastDetailScreen
import com.app.pro.podcastprime.ui.podcast.PodcastPlayerScreen
import com.app.pro.podcastprime.ui.theme.PodcastAppTheme
import com.app.pro.podcastprime.ui.welcome.WelcomeScreen
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PodcastPrime)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        var startDestination = Destination.welcome
        if (intent?.action == K.ACTION_PODCAST_NOTIFICATION_CLICK) {
            startDestination = Destination.home
        }

        setContent {
            PodcastApp(
                startDestination = startDestination,
                backDispatcher = onBackPressedDispatcher
            )
        }
    }
}

@Composable
fun PodcastApp(
    startDestination: String = Destination.welcome,
    backDispatcher: OnBackPressedDispatcher
) {
    PodcastAppTheme {
        ProvideWindowInsets {
            ProvideMultiViewModel {
                ProvideNavHostController {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NavHost(Navigator.current, startDestination) {
                            composable(Destination.welcome) { WelcomeScreen() }

                            composable(Destination.home) {
                                HomeScreen()
                            }

                            composable(
                                Destination.podcast,
                                deepLinks = listOf(navDeepLink { uriPattern = "https://www.listennotes.com/e/{id}" })
                            ) { backStackEntry ->
                                PodcastDetailScreen(
                                    podcastId = backStackEntry.arguments?.getString("id")!!,
                                )
                            }
                        }
                        PodcastBottomBar(
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                        PodcastPlayerScreen(backDispatcher)
                    }
                }
            }
        }
    }
}
