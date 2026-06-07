@file:OptIn(ExperimentalUuidApi::class)

package io.velcuri.tv.utils

import com.fasterxml.jackson.annotation.JsonIgnore
import io.velcuri.tv.AudioFile
import io.velcuri.tv.IDownloadableMinimum
import io.velcuri.tv.Prerelease
import io.velcuri.tv.SubtitleFile
import io.velcuri.tv.USER_AGENT
import io.velcuri.tv.app
import io.velcuri.tv.extractors.Acefile
import io.velcuri.tv.extractors.Ahvsh
import io.velcuri.tv.extractors.Aico
import io.velcuri.tv.extractors.Asnwish
import io.velcuri.tv.extractors.Auvexiug
import io.velcuri.tv.extractors.Awish
import io.velcuri.tv.extractors.BgwpCC
import io.velcuri.tv.extractors.BigwarpArt
import io.velcuri.tv.extractors.BigwarpIO
import io.velcuri.tv.extractors.Blogger
import io.velcuri.tv.extractors.ByseSX
import io.velcuri.tv.extractors.Bysezejataos
import io.velcuri.tv.extractors.ByseBuho
import io.velcuri.tv.extractors.ByseVepoin
import io.velcuri.tv.extractors.ByseQekaho
import io.velcuri.tv.extractors.Cavanhabg
import io.velcuri.tv.extractors.Cda
import io.velcuri.tv.extractors.Cdnplayer
import io.velcuri.tv.extractors.CdnwishCom
import io.velcuri.tv.extractors.CloudMailRu
import io.velcuri.tv.extractors.ContentX
import io.velcuri.tv.extractors.CsstOnline
import io.velcuri.tv.extractors.D0000d
import io.velcuri.tv.extractors.D000dCom
import io.velcuri.tv.extractors.DBfilm
import io.velcuri.tv.extractors.Dailymotion
import io.velcuri.tv.extractors.DatabaseGdrive
import io.velcuri.tv.extractors.DatabaseGdrive2
import io.velcuri.tv.extractors.DesuArcg
import io.velcuri.tv.extractors.DesuDrive
import io.velcuri.tv.extractors.DesuOdchan
import io.velcuri.tv.extractors.DesuOdvip
import io.velcuri.tv.extractors.Dhcplay
import io.velcuri.tv.extractors.Dhtpre
import io.velcuri.tv.extractors.Dokicloud
import io.velcuri.tv.extractors.DoodCxExtractor
import io.velcuri.tv.extractors.DoodLaExtractor
import io.velcuri.tv.extractors.DoodPmExtractor
import io.velcuri.tv.extractors.DoodShExtractor
import io.velcuri.tv.extractors.DoodSoExtractor
import io.velcuri.tv.extractors.DoodToExtractor
import io.velcuri.tv.extractors.DoodWatchExtractor
import io.velcuri.tv.extractors.DoodWfExtractor
import io.velcuri.tv.extractors.DoodWsExtractor
import io.velcuri.tv.extractors.DoodYtExtractor
import io.velcuri.tv.extractors.Doodspro
import io.velcuri.tv.extractors.Dsvplay
import io.velcuri.tv.extractors.Doodporn
import io.velcuri.tv.extractors.DoodstreamCom
import io.velcuri.tv.extractors.Dooood
import io.velcuri.tv.extractors.Ds2play
import io.velcuri.tv.extractors.Ds2video
import io.velcuri.tv.extractors.DsstOnline
import io.velcuri.tv.extractors.Dumbalag
import io.velcuri.tv.extractors.Dwish
import io.velcuri.tv.extractors.Embedgram
import io.velcuri.tv.extractors.EmturbovidExtractor
import io.velcuri.tv.extractors.Evoload
import io.velcuri.tv.extractors.Evoload1
import io.velcuri.tv.extractors.Ewish
import io.velcuri.tv.extractors.FEmbed
import io.velcuri.tv.extractors.FEnet
import io.velcuri.tv.extractors.Fastream
import io.velcuri.tv.extractors.FeHD
import io.velcuri.tv.extractors.Fembed9hd
import io.velcuri.tv.extractors.FileMoon
import io.velcuri.tv.extractors.FileMoonIn
import io.velcuri.tv.extractors.FileMoonSx
import io.velcuri.tv.extractors.FilemoonV2
import io.velcuri.tv.extractors.Filesim
import io.velcuri.tv.extractors.Multimoviesshg
import io.velcuri.tv.extractors.FlaswishCom
import io.velcuri.tv.extractors.FourCX
import io.velcuri.tv.extractors.FourPichive
import io.velcuri.tv.extractors.FourPlayRu
import io.velcuri.tv.extractors.Fplayer
import io.velcuri.tv.extractors.FsstOnline
import io.velcuri.tv.extractors.GDMirrorbot
import io.velcuri.tv.extractors.GUpload
import io.velcuri.tv.extractors.GamoVideo
import io.velcuri.tv.extractors.Gdriveplayer
import io.velcuri.tv.extractors.Gdriveplayerapi
import io.velcuri.tv.extractors.Gdriveplayerapp
import io.velcuri.tv.extractors.Gdriveplayerbiz
import io.velcuri.tv.extractors.Gdriveplayerco
import io.velcuri.tv.extractors.Gdriveplayerfun
import io.velcuri.tv.extractors.Gdriveplayerio
import io.velcuri.tv.extractors.Gdriveplayerme
import io.velcuri.tv.extractors.Gdriveplayerorg
import io.velcuri.tv.extractors.Gdriveplayerus
import io.velcuri.tv.extractors.Geodailymotion
import io.velcuri.tv.extractors.Gofile
import io.velcuri.tv.extractors.GoodstreamExtractor
import io.velcuri.tv.extractors.Guccihide
import io.velcuri.tv.extractors.Guxhag
import io.velcuri.tv.extractors.HDMomPlayer
import io.velcuri.tv.extractors.HDPlayerSystem
import io.velcuri.tv.extractors.HDStreamAble
import io.velcuri.tv.extractors.Habetar
import io.velcuri.tv.extractors.Haxloppd
import io.velcuri.tv.extractors.Hgcloudto
import io.velcuri.tv.extractors.HglinkTo
import io.velcuri.tv.extractors.HgplayCDN
import io.velcuri.tv.extractors.Hotlinger
import io.velcuri.tv.extractors.HubCloud
import io.velcuri.tv.extractors.Hxfile
import io.velcuri.tv.extractors.HlsWish
import io.velcuri.tv.extractors.InternetArchive
import io.velcuri.tv.extractors.JWPlayer
import io.velcuri.tv.extractors.Jeniusplay
import io.velcuri.tv.extractors.Jodwish
import io.velcuri.tv.extractors.Keephealth
import io.velcuri.tv.extractors.KotakAnimeid
import io.velcuri.tv.extractors.Kotakajair
import io.velcuri.tv.extractors.Krakenfiles
import io.velcuri.tv.extractors.Kswplayer
import io.velcuri.tv.extractors.LayarKaca
import io.velcuri.tv.extractors.Linkbox
import io.velcuri.tv.extractors.LuluStream
import io.velcuri.tv.extractors.Lulustream1
import io.velcuri.tv.extractors.Lulustream2
import io.velcuri.tv.extractors.Luluvdoo
import io.velcuri.tv.extractors.Luxubu
import io.velcuri.tv.extractors.Lvturbo
import io.velcuri.tv.extractors.MailRu
import io.velcuri.tv.extractors.Maxstream
import io.velcuri.tv.extractors.Mediafire
import io.velcuri.tv.extractors.Megacloud
import io.velcuri.tv.extractors.Meownime
import io.velcuri.tv.extractors.MetaGnathTuggers
import io.velcuri.tv.extractors.MixDrop
import io.velcuri.tv.extractors.MixDropAg
import io.velcuri.tv.extractors.MixDropBz
import io.velcuri.tv.extractors.MixDropCh
import io.velcuri.tv.extractors.MixDropTo
import io.velcuri.tv.extractors.MixDropPs
import io.velcuri.tv.extractors.Mdy
import io.velcuri.tv.extractors.MixDropSi
import io.velcuri.tv.extractors.MxDropTo
import io.velcuri.tv.extractors.Movhide
import io.velcuri.tv.extractors.Moviehab
import io.velcuri.tv.extractors.MoviehabNet
import io.velcuri.tv.extractors.Moviesm4u
import io.velcuri.tv.extractors.Mp4Upload
import io.velcuri.tv.extractors.Multimovies
import io.velcuri.tv.extractors.Mvidoo
import io.velcuri.tv.extractors.MyVidPlay
import io.velcuri.tv.extractors.Mwish
import io.velcuri.tv.extractors.NathanFromSubject
import io.velcuri.tv.extractors.Nekostream
import io.velcuri.tv.extractors.Nekowish
import io.velcuri.tv.extractors.Neonime7n
import io.velcuri.tv.extractors.Neonime8n
import io.velcuri.tv.extractors.Obeywish
import io.velcuri.tv.extractors.Odnoklassniki
import io.velcuri.tv.extractors.OkRuHTTP
import io.velcuri.tv.extractors.OkRuHTTPMobile
import io.velcuri.tv.extractors.OkRuSSL
import io.velcuri.tv.extractors.OkRuSSLMobile
import io.velcuri.tv.extractors.PeaceMakerst
import io.velcuri.tv.extractors.Peytonepre
import io.velcuri.tv.extractors.Pichive
import io.velcuri.tv.extractors.PixelDrain
import io.velcuri.tv.extractors.PixelDrainDev
import io.velcuri.tv.extractors.PlayLtXyz
import io.velcuri.tv.extractors.PlayRu
import io.velcuri.tv.extractors.PlayerVoxzer
import io.velcuri.tv.extractors.Playerwish
import io.velcuri.tv.extractors.Playmogo
import io.velcuri.tv.extractors.Rabbitstream
import io.velcuri.tv.extractors.RapidVid
import io.velcuri.tv.extractors.Rasacintaku
import io.velcuri.tv.extractors.SBfull
import io.velcuri.tv.extractors.Sbasian
import io.velcuri.tv.extractors.Sbface
import io.velcuri.tv.extractors.Sbflix
import io.velcuri.tv.extractors.Sblona
import io.velcuri.tv.extractors.Sblongvu
import io.velcuri.tv.extractors.Sbnet
import io.velcuri.tv.extractors.Sbrapid
import io.velcuri.tv.extractors.Sbsonic
import io.velcuri.tv.extractors.Sbspeed
import io.velcuri.tv.extractors.Sbthe
import io.velcuri.tv.extractors.SecvideoOnline
import io.velcuri.tv.extractors.Sendvid
import io.velcuri.tv.extractors.Server1uns
import io.velcuri.tv.extractors.SfastwishCom
import io.velcuri.tv.extractors.ShaveTape
import io.velcuri.tv.extractors.SibNet
import io.velcuri.tv.extractors.Simpulumlamerop
import io.velcuri.tv.extractors.Smoothpre
import io.velcuri.tv.extractors.Sobreatsesuyp
import io.velcuri.tv.extractors.Ssbstream
import io.velcuri.tv.extractors.StreamEmbed
import io.velcuri.tv.extractors.StreamHLS
import io.velcuri.tv.extractors.StreamM4u
import io.velcuri.tv.extractors.StreamSB
import io.velcuri.tv.extractors.StreamSB1
import io.velcuri.tv.extractors.StreamSB10
import io.velcuri.tv.extractors.StreamSB11
import io.velcuri.tv.extractors.StreamSB2
import io.velcuri.tv.extractors.StreamSB3
import io.velcuri.tv.extractors.StreamSB4
import io.velcuri.tv.extractors.StreamSB5
import io.velcuri.tv.extractors.StreamSB6
import io.velcuri.tv.extractors.StreamSB7
import io.velcuri.tv.extractors.StreamSB8
import io.velcuri.tv.extractors.StreamSB9
import io.velcuri.tv.extractors.StreamSilk
import io.velcuri.tv.extractors.StreamTape
import io.velcuri.tv.extractors.StreamTapeNet
import io.velcuri.tv.extractors.StreamTapeXyz
import io.velcuri.tv.extractors.Watchadsontape
import io.velcuri.tv.extractors.StreamWishExtractor
import io.velcuri.tv.extractors.StreamhideCom
import io.velcuri.tv.extractors.StreamhideTo
import io.velcuri.tv.extractors.Streamhub2
import io.velcuri.tv.extractors.Streamix
import io.velcuri.tv.extractors.Streamlare
import io.velcuri.tv.extractors.StreamoUpload
import io.velcuri.tv.extractors.Streamplay
import io.velcuri.tv.extractors.Streamsss
import io.velcuri.tv.extractors.Streamup
import io.velcuri.tv.extractors.Streamwish2
import io.velcuri.tv.extractors.Strwish
import io.velcuri.tv.extractors.Strwish2
import io.velcuri.tv.extractors.Supervideo
import io.velcuri.tv.extractors.Swdyu
import io.velcuri.tv.extractors.Swhoi
import io.velcuri.tv.extractors.TRsTX
import io.velcuri.tv.extractors.Tantifilm
import io.velcuri.tv.extractors.TauVideo
import io.velcuri.tv.extractors.Techinmind
import io.velcuri.tv.extractors.Tubeless
import io.velcuri.tv.extractors.Uasopt
import io.velcuri.tv.extractors.Up4FunTop
import io.velcuri.tv.extractors.Up4Stream
import io.velcuri.tv.extractors.Upstream
import io.velcuri.tv.extractors.UpstreamExtractor
import io.velcuri.tv.extractors.Uqload
import io.velcuri.tv.extractors.Uqload1
import io.velcuri.tv.extractors.Uqload2
import io.velcuri.tv.extractors.Uqloadcx
import io.velcuri.tv.extractors.Uqloadbz
import io.velcuri.tv.extractors.UqloadsXyz
import io.velcuri.tv.extractors.Urochsunloath
import io.velcuri.tv.extractors.Userload
import io.velcuri.tv.extractors.Userscloud
import io.velcuri.tv.extractors.Uservideo
import io.velcuri.tv.extractors.Videa
import io.velcuri.tv.extractors.Vicloud
import io.velcuri.tv.extractors.VidHidePro
import io.velcuri.tv.extractors.VidHidePro1
import io.velcuri.tv.extractors.VidHidePro2
import io.velcuri.tv.extractors.VidHidePro3
import io.velcuri.tv.extractors.VidHidePro4
import io.velcuri.tv.extractors.VidHidePro5
import io.velcuri.tv.extractors.VidHidePro6
import io.velcuri.tv.extractors.VidHideHub
import io.velcuri.tv.extractors.Ryderjet
import io.velcuri.tv.extractors.VidMoxy
import io.velcuri.tv.extractors.VidStack
import io.velcuri.tv.extractors.VideoSeyred
import io.velcuri.tv.extractors.Videzz
import io.velcuri.tv.extractors.Vidgomunime
import io.velcuri.tv.extractors.Vidgomunimesb
import io.velcuri.tv.extractors.VidhideExtractor
import io.velcuri.tv.extractors.Vidmoly
import io.velcuri.tv.extractors.Vidmolyme
import io.velcuri.tv.extractors.Vidmolyto
import io.velcuri.tv.extractors.Vidmolybiz
import io.velcuri.tv.extractors.Vido
import io.velcuri.tv.extractors.Vidoza
import io.velcuri.tv.extractors.VinovoSi
import io.velcuri.tv.extractors.VinovoTo
import io.velcuri.tv.extractors.VidNest
import io.velcuri.tv.extractors.Vidara
import io.velcuri.tv.extractors.Vide0Net
import io.velcuri.tv.extractors.Vidsonic
import io.velcuri.tv.extractors.VkExtractor
import io.velcuri.tv.extractors.Voe
import io.velcuri.tv.extractors.Voe1
import io.velcuri.tv.extractors.Voe2
import io.velcuri.tv.extractors.Vtbe
import io.velcuri.tv.extractors.Wibufile
import io.velcuri.tv.extractors.WishembedPro
import io.velcuri.tv.extractors.Wishfast
import io.velcuri.tv.extractors.Wishonly
import io.velcuri.tv.extractors.XStreamCdn
import io.velcuri.tv.extractors.Xenolyzb
import io.velcuri.tv.extractors.Yipsu
import io.velcuri.tv.extractors.YourUpload
import io.velcuri.tv.extractors.YoutubeExtractor
import io.velcuri.tv.extractors.YoutubeMobileExtractor
import io.velcuri.tv.extractors.YoutubeNoCookieExtractor
import io.velcuri.tv.extractors.YoutubeShortLinkExtractor
import io.velcuri.tv.extractors.Yufiles
import io.velcuri.tv.extractors.Yuguaab
import io.velcuri.tv.extractors.Zplayer
import io.velcuri.tv.extractors.ZplayerV2
import io.velcuri.tv.extractors.Ztreamhub
import io.velcuri.tv.mvvm.logError
import io.velcuri.tv.utils.Coroutines.atomicListOf
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
