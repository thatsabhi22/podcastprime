package com.app.pro.podcastprime.domain.repository

import com.app.pro.podcastprime.data.datastore.PodcastDataStore
import com.app.pro.podcastprime.data.network.service.PodcastService
import com.app.pro.podcastprime.domain.model.PodcastSearch
import com.app.pro.podcastprime.error.Failure
import com.app.pro.podcastprime.util.Either
import com.app.pro.podcastprime.util.left
import com.app.pro.podcastprime.util.right

class PodcastRepositoryImpl(
    private val service: PodcastService,
    private val dataStore: PodcastDataStore
) : PodcastRepository {

    companion object {
        private const val TAG = "PodcastRepository"
    }

    override suspend fun searchPodcasts(
        query: String,
        type: String
    ): Either<Failure, PodcastSearch> {
        return try {
            val canFetchAPI = dataStore.canFetchAPI()
            if (canFetchAPI) {
                val result = service.searchPodcasts(query, type).asDomainModel()
                dataStore.storePodcastSearchResult(result)
                right(result)
            } else {
                right(dataStore.readLastPodcastSearchResult())
            }
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }
}