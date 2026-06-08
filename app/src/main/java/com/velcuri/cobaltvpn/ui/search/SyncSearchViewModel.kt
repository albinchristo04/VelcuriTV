package com.velcuri.cobaltvpn.ui.search

import com.velcuri.cobaltvpn.Score
import com.velcuri.cobaltvpn.SearchQuality
import com.velcuri.cobaltvpn.SearchResponse
import com.velcuri.cobaltvpn.TvType

//TODO Relevance of this class since it's not used
class SyncSearchViewModel {
    data class SyncSearchResultSearchResponse(
        override val name: String,
        override val url: String,
        override val apiName: String,
        override var type: TvType?,
        override var posterUrl: String?,
        override var id: Int?,
        override var quality: SearchQuality? = null,
        override var posterHeaders: Map<String, String>? = null,
        override var score: Score? = null,
    ) : SearchResponse
}