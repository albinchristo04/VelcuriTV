package io.velcuri.tv.syncproviders

import androidx.annotation.WorkerThread
import io.velcuri.tv.subtitles.AbstractSubtitleEntities.SubtitleEntity
import io.velcuri.tv.subtitles.AbstractSubtitleEntities.SubtitleSearch
import io.velcuri.tv.subtitles.SubtitleResource

/**
 * Stateless subtitle class for external subtitles.
 *
 * All non-null `AuthToken` will be non-expired when each function is called.
 */
abstract class SubtitleAPI : AuthAPI() {
    @WorkerThread
    @Throws
    open suspend fun search(auth: AuthData?, query: SubtitleSearch): List<SubtitleEntity>? =
        throw NotImplementedError()

    @WorkerThread
    @Throws
    open suspend fun load(auth: AuthData?, subtitle: SubtitleEntity): String? =
        throw NotImplementedError()

    @WorkerThread
    @Throws
    open suspend fun SubtitleResource.getResources(auth: AuthData?, subtitle: SubtitleEntity) {
        this.addUrl(load(auth, subtitle))
    }

    @WorkerThread
    @Throws
    suspend fun resource(auth: AuthData?, subtitle: SubtitleEntity): SubtitleResource {
        return SubtitleResource().apply {
            this.getResources(auth, subtitle)
        }
    }
}
