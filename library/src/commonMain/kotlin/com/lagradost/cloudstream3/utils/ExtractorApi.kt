@file:OptIn(ExperimentalUuidApi::class)

package com.velcuri.tv.utils

import com.fasterxml.jackson.annotation.JsonIgnore
import com.velcuri.tv.AudioFile
import com.velcuri.tv.IDownloadableMinimum
import com.velcuri.tv.Prerelease
import com.velcuri.tv.SubtitleFile
import com.velcuri.tv.USER_AGENT
import com.velcuri.tv.app
import com.velcuri.tv.extractors.Acefile
import com.velcuri.tv.extractors.Ahvsh
import com.velcuri.tv.extractors.Aico
import com.velcuri.tv.extractors.Asnwish
import com.velcuri.tv.extractors.Auvexiug
import com.velcuri.tv.extractors.Awish
import com.velcuri.tv.extractors.BgwpCC
import com.velcuri.tv.extractors.BigwarpArt
import com.velcuri.tv.extractors.BigwarpIO
import com.velcuri.tv.extractors.Blogger
import com.velcuri.tv.extractors.ByseSX
import com.velcuri.tv.extractors.Bysezejataos
import com.velcuri.tv.extractors.ByseBuho
import com.velcuri.tv.extractors.ByseVepoin
import com.velcuri.tv.extractors.ByseQekaho
import com.velcuri.tv.extractors.Cavanhabg
import com.velcuri.tv.extractors.Cda
import com.velcuri.tv.extractors.Cdnplayer
import com.velcuri.tv.extractors.CdnwishCom
import com.velcuri.tv.extractors.CloudMailRu
import com.velcuri.tv.extractors.ContentX
import com.velcuri.tv.extractors.CsstOnline
import com.velcuri.tv.extractors.D0000d
import com.velcuri.tv.extractors.D000dCom
import com.velcuri.tv.extractors.DBfilm
import com.velcuri.tv.extractors.Dailymotion
import com.velcuri.tv.extractors.DatabaseGdrive
import com.velcuri.tv.extractors.DatabaseGdrive2
import com.velcuri.tv.extractors.DesuArcg
import com.velcuri.tv.extractors.DesuDrive
import com.velcuri.tv.extractors.DesuOdchan
import com.velcuri.tv.extractors.DesuOdvip
import com.velcuri.tv.extractors.Dhcplay
import com.velcuri.tv.extractors.Dhtpre
import com.velcuri.tv.extractors.Dokicloud
import com.velcuri.tv.extractors.DoodCxExtractor
import com.velcuri.tv.extractors.DoodLaExtractor
import com.velcuri.tv.extractors.DoodPmExtractor
import com.velcuri.tv.extractors.DoodShExtractor
import com.velcuri.tv.extractors.DoodSoExtractor
import com.velcuri.tv.extractors.DoodToExtractor
import com.velcuri.tv.extractors.DoodWatchExtractor
import com.velcuri.tv.extractors.DoodWfExtractor
import com.velcuri.tv.extractors.DoodWsExtractor
import com.velcuri.tv.extractors.DoodYtExtractor
import com.velcuri.tv.extractors.Doodspro
import com.velcuri.tv.extractors.Dsvplay
import com.velcuri.tv.extractors.Doodporn
import com.velcuri.tv.extractors.DoodstreamCom
import com.velcuri.tv.extractors.Dooood
import com.velcuri.tv.extractors.Ds2play
import com.velcuri.tv.extractors.Ds2video
import com.velcuri.tv.extractors.DsstOnline
import com.velcuri.tv.extractors.Dumbalag
import com.velcuri.tv.extractors.Dwish
import com.velcuri.tv.extractors.Embedgram
import com.velcuri.tv.extractors.EmturbovidExtractor
import com.velcuri.tv.extractors.Evoload
import com.velcuri.tv.extractors.Evoload1
import com.velcuri.tv.extractors.Ewish
import com.velcuri.tv.extractors.FEmbed
import com.velcuri.tv.extractors.FEnet
import com.velcuri.tv.extractors.Fastream
import com.velcuri.tv.extractors.FeHD
import com.velcuri.tv.extractors.Fembed9hd
import com.velcuri.tv.extractors.FileMoon
import com.velcuri.tv.extractors.FileMoonIn
import com.velcuri.tv.extractors.FileMoonSx
import com.velcuri.tv.extractors.FilemoonV2
import com.velcuri.tv.extractors.Filesim
import com.velcuri.tv.extractors.Multimoviesshg
import com.velcuri.tv.extractors.FlaswishCom
import com.velcuri.tv.extractors.FourCX
import com.velcuri.tv.extractors.FourPichive
import com.velcuri.tv.extractors.FourPlayRu
import com.velcuri.tv.extractors.Fplayer
import com.velcuri.tv.extractors.FsstOnline
import com.velcuri.tv.extractors.GDMirrorbot
import com.velcuri.tv.extractors.GUpload
import com.velcuri.tv.extractors.GamoVideo
import com.velcuri.tv.extractors.Gdriveplayer
import com.velcuri.tv.extractors.Gdriveplayerapi
import com.velcuri.tv.extractors.Gdriveplayerapp
import com.velcuri.tv.extractors.Gdriveplayerbiz
import com.velcuri.tv.extractors.Gdriveplayerco
import com.velcuri.tv.extractors.Gdriveplayerfun
import com.velcuri.tv.extractors.Gdriveplayerio
import com.velcuri.tv.extractors.Gdriveplayerme
import com.velcuri.tv.extractors.Gdriveplayerorg
import com.velcuri.tv.extractors.Gdriveplayerus
import com.velcuri.tv.extractors.Geodailymotion
import com.velcuri.tv.extractors.Gofile
import com.velcuri.tv.extractors.GoodstreamExtractor
import com.velcuri.tv.extractors.Guccihide
import com.velcuri.tv.extractors.Guxhag
import com.velcuri.tv.extractors.HDMomPlayer
import com.velcuri.tv.extractors.HDPlayerSystem
import com.velcuri.tv.extractors.HDStreamAble
import com.velcuri.tv.extractors.Habetar
import com.velcuri.tv.extractors.Haxloppd
import com.velcuri.tv.extractors.Hgcloudto
import com.velcuri.tv.extractors.HglinkTo
import com.velcuri.tv.extractors.HgplayCDN
import com.velcuri.tv.extractors.Hotlinger
import com.velcuri.tv.extractors.HubCloud
import com.velcuri.tv.extractors.Hxfile
import com.velcuri.tv.extractors.HlsWish
import com.velcuri.tv.extractors.InternetArchive
import com.velcuri.tv.extractors.JWPlayer
import com.velcuri.tv.extractors.Jeniusplay
import com.velcuri.tv.extractors.Jodwish
import com.velcuri.tv.extractors.Keephealth
import com.velcuri.tv.extractors.KotakAnimeid
import com.velcuri.tv.extractors.Kotakajair
import com.velcuri.tv.extractors.Krakenfiles
import com.velcuri.tv.extractors.Kswplayer
import com.velcuri.tv.extractors.LayarKaca
import com.velcuri.tv.extractors.Linkbox
import com.velcuri.tv.extractors.LuluStream
import com.velcuri.tv.extractors.Lulustream1
import com.velcuri.tv.extractors.Lulustream2
import com.velcuri.tv.extractors.Luluvdoo
import com.velcuri.tv.extractors.Luxubu
import com.velcuri.tv.extractors.Lvturbo
import com.velcuri.tv.extractors.MailRu
import com.velcuri.tv.extractors.Maxstream
import com.velcuri.tv.extractors.Mediafire
import com.velcuri.tv.extractors.Megacloud
import com.velcuri.tv.extractors.Meownime
import com.velcuri.tv.extractors.MetaGnathTuggers
import com.velcuri.tv.extractors.MixDrop
import com.velcuri.tv.extractors.MixDropAg
import com.velcuri.tv.extractors.MixDropBz
import com.velcuri.tv.extractors.MixDropCh
import com.velcuri.tv.extractors.MixDropTo
import com.velcuri.tv.extractors.MixDropPs
import com.velcuri.tv.extractors.Mdy
import com.velcuri.tv.extractors.MixDropSi
import com.velcuri.tv.extractors.MxDropTo
import com.velcuri.tv.extractors.Movhide
import com.velcuri.tv.extractors.Moviehab
import com.velcuri.tv.extractors.MoviehabNet
import com.velcuri.tv.extractors.Moviesm4u
import com.velcuri.tv.extractors.Mp4Upload
import com.velcuri.tv.extractors.Multimovies
import com.velcuri.tv.extractors.Mvidoo
import com.velcuri.tv.extractors.MyVidPlay
import com.velcuri.tv.extractors.Mwish
import com.velcuri.tv.extractors.NathanFromSubject
import com.velcuri.tv.extractors.Nekostream
import com.velcuri.tv.extractors.Nekowish
import com.velcuri.tv.extractors.Neonime7n
import com.velcuri.tv.extractors.Neonime8n
import com.velcuri.tv.extractors.Obeywish
import com.velcuri.tv.extractors.Odnoklassniki
import com.velcuri.tv.extractors.OkRuHTTP
import com.velcuri.tv.extractors.OkRuHTTPMobile
import com.velcuri.tv.extractors.OkRuSSL
import com.velcuri.tv.extractors.OkRuSSLMobile
import com.velcuri.tv.extractors.PeaceMakerst
import com.velcuri.tv.extractors.Peytonepre
import com.velcuri.tv.extractors.Pichive
import com.velcuri.tv.extractors.PixelDrain
import com.velcuri.tv.extractors.PixelDrainDev
import com.velcuri.tv.extractors.PlayLtXyz
import com.velcuri.tv.extractors.PlayRu
import com.velcuri.tv.extractors.PlayerVoxzer
import com.velcuri.tv.extractors.Playerwish
import com.velcuri.tv.extractors.Playmogo
import com.velcuri.tv.extractors.Rabbitstream
import com.velcuri.tv.extractors.RapidVid
import com.velcuri.tv.extractors.Rasacintaku
import com.velcuri.tv.extractors.SBfull
import com.velcuri.tv.extractors.Sbasian
import com.velcuri.tv.extractors.Sbface
import com.velcuri.tv.extractors.Sbflix
import com.velcuri.tv.extractors.Sblona
import com.velcuri.tv.extractors.Sblongvu
import com.velcuri.tv.extractors.Sbnet
import com.velcuri.tv.extractors.Sbrapid
import com.velcuri.tv.extractors.Sbsonic
import com.velcuri.tv.extractors.Sbspeed
import com.velcuri.tv.extractors.Sbthe
import com.velcuri.tv.extractors.SecvideoOnline
import com.velcuri.tv.extractors.Sendvid
import com.velcuri.tv.extractors.Server1uns
import com.velcuri.tv.extractors.SfastwishCom
import com.velcuri.tv.extractors.ShaveTape
import com.velcuri.tv.extractors.SibNet
import com.velcuri.tv.extractors.Simpulumlamerop
import com.velcuri.tv.extractors.Smoothpre
import com.velcuri.tv.extractors.Sobreatsesuyp
import com.velcuri.tv.extractors.Ssbstream
import com.velcuri.tv.extractors.StreamEmbed
import com.velcuri.tv.extractors.StreamHLS
import com.velcuri.tv.extractors.StreamM4u
import com.velcuri.tv.extractors.StreamSB
import com.velcuri.tv.extractors.StreamSB1
import com.velcuri.tv.extractors.StreamSB10
import com.velcuri.tv.extractors.StreamSB11
import com.velcuri.tv.extractors.StreamSB2
import com.velcuri.tv.extractors.StreamSB3
import com.velcuri.tv.extractors.StreamSB4
import com.velcuri.tv.extractors.StreamSB5
import com.velcuri.tv.extractors.StreamSB6
import com.velcuri.tv.extractors.StreamSB7
import com.velcuri.tv.extractors.StreamSB8
import com.velcuri.tv.extractors.StreamSB9
import com.velcuri.tv.extractors.StreamSilk
import com.velcuri.tv.extractors.StreamTape
import com.velcuri.tv.extractors.StreamTapeNet
import com.velcuri.tv.extractors.StreamTapeXyz
import com.velcuri.tv.extractors.Watchadsontape
import com.velcuri.tv.extractors.StreamWishExtractor
import com.velcuri.tv.extractors.StreamhideCom
import com.velcuri.tv.extractors.StreamhideTo
import com.velcuri.tv.extractors.Streamhub2
import com.velcuri.tv.extractors.Streamix
import com.velcuri.tv.extractors.Streamlare
import com.velcuri.tv.extractors.StreamoUpload
import com.velcuri.tv.extractors.Streamplay
import com.velcuri.tv.extractors.Streamsss
import com.velcuri.tv.extractors.Streamup
import com.velcuri.tv.extractors.Streamwish2
import com.velcuri.tv.extractors.Strwish
import com.velcuri.tv.extractors.Strwish2
import com.velcuri.tv.extractors.Supervideo
import com.velcuri.tv.extractors.Swdyu
import com.velcuri.tv.extractors.Swhoi
import com.velcuri.tv.extractors.TRsTX
import com.velcuri.tv.extractors.Tantifilm
import com.velcuri.tv.extractors.TauVideo
import com.velcuri.tv.extractors.Techinmind
import com.velcuri.tv.extractors.Tubeless
import com.velcuri.tv.extractors.Uasopt
import com.velcuri.tv.extractors.Up4FunTop
import com.velcuri.tv.extractors.Up4Stream
import com.velcuri.tv.extractors.Upstream
import com.velcuri.tv.extractors.UpstreamExtractor
import com.velcuri.tv.extractors.Uqload
import com.velcuri.tv.extractors.Uqload1
import com.velcuri.tv.extractors.Uqload2
import com.velcuri.tv.extractors.Uqloadcx
import com.velcuri.tv.extractors.Uqloadbz
import com.velcuri.tv.extractors.UqloadsXyz
import com.velcuri.tv.extractors.Urochsunloath
import com.velcuri.tv.extractors.Userload
import com.velcuri.tv.extractors.Userscloud
import com.velcuri.tv.extractors.Uservideo
import com.velcuri.tv.extractors.Videa
import com.velcuri.tv.extractors.Vicloud
import com.velcuri.tv.extractors.VidHidePro
import com.velcuri.tv.extractors.VidHidePro1
import com.velcuri.tv.extractors.VidHidePro2
import com.velcuri.tv.extractors.VidHidePro3
import com.velcuri.tv.extractors.VidHidePro4
import com.velcuri.tv.extractors.VidHidePro5
import com.velcuri.tv.extractors.VidHidePro6
import com.velcuri.tv.extractors.VidHideHub
import com.velcuri.tv.extractors.Ryderjet
import com.velcuri.tv.extractors.VidMoxy
import com.velcuri.tv.extractors.VidStack
import com.velcuri.tv.extractors.VideoSeyred
import com.velcuri.tv.extractors.Videzz
import com.velcuri.tv.extractors.Vidgomunime
import com.velcuri.tv.extractors.Vidgomunimesb
import com.velcuri.tv.extractors.VidhideExtractor
import com.velcuri.tv.extractors.Vidmoly
import com.velcuri.tv.extractors.Vidmolyme
import com.velcuri.tv.extractors.Vidmolyto
import com.velcuri.tv.extractors.Vidmolybiz
import com.velcuri.tv.extractors.Vido
import com.velcuri.tv.extractors.Vidoza
import com.velcuri.tv.extractors.VinovoSi
import com.velcuri.tv.extractors.VinovoTo
import com.velcuri.tv.extractors.VidNest
import com.velcuri.tv.extractors.Vidara
import com.velcuri.tv.extractors.Vide0Net
import com.velcuri.tv.extractors.Vidsonic
import com.velcuri.tv.extractors.VkExtractor
import com.velcuri.tv.extractors.Voe
import com.velcuri.tv.extractors.Voe1
import com.velcuri.tv.extractors.Voe2
import com.velcuri.tv.extractors.Vtbe
import com.velcuri.tv.extractors.Wibufile
import com.velcuri.tv.extractors.WishembedPro
import com.velcuri.tv.extractors.Wishfast
import com.velcuri.tv.extractors.Wishonly
import com.velcuri.tv.extractors.XStreamCdn
import com.velcuri.tv.extractors.Xenolyzb
import com.velcuri.tv.extractors.Yipsu
import com.velcuri.tv.extractors.YourUpload
import com.velcuri.tv.extractors.YoutubeExtractor
import com.velcuri.tv.extractors.YoutubeMobileExtractor
import com.velcuri.tv.extractors.YoutubeNoCookieExtractor
import com.velcuri.tv.extractors.YoutubeShortLinkExtractor
import com.velcuri.tv.extractors.Yufiles
import com.velcuri.tv.extractors.Yuguaab
import com.velcuri.tv.extractors.Zplayer
import com.velcuri.tv.extractors.ZplayerV2
import com.velcuri.tv.extractors.Ztreamhub
import com.velcuri.tv.mvvm.logError
import com.velcuri.tv.utils.Coroutines.atomicListOf
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import org.jsoup.Jsoup
import java.net.URI
import kotlin.coroutines.cancellation.CancellationException
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

/**
 * For use in the ConcatenatingMediaSource.
 * If features are missing (headers), please report and we can add it.
 * @param durationUs use Long.toUs() for easier input
 * */
data class PlayListItem(
    val url: String,
    val durationUs: Long,
)

/**
 * Converts Seconds to MicroSeconds, multiplication by 1_000_000
 * */
fun Long.toUs(): Long {
    return this * 1_000_000
}

/**
 * If your site has an unorthodox m3u8-like system where there are multiple smaller videos concatenated
 * use this.
 * */
@Suppress("DEPRECATION")
data class ExtractorLinkPlayList(
    override val source: String,
    override val name: String,
    val playlist: List<PlayListItem>,
    override var referer: String,
    override var quality: Int,
    override var headers: Map<String, String> = mapOf(),
    /** Used for getExtractorVerifierJob() */
    override var extractorData: String? = null,
    override var type: ExtractorLinkType,
    override var audioTracks: List<AudioFile> = emptyList(),
) : ExtractorLink(
    source = source,
    name = name,
    url = "",
    referer = referer,
    quality = quality,
    headers = headers,
    extractorData = extractorData,
    type = type,
    audioTracks = audioTracks
) {
    constructor(
        source: String,
        name: String,
        playlist: List<PlayListItem>,
        referer: String,
        quality: Int,
        isM3u8: Boolean = false,
        headers: Map<String, String> = mapOf(),
        extractorData: String? = null,
    ) : this(
        source = source,
        name = name,
        playlist = playlist,
        referer = referer,
        quality = quality,
        type = if (isM3u8) ExtractorLinkType.M3U8 else ExtractorLinkType.VIDEO,
        headers = headers,
        extractorData = extractorData,
    )
}

/** Metadata about the file type used for downloads and exoplayer hint,
 * if you respond with the wrong one the file will fail to download or be played */
enum class ExtractorLinkType {
    /** Single stream of bytes no matter the actual file type */
    VIDEO,

    /** Split into several .ts files, has support for encrypted m3u8s */
    M3U8,

    /** Like m3u8 but uses xml, currently no download support */
    DASH,

    /** No support at the moment */
    TORRENT,

    /** No support at the moment */
    MAGNET;

    // See https://www.iana.org/assignments/media-types/media-types.xhtml
    fun getMimeType(): String {
        return when (this) {
            VIDEO -> "video/mp4"
            M3U8 -> "application/x-mpegURL"
            DASH -> "application/dash+xml"
            TORRENT -> "application/x-bittorrent"
            MAGNET -> "application/x-bittorrent"
        }
    }
}

private fun inferTypeFromUrl(url: String): ExtractorLinkType {
    val path = try {
        URI(url).path
    } catch (_: Throwable) {
        // don't log magnet links as errors
        null
    }
    return when {
        path?.endsWith(".m3u8") == true -> ExtractorLinkType.M3U8
        path?.endsWith(".mpd") == true -> ExtractorLinkType.DASH
        path?.endsWith(".torrent") == true -> ExtractorLinkType.TORRENT
        url.startsWith("magnet:") -> ExtractorLinkType.MAGNET
        else -> ExtractorLinkType.VIDEO
    }
}

val INFER_TYPE: ExtractorLinkType? = null

/**
 * [Uuid] for the ClearKey DRM scheme.
 *
 *
 * ClearKey is supported on Android devices running Android 5.0 (API Level 21) and up.
 */
@Prerelease
val CLEARKEY_DRM_UUID = Uuid.fromLongs(-0x1d8e62a7567a4c37L, 0x781AB030AF78D30EL)

/**
 * [Uuid] for the Widevine DRM scheme.
 *
 *
 * Widevine is supported on Android devices running Android 4.3 (API Level 18) and up.
 */
@Prerelease
val WIDEVINE_DRM_UUID = Uuid.fromLongs(-0x121074568629b532L, -0x5c37d8232ae2de13L)

/**
 * [Uuid] for the PlayReady DRM scheme.
 *
 *
 * PlayReady is supported on all AndroidTV devices. Note that most other Android devices do not
 * provide PlayReady support.
 */
@Prerelease
val PLAYREADY_DRM_UUID = Uuid.fromLongs(-0x65fb0f8667bfbd7aL, -0x546d19a41f77a06bL)

// Deprecate after next stable

// @Deprecated("Use CLEARKEY_DRM_UUID", ReplaceWith("CLEARKEY_DRM_UUID"), level = DeprecationLevel.WARNING)
val CLEARKEY_UUID = CLEARKEY_DRM_UUID.toJavaUuid()

// @Deprecated("Use WIDEVINE_DRM_UUID", ReplaceWith("WIDEVINE_DRM_UUID"), level = DeprecationLevel.WARNING)
val WIDEVINE_UUID = WIDEVINE_DRM_UUID.toJavaUuid()

// @Deprecated("Use PLAYREADY_DRM_UUID", ReplaceWith("PLAYREADY_DRM_UUID"), level = DeprecationLevel.WARNING)
val PLAYREADY_UUID = PLAYREADY_DRM_UUID.toJavaUuid()

suspend fun newExtractorLink(
    source: String,
    name: String,
    url: String,
    type: ExtractorLinkType? = null,
    initializer: suspend ExtractorLink.() -> Unit = { }
): ExtractorLink {

    @Suppress("DEPRECATION_ERROR")
    val builder =
        ExtractorLink(
            source = source,
            name = name,
            url = url,
            type = type ?: INFER_TYPE
        )

    builder.initializer()
    return builder
}

// Deprecate after next stable
/* @Deprecated(
    message = "Use Kotlin Uuid (kotlin.uuid.Uuid) instead of Java UUID.",
    level = DeprecationLevel.WARNING,
) */
suspend fun newDrmExtractorLink(
    source: String,
    name: String,
    url: String,
    type: ExtractorLinkType? = null,
    uuid: java.util.UUID,
    initializer: suspend DrmExtractorLink.() -> Unit = { }
): DrmExtractorLink {
    @Suppress("DEPRECATION_ERROR")
    val builder =
        DrmExtractorLink(
            source = source,
            name = name,
            url = url,
            uuid = uuid.toKotlinUuid(),
            type = type ?: INFER_TYPE
        )

    builder.initializer()
    return builder
}

@Prerelease
suspend fun newDrmExtractorLink(
    source: String,
    name: String,
    url: String,
    type: ExtractorLinkType? = null,
    uuid: Uuid,
    initializer: suspend DrmExtractorLink.() -> Unit = {},
): DrmExtractorLink {
    @Suppress("DEPRECATION_ERROR")
    val builder =
        DrmExtractorLink(
            source = source,
            name = name,
            url = url,
            uuid = uuid,
            type = type ?: INFER_TYPE
        )

    builder.initializer()
    return builder
}

/** Class holds extracted DRM media info to be passed to the player.
 * @property source Name of the media source, appears on player layout.
 * @property name Title of the media, appears on player layout.
 * @property url Url string of media file
 * @property referer Referer that will be used by network request.
 * @property quality Quality of the media file
 * @property headers Headers <String, String> map that will be used by network request.
 * @property extractorData Used for getExtractorVerifierJob()
 * @property type the type of the media, use [INFER_TYPE] if you want to auto infer the type from the url
 * @property kid  Base64 value of The KID element (Key Id) contains the identifier of the key associated with a license.
 * @property key Base64 value of Key to be used to decrypt the media file.
 * @property uuid Drm [Uuid] [WIDEVINE_DRM_UUID], [PLAYREADY_DRM_UUID], [CLEARKEY_DRM_UUID] (by default) .. etc
 * @property kty Key type "oct" (octet sequence) by default
 * @property keyRequestParameters Parameters that will used to request the key.
 * @see newDrmExtractorLink
 * */
@Suppress("DEPRECATION")
open class DrmExtractorLink private constructor(
    override val source: String,
    override val name: String,
    override val url: String,
    override var referer: String,
    override var quality: Int,
    override var headers: Map<String, String> = mapOf(),
    /** Used for getExtractorVerifierJob() */
    override var extractorData: String? = null,
    override var type: ExtractorLinkType,
    open var kid: String? = null,
    open var key: String? = null,
    open var uuid: Uuid,
    open var kty: String? = null,
    open var keyRequestParameters: HashMap<String, String>,
    open var licenseUrl: String? = null,
    override var audioTracks: List<AudioFile> = emptyList(),
) : ExtractorLink(
    source, name, url, referer, quality, headers, extractorData, type, audioTracks
) {
    @Deprecated("Use newDrmExtractorLink", level = DeprecationLevel.ERROR)
    constructor(
        source: String,
        name: String,
        url: String,
        referer: String? = null,
        quality: Int? = null,
        /** the type of the media, use INFER_TYPE if you want to auto infer the type from the url */
        type: ExtractorLinkType? = INFER_TYPE,
        headers: Map<String, String> = mapOf(),
        /** Used for getExtractorVerifierJob() */
        extractorData: String? = null,
        kid: String? = null,
        key: String? = null,
        uuid: Uuid = CLEARKEY_DRM_UUID,
        kty: String? = "oct",
        keyRequestParameters: HashMap<String, String> = hashMapOf(),
        licenseUrl: String? = null,
    ) : this(
        source = source,
        name = name,
        url = url,
        referer = referer ?: "",
        quality = quality ?: Qualities.Unknown.value,
        headers = headers,
        extractorData = extractorData,
        type = type ?: inferTypeFromUrl(url),
        kid = kid,
        key = key,
        uuid = uuid,
        keyRequestParameters = keyRequestParameters,
        kty = kty,
        licenseUrl = licenseUrl,
    )

    @Deprecated("Use newDrmExtractorLink", level = DeprecationLevel.ERROR)
    constructor(
        source: String,
        name: String,
        url: String,
        referer: String,
        quality: Int,
        /** the type of the media, use INFER_TYPE if you want to auto infer the type from the url */
        type: ExtractorLinkType?,
        headers: Map<String, String> = mapOf(),
        /** Used for getExtractorVerifierJob() */
        extractorData: String? = null,
        kid: String? = null,
        key: String? = null,
        uuid: Uuid = CLEARKEY_DRM_UUID,
        kty: String? = "oct",
        keyRequestParameters: HashMap<String, String> = hashMapOf(),
        licenseUrl: String? = null,
    ) : this(
        source = source,
        name = name,
        url = url,
        referer = referer,
        quality = quality,
        headers = headers,
        extractorData = extractorData,
        type = type ?: inferTypeFromUrl(url),
        kid = kid,
        key = key,
        uuid = uuid,
        keyRequestParameters = keyRequestParameters,
        kty = kty,
        licenseUrl = licenseUrl,
    )

    @Deprecated(message = "Use Kotlin Uuid", level = DeprecationLevel.HIDDEN)
    fun setUuid(uuid: java.util.UUID) {
        this.uuid = uuid.toKotlinUuid()
    }

    @Deprecated(message = "Use Kotlin Uuid", level = DeprecationLevel.HIDDEN)
    fun getUuid(): java.util.UUID = this.uuid.toJavaUuid()
}

/** Class holds extracted media info to be passed to the player.
 * @property source Name of the media source, appears on player layout.
 * @property name Title of the media, appears on player layout.
 * @property url Url string of media file
 * @property referer Referer that will be used by network request.
 * @property quality Quality of the media file
 * @property headers Headers <String, String> map that will be used by network request.
 * @property extractorData Used for getExtractorVerifierJob()
 * @property type Extracted link type (Video, M3u8, Dash, Torrent or Magnet)
 * @property audioTracks List of separate audio tracks that can be used with this video
 * @see newExtractorLink
 * */
open class ExtractorLink
@Deprecated("Use newExtractorLink", level = DeprecationLevel.WARNING)
constructor(
    open val source: String,
    open val name: String,
    override val url: String,
    override var referer: String,
    open var quality: Int,
    override var headers: Map<String, String> = mapOf(),
    /** Used for getExtractorVerifierJob() */
    open var extractorData: String? = null,
    open var type: ExtractorLinkType,
    /** List of separate audio tracks that can be merged with this video */
    open var audioTracks: List<AudioFile> = emptyList(),
) : IDownloadableMinimum {
    val isM3u8: Boolean get() = type == ExtractorLinkType.M3U8
    val isDash: Boolean get() = type == ExtractorLinkType.DASH

    // Cached video size
    private var videoSize: Long? = null

    /**
     * Get video size in bytes with one head request. Only available for ExtractorLinkType.Video
     * @param timeoutSeconds timeout of the head request.
     */
    suspend fun getVideoSize(timeoutSeconds: Long = 3L): Long? {
        // Content-Length is not applicable to other types of formats
        if (this.type != ExtractorLinkType.VIDEO) return null

        videoSize = videoSize ?: runCatching {
            val response =
                app.head(this.url, headers = headers, referer = referer, timeout = timeoutSeconds)
            response.headers["Content-Length"]?.toLong()
        }.getOrNull()

        return videoSize
    }

    @JsonIgnore
    fun getAllHeaders(): Map<String, String> {
        if (referer.isBlank()) {
            return headers
        } else if (headers.keys.none { it.equals("referer", ignoreCase = true) }) {
            return headers + mapOf("referer" to referer)
        }
        return headers
    }

    @Suppress("DEPRECATION")
    @Deprecated("Use newExtractorLink", level = DeprecationLevel.ERROR)
    constructor(
        source: String,
        name: String,
        url: String,
        referer: String? = null,
        quality: Int? = null,
        /** the type of the media, use INFER_TYPE if you want to auto infer the type from the url */
        type: ExtractorLinkType? = INFER_TYPE,
        headers: Map<String, String> = mapOf(),
        /** Used for getExtractorVerifierJob() */
        extractorData: String? = null,
    ) : this(
        source = source,
        name = name,
        url = url,
        referer = referer ?: "",
        quality = quality ?: Qualities.Unknown.value,
        headers = headers,
        extractorData = extractorData,
        type = type ?: inferTypeFromUrl(url)
    )

    @Suppress("DEPRECATION")
    @Deprecated("Use newExtractorLink", level = DeprecationLevel.ERROR)
    constructor(
        source: String,
        name: String,
        url: String,
        referer: String,
        quality: Int,
        /** the type of the media, use INFER_TYPE if you want to auto infer the type from the url */
        type: ExtractorLinkType?,
        headers: Map<String, String> = mapOf(),
        /** Used for getExtractorVerifierJob() */
        extractorData: String? = null,
    ) : this(
        source = source,
        name = name,
        url = url,
        referer = referer,
        quality = quality,
        headers = headers,
        extractorData = extractorData,
        type = type ?: inferTypeFromUrl(url)
    )

    /**
     * Old constructor without isDash, allows for backwards compatibility with extensions.
     * Should be removed after all extensions have updated their cloudstream.jar
     **/
    @Suppress("DEPRECATION_ERROR")
    @Deprecated("Use newExtractorLink", level = DeprecationLevel.ERROR)
    constructor(
        source: String,
        name: String,
        url: String,
        referer: String,
        quality: Int,
        isM3u8: Boolean = false,
        headers: Map<String, String> = mapOf(),
        /** Used for getExtractorVerifierJob() */
        extractorData: String? = null
    ) : this(source, name, url, referer, quality, isM3u8, headers, extractorData, false)

    @Suppress("DEPRECATION")
    @Deprecated("Use newExtractorLink", level = DeprecationLevel.ERROR)
    constructor(
        source: String,
        name: String,
        url: String,
        referer: String,
        quality: Int,
        isM3u8: Boolean = false,
        headers: Map<String, String> = mapOf(),
        /** Used for getExtractorVerifierJob() */
        extractorData: String? = null,
        isDash: Boolean,
    ) : this(
        source = source,
        name = name,
        url = url,
        referer = referer,
        quality = quality,
        headers = headers,
        extractorData = extractorData,
        type = if (isDash) ExtractorLinkType.DASH else if (isM3u8) ExtractorLinkType.M3U8 else ExtractorLinkType.VIDEO
    )

    override fun toString(): String {
        return "ExtractorLink(name=$name, url=$url, referer=$referer, type=$type)"
    }
}

/**
 * Removes https:// and www.
 * To match urls regardless of schema, perhaps Uri() can be used?
 */
val schemaStripRegex = Regex("""^(https:|)//(www\.|)""")

enum class Qualities(var value: Int, val defaultPriority: Int) {
    Unknown(400, 4),
    P144(144, 0), // 144p
    P240(240, 2), // 240p
    P360(360, 3), // 360p
    P480(480, 4), // 480p
    P720(720, 5), // 720p
    P1080(1080, 6), // 1080p
    P1440(1440, 7), // 1440p
    P2160(2160, 8); // 4k or 2160p

    companion object {
        fun getStringByInt(qual: Int?): String {
            return when (qual) {
                0 -> "Auto"
                Unknown.value -> ""
                P2160.value -> "4K"
                null -> ""
                else -> "${qual}p"
            }
        }

        fun getStringByIntFull(quality: Int): String {
            return when (quality) {
                0 -> "Auto"
                Unknown.value -> "Unknown"
                P2160.value -> "4K"
                else -> "${quality}p"
            }
        }
    }
}

fun getQualityFromName(qualityName: String?): Int {
    if (qualityName == null)
        return Qualities.Unknown.value

    val match = qualityName.lowercase().replace("p", "").trim()
    return when (match) {
        "4k" -> Qualities.P2160
        else -> null
    }?.value ?: match.toIntOrNull() ?: Qualities.Unknown.value
}

private val packedRegex = Regex("""eval\(function\(p,a,c,k,e,.*\)\)""")
fun getPacked(string: String): String? {
    return packedRegex.find(string)?.value
}

fun getAndUnpack(string: String): String {
    val packedText = getPacked(string)
    return JsUnpacker(packedText).unpack() ?: string
}

suspend fun unshortenLinkSafe(url: String): String {
    return try {
        if (ShortLink.isShortLink(url))
            ShortLink.unshorten(url)
        else url
    } catch (e: Exception) {
        logError(e)
        url
    }
}

suspend fun loadExtractor(
    url: String,
    subtitleCallback: (SubtitleFile) -> Unit,
    callback: (ExtractorLink) -> Unit
): Boolean {
    return loadExtractor(
        url = url,
        referer = null,
        subtitleCallback = subtitleCallback,
        callback = callback
    )
}


/**
 * Tries to load the appropriate extractor based on link, returns true if any extractor is loaded.
 * */
@Throws(CancellationException::class)
suspend fun loadExtractor(
    url: String,
    referer: String? = null,
    subtitleCallback: (SubtitleFile) -> Unit,
    callback: (ExtractorLink) -> Unit
): Boolean {
    // Ensure this coroutine has not timed out
    coroutineScope { ensureActive() }

    val currentUrl = unshortenLinkSafe(url)
    val compareUrl = currentUrl.lowercase().replace(schemaStripRegex, "")

    // Iterate in reverse order so the new registered ExtractorApi takes priority
    for (index in extractorApis.lastIndex downTo 0) {
        val extractor = extractorApis[index]
        if (compareUrl.startsWith(extractor.mainUrl.replace(schemaStripRegex, ""))) {
            try {
                extractor.getUrl(currentUrl, referer, subtitleCallback, callback)
            } catch (e: Exception) {
                logError(e)
                // Rethrow if we have timed out
                if (e is CancellationException) {
                    throw e
                }
            }
            return true
        }
    }

    // this is to match mirror domains - like example.com, example.net
    for (index in extractorApis.lastIndex downTo 0) {
        val extractor = extractorApis[index]
        if (Levenshtein.partialRatio(
                extractor.mainUrl,
                currentUrl
            ) > 80
        ) {
            try {
                extractor.getUrl(currentUrl, referer, subtitleCallback, callback)
            } catch (e: Exception) {
                logError(e)
                // Rethrow if we have timed out
                if (e is CancellationException) {
                    throw e
                }
            }
            return true
        }
    }

    return false
}

val extractorApis: AtomicMutableList<ExtractorApi> = atomicListOf(
    //AllProvider(),
    Mp4Upload(),
    StreamTape(),
    StreamTapeNet(),
    ShaveTape(),
    StreamTapeXyz(),
    Watchadsontape(),

    //mixdrop extractors
    MixDropBz(),
    MixDropCh(),
    MixDropTo(),
    MixDropAg(),
    MixDrop(),
    MixDropPs(),
    Mdy(),
    MxDropTo(),
    MixDropSi(),

    XStreamCdn(),

    StreamSB(),
    Sblona(),
    Vidgomunimesb(),
    StreamSilk(),
    StreamSB1(),
    StreamSB2(),
    StreamSB3(),
    StreamSB4(),
    StreamSB5(),
    StreamSB6(),
    StreamSB7(),
    StreamSB8(),
    StreamSB9(),
    StreamSB10(),
    StreamSB11(),
    SBfull(),
    // Streamhub(), cause Streamhub2() works
    Streamhub2(),
    Ssbstream(),
    Sbthe(),
    Vidgomunime(),
    Sbflix(),
    Streamsss(),
    Sbspeed(),
    Sbsonic(),
    Sbface(),
    Sbrapid(),
    Lvturbo(),

    Fastream(),
    Videa(),
    FEmbed(),
    FeHD(),
    Fplayer(),
    DBfilm(),
    Luxubu(),
    LayarKaca(),
    Rasacintaku(),
    FEnet(),
    Kotakajair(),
    Cdnplayer(),
    //  WatchSB(), 'cause StreamSB.kt works
    Uqload(),
    Uqload1(),
    Uqload2(),
    Uqloadcx(),
    Uqloadbz(),
    Evoload(),
    Evoload1(),
    UpstreamExtractor(),

    Odnoklassniki(),
    TauVideo(),
    SibNet(),
    ContentX(),
    Hotlinger(),
    FourCX(),
    PlayRu(),
    FourPlayRu(),
    Pichive(),
    FourPichive(),
    HDMomPlayer(),
    HDPlayerSystem(),
    VideoSeyred(),
    PeaceMakerst(),
    HDStreamAble(),
    RapidVid(),
    TRsTX(),
    VidMoxy(),
    Sobreatsesuyp(),
    PixelDrain(),
    PixelDrainDev(),
    MailRu(),

    OkRuSSL(),
    OkRuSSLMobile(),
    OkRuHTTP(),
    OkRuHTTPMobile(),
    Sendvid(),

    // dood extractors
    DoodCxExtractor(),
    DoodPmExtractor(),
    DoodToExtractor(),
    DoodSoExtractor(),
    DoodLaExtractor(),
    Dooood(),
    D0000d(),
    D000dCom(),
    DoodstreamCom(),
    DoodWsExtractor(),
    DoodShExtractor(),
    DoodWatchExtractor(),
    DoodWfExtractor(),
    DoodYtExtractor(),
    Doodspro(),
    Dsvplay(),

    // GenericM3U8(),
    Zplayer(),
    ZplayerV2(),
    Upstream(),

    Maxstream(),
    Tantifilm(),
    Userload(),
    Supervideo(),

    // StreamSB.kt works
    //  SBPlay(),
    //  SBPlay1(),
    //  SBPlay2(),

    PlayerVoxzer(),

    Blogger(),
    YourUpload(),

    Hxfile(),
    KotakAnimeid(),
    Neonime8n(),
    Neonime7n(),
    Yufiles(),
    Aico(),

    JWPlayer(),
    Meownime(),
    DesuArcg(),
    DesuOdchan(),
    DesuOdvip(),
    DesuDrive(),


    Keephealth(),
    Sbnet(),
    Sbasian(),
    Sblongvu(),
    Fembed9hd(),
    StreamM4u(),
    Krakenfiles(),
    Gofile(),
    Vicloud(),
    Uservideo(),
    Userscloud(),

    Movhide(),
    StreamhideCom(),
    StreamhideTo(),
    Wibufile(),
    FileMoonIn(),
    Moviesm4u(),
    Filesim(),
    Multimoviesshg(),
    Ahvsh(),
    Guccihide(),
    FileMoon(),
    FileMoonSx(),
    FilemoonV2(),

    Vido(),
    Linkbox(),
    Acefile(),
    Embedgram(),
    Mvidoo(),
    Streamplay(),
    Vidmoly(),
    Vidmolyme(),
    Vidmolyto(),
    Vidmolybiz(),
    Voe(),
    Voe1(),
    Voe2(),
    Tubeless(),
    Moviehab(),
    MoviehabNet(),
    Jeniusplay(),
    StreamoUpload(),
    Streamup(),
    Streamix(),
    Vidara(),

    GamoVideo(),
    Gdriveplayerapi(),
    Gdriveplayerapp(),
    Gdriveplayerfun(),
    Gdriveplayerio(),
    Gdriveplayerme(),
    Gdriveplayerbiz(),
    Gdriveplayerorg(),
    Gdriveplayerus(),
    Gdriveplayerco(),
    GoodstreamExtractor(),
    Gdriveplayer(),
    DatabaseGdrive(),
    DatabaseGdrive2(),
    Mediafire(),

    YoutubeExtractor(),
    YoutubeShortLinkExtractor(),
    YoutubeMobileExtractor(),
    YoutubeNoCookieExtractor(),
    Streamlare(),
    PlayLtXyz(),

    Cda(),
    Dailymotion(),
    Ztreamhub(),
    Rabbitstream(),
    Dokicloud(),
    Megacloud(),
    VidhideExtractor(),
    VidHidePro(),
    VidHidePro1(),
    VidHidePro2(),
    VidHidePro3(),
    VidHidePro4(),
    VidHidePro5(),
    VidHidePro6(),
    VidHideHub(),
    Ryderjet(),
    VidNest(),
    Dhtpre(),

    // CineMM Redirects
    Dhcplay(),
    HglinkTo(),

    // CineMM mirrors
    HgplayCDN(),
    Habetar(),
    Yuguaab(),
    Guxhag(),
    Auvexiug(),
    Xenolyzb(),
    Haxloppd(),
    Cavanhabg(),
    Dumbalag(),
    Uasopt(),

    Smoothpre(),
    Peytonepre(),
    LuluStream(),
    Lulustream1(),
    Lulustream2(),
    Luluvdoo(),
    StreamWishExtractor(),
    StreamHLS(),
    BigwarpIO(),
    BigwarpArt(),
    BgwpCC(),
    WishembedPro(),
    CdnwishCom(),
    FlaswishCom(),
    SfastwishCom(),
    Playerwish(),
    StreamEmbed(),
    EmturbovidExtractor(),
    Vtbe(),
    SecvideoOnline(),
    FsstOnline(),
    CsstOnline(),
    DsstOnline(),
    Simpulumlamerop(),
    Urochsunloath(),
    NathanFromSubject(),
    Yipsu(),
    MetaGnathTuggers(),
    Geodailymotion(),
    Mwish(),
    Hgcloudto(),
    Dwish(),
    Ewish(),
    Kswplayer(),
    Wishfast(),
    Streamwish2(),
    Strwish(),
    Strwish2(),
    Awish(),
    Obeywish(),
    Jodwish(),
    Swhoi(),
    Multimovies(),
    UqloadsXyz(),
    Doodporn(),
    Asnwish(),
    Nekowish(),
    Nekostream(),
    Swdyu(),
    Wishonly(),
    Ds2play(),
    Ds2video(),
    Vidsonic(),
    InternetArchive(),
    VidStack(),
    GDMirrorbot(),
    Techinmind(),
    Server1uns(),
    VinovoSi(),
    VinovoTo(),
    Vidoza(),
    Videzz(),
    CloudMailRu(),
    HubCloud(),
    VkExtractor(),
    Bysezejataos(),
    ByseSX(),
    ByseVepoin(),
    ByseBuho(),
    MyVidPlay(),
    Playmogo(),
    Vide0Net(),
    Up4Stream(),
    Up4FunTop(),
    GUpload(),
    HlsWish(),
    ByseQekaho(),
)


fun getExtractorApiFromName(name: String): ExtractorApi {
    for (api in extractorApis) {
        if (api.name == name) return api
    }
    return extractorApis[0]
}

fun requireReferer(name: String): Boolean {
    return getExtractorApiFromName(name).requiresReferer
}

fun httpsify(url: String): String {
    return if (url.startsWith("//")) "https:$url" else url
}

suspend fun getPostForm(requestUrl: String, html: String): String? {
    val document = Jsoup.parse(html)
    val inputs = document.select("Form > input")
    if (inputs.size < 4) return null
    var op: String? = null
    var id: String? = null
    var mode: String? = null
    var hash: String? = null

    for (input in inputs) {
        val value = input.attr("value")
        when (input.attr("name")) {
            "op" -> op = value
            "id" -> id = value
            "mode" -> mode = value
            "hash" -> hash = value
            else -> Unit
        }
    }
    if (op == null || id == null || mode == null || hash == null) {
        return null
    }
    delay(5000) // ye this is needed, wont work with 0 delay

    return app.post(
        requestUrl,
        headers = mapOf(
            "content-type" to "application/x-www-form-urlencoded",
            "referer" to requestUrl,
            "user-agent" to USER_AGENT,
            "accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
        ),
        data = mapOf("op" to op, "id" to id, "mode" to mode, "hash" to hash)
    ).text
}

fun ExtractorApi.fixUrl(url: String): String {
    if (url.startsWith("http") ||
        // Do not fix JSON objects when passed as urls.
        url.startsWith("{\"")
    ) {
        return url
    }
    if (url.isEmpty()) {
        return ""
    }

    val startsWithNoHttp = url.startsWith("//")
    if (startsWithNoHttp) {
        return "https:$url"
    } else {
        if (url.startsWith('/')) {
            return mainUrl + url
        }
        return "$mainUrl/$url"
    }
}

abstract class ExtractorApi {
    abstract val name: String
    abstract val mainUrl: String
    abstract val requiresReferer: Boolean

    /** Determines which plugin a given provider is from. This is the full path to the plugin. */
    var sourcePlugin: String? = null

    //suspend fun getSafeUrl(url: String, referer: String? = null): List<ExtractorLink>? {
    //    return safeAsync { getUrl(url, referer) }
    //}

    // this is the new extractorapi, override to add subtitles and stuff
    @Throws
    open suspend fun getUrl(
        url: String,
        referer: String? = null,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        getUrl(url, referer)?.forEach(callback)
    }

    suspend fun getSafeUrl(
        url: String,
        referer: String? = null,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        try {
            getUrl(url, referer, subtitleCallback, callback)
        } catch (e: Exception) {
            logError(e)
        }
    }

    /**
     * Will throw errors, use getSafeUrl if you don't want to handle the exception yourself
     */
    @Throws
    open suspend fun getUrl(url: String, referer: String? = null): List<ExtractorLink>? {
        return emptyList()
    }

    open fun getExtractorUrl(id: String): String {
        return id
    }
}
