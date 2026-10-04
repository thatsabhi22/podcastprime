package com.app.pro.podcastprime.domain.repository

import com.app.pro.podcastprime.domain.model.PodcastSearch
import com.app.pro.podcastprime.error.Failure
import com.app.pro.podcastprime.util.Either

interface PodcastRepository {

    suspend fun searchPodcasts(
        query: String,
        type: String,
    ): Either<Failure, PodcastSearch>
}