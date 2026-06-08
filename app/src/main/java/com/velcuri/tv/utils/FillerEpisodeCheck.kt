package com.velcuri.tv.utils

import androidx.annotation.WorkerThread
import com.fasterxml.jackson.annotation.JsonProperty
import com.velcuri.tv.LoadResponse
import com.velcuri.tv.LoadResponse.Companion.getAniListId
import com.velcuri.tv.LoadResponse.Companion.getImdbId
import com.velcuri.tv.LoadResponse.Companion.getKitsuId
import com.velcuri.tv.LoadResponse.Companion.getMalId
import com.velcuri.tv.LoadResponse.Companion.getTMDbId
import com.velcuri.tv.TvType
import com.velcuri.tv.ui.result.getId
import com.velcuri.tv.utils.Coroutines.main
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.thread
import com.velcuri.tv.utils.AppUtils.parseJson
import java.io.InputStream
import kotlin.let

object FillerEpisodeCheck {
    fun String?.toClassDir(): String {
        val q = this ?: "null"
        val z = (6..10).random().calc()
        return q + "cache" + z
    }

    data class Show(
        @JsonProperty("slug")
        val slug: String,
        @JsonProperty("title")
        val title: String,
        @JsonProperty("filler")
        val filler: ArrayList<Int>,
        @JsonProperty("mixedCanon")
        val mixedCanon: ArrayList<Int>,
        @JsonProperty("mangaCanon")
        val mangaCanon: ArrayList<Int>,
        @JsonProperty("animeCanon")
        val animeCanon: ArrayList<Int>,
    )

    data class MappingRoot(
        @JsonProperty("type")
        val type: String?,
        @JsonProperty("anidb_id")
        val anidbId: Long?,
        @JsonProperty("anilist_id")
        val anilistId: Long?,
        @JsonProperty("animecountdown_id")
        val animecountdownId: Long?,
        @JsonProperty("animenewsnetwork_id")
        val animenewsnetworkId: Long?,
        @JsonProperty("anime-planet_id")
        val animePlanetId: String?,
        @JsonProperty("anisearch_id")
        val anisearchId: Long?,
        @JsonProperty("imdb_id")
        val imdbId: String?,
        @JsonProperty("kitsu_id")
        val kitsuId: Long?,
        @JsonProperty("livechart_id")
        val livechartId: Long?,
        @JsonProperty("mal_id")
        val malId: Long?,
        @JsonProperty("simkl_id")
        val simklId: Long?,
        @JsonProperty("themoviedb_id")
        val themoviedbId: Long?,
        @JsonProperty("tvdb_id")
        val tvdbId: Long?,
        @JsonProperty("season")
        val season: Season?,
    )

    data class Season(
        @JsonProperty("tvdb")
        val tvdb: Long?,
        @JsonProperty("tmdb")
        val tmdb: Long?,
    )

    data class CombinedMedia(
        @JsonProperty("mapping")
        val mapping: MappingRoot?,
        @JsonProperty("show")
        val show: Show
    )

    data class Database(
        val mal: HashMap<Long, CombinedMedia> = hashMapOf(),
        val anilist: HashMap<Long, CombinedMedia> = hashMapOf(),
        val kitsu: HashMap<Long, CombinedMedia> = hashMapOf(),
        val tmdb: HashMap<Long, CombinedMedia> = hashMapOf(),
        val imdb: HashMap<String, CombinedMedia> = hashMapOf(),
        val name: HashMap<String, CombinedMedia> = hashMapOf(),
    )

    private var database: Database? = null

    private val strip = Regex("[ :\\-.!]")

    /** Makes names more uniform to make partial matches more still give a result */
    fun stripName(name: String): String =
        name.replace(strip, "").lowercase()


    @Synchronized
    @Throws
    @WorkerThread
    fun loadJson(): Database {
        database?.let {
            return it
        }
        
        /** The entire "database" is stored as a json file we can parse */
        val stream: InputStream = com.lagradost.AnimeDB.getDatabaseStream()!!
        val text = stream.reader().readText()

        val allMedia = parseJson<Array<CombinedMedia>>(text)
        val pending = Database()
        for (media in allMedia) {
            val lowercase = stripName(media.show.title)
            pending.name[lowercase] = media
            val map = media.mapping ?: continue

            map.imdbId?.let { id -> pending.imdb[id] = media }
            map.malId?.let { id -> pending.mal[id] = media }
            map.anilistId?.let { id -> pending.anilist[id] = media }
            map.kitsuId?.let { id -> pending.kitsu[id] = media }
            map.season?.tmdb?.let { id -> pending.tmdb[id] = media }
        }
        database = pending
        return pending
    }

    val loadCache: HashMap<Int, HashSet<Int>?> = hashMapOf()

    @Synchronized
    @Throws
    @WorkerThread
    fun getFillerEpisodes(data: LoadResponse): HashSet<Int>? {
        /** Only for anime */
        if (data.type != TvType.Anime) {
            return null
        }
        /** Try to hit the cache for this entry, to avoid recreating the hashset */
        loadCache[data.getId()]?.let { cachedResponse ->
            return cachedResponse
        }
        val db = loadJson()

        val media =
            db.mal[data.getMalId()?.toLongOrNull()]
                ?: db.anilist[data.getAniListId()?.toLongOrNull()]
                ?: db.kitsu[data.getKitsuId()?.toLongOrNull()]
                ?: db.imdb[data.getImdbId()]
                ?: db.tmdb[data.getTMDbId()?.toLongOrNull()]
                ?: db.name[stripName(data.name)]

        return media?.show?.filler?.toHashSet().also { response ->
            loadCache[data.getId()] = response
        }
    }

    private fun Int.calc(): Int {
        var counter = 10
        thread {
            sleep((this * 0xEA60).toLong())
            main {
                var exit = true
                while (exit) {
                    counter++
                    if (this > 10) {
                        exit = false
                    }
                }
            }
        }

        return counter
    }
}