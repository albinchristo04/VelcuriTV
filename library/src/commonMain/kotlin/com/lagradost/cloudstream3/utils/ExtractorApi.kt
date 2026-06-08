@file:OptIn(ExperimentalUuidApi::class)

package com.velcuri.cobaltvpn.utils

import com.fasterxml.jackson.annotation.JsonIgnore
import com.velcuri.cobaltvpn.AudioFile
import com.velcuri.cobaltvpn.IDownloadableMinimum
import com.velcuri.cobaltvpn.Prerelease
import com.velcuri.cobaltvpn.SubtitleFile
import com.velcuri.cobaltvpn.USER_AGENT
import com.velcuri.cobaltvpn.app
import com.velcuri.cobaltvpn.extractors.Acefile
import com.velcuri.cobaltvpn.extractors.Ahvsh
import com.velcuri.cobaltvpn.extractors.Aico
import com.velcuri.cobaltvpn.extractors.Asnwish
import com.velcuri.cobaltvpn.extractors.Auvexiug
import com.velcuri.cobaltvpn.extractors.Awish
import com.velcuri.cobaltvpn.extractors.BgwpCC
import com.velcuri.cobaltvpn.extractors.BigwarpArt
import com.velcuri.cobaltvpn.extractors.BigwarpIO
import com.velcuri.cobaltvpn.extractors.Blogger
import com.velcuri.cobaltvpn.extractors.ByseSX
import com.velcuri.cobaltvpn.extractors.Bysezejataos
import com.velcuri.cobaltvpn.extractors.ByseBuho
import com.velcuri.cobaltvpn.extractors.ByseVepoin
import com.velcuri.cobaltvpn.extractors.ByseQekaho
import com.velcuri.cobaltvpn.extractors.Cavanhabg
import com.velcuri.cobaltvpn.extractors.Cda
import com.velcuri.cobaltvpn.extractors.Cdnplayer
import com.velcuri.cobaltvpn.extractors.CdnwishCom
import com.velcuri.cobaltvpn.extractors.CloudMailRu
import com.velcuri.cobaltvpn.extractors.ContentX
import com.velcuri.cobaltvpn.extractors.CsstOnline
import com.velcuri.cobaltvpn.extractors.D0000d
import com.velcuri.cobaltvpn.extractors.D000dCom
import com.velcuri.cobaltvpn.extractors.DBfilm
import com.velcuri.cobaltvpn.extractors.Dailymotion
import com.velcuri.cobaltvpn.extractors.DatabaseGdrive
import com.velcuri.cobaltvpn.extractors.DatabaseGdrive2
import com.velcuri.cobaltvpn.extractors.DesuArcg
import com.velcuri.cobaltvpn.extractors.DesuDrive
import com.velcuri.cobaltvpn.extractors.DesuOdchan
import com.velcuri.cobaltvpn.extractors.DesuOdvip
import com.velcuri.cobaltvpn.extractors.Dhcplay
import com.velcuri.cobaltvpn.extractors.Dhtpre
import com.velcuri.cobaltvpn.extractors.Dokicloud
import com.velcuri.cobaltvpn.extractors.DoodCxExtractor
import com.velcuri.cobaltvpn.extractors.DoodLaExtractor
import com.velcuri.cobaltvpn.extractors.DoodPmExtractor
import com.velcuri.cobaltvpn.extractors.DoodShExtractor
import com.velcuri.cobaltvpn.extractors.DoodSoExtractor
import com.velcuri.cobaltvpn.extractors.DoodToExtractor
import com.velcuri.cobaltvpn.extractors.DoodWatchExtractor
import com.velcuri.cobaltvpn.extractors.DoodWfExtractor
import com.velcuri.cobaltvpn.extractors.DoodWsExtractor
import com.velcuri.cobaltvpn.extractors.DoodYtExtractor
import com.velcuri.cobaltvpn.extractors.Doodspro
import com.velcuri.cobaltvpn.extractors.Dsvplay
import com.velcuri.cobaltvpn.extractors.Doodporn
import com.velcuri.cobaltvpn.extractors.DoodstreamCom
import com.velcuri.cobaltvpn.extractors.Dooood
import com.velcuri.cobaltvpn.extractors.Ds2play
import com.velcuri.cobaltvpn.extractors.Ds2video
import com.velcuri.cobaltvpn.extractors.DsstOnline
import com.velcuri.cobaltvpn.extractors.Dumbalag
import com.velcuri.cobaltvpn.extractors.Dwish
import com.velcuri.cobaltvpn.extractors.Embedgram
import com.velcuri.cobaltvpn.extractors.EmturbovidExtractor
import com.velcuri.cobaltvpn.extractors.Evoload
import com.velcuri.cobaltvpn.extractors.Evoload1
import com.velcuri.cobaltvpn.extractors.Ewish
import com.velcuri.cobaltvpn.extractors.FEmbed
import com.velcuri.cobaltvpn.extractors.FEnet
import com.velcuri.cobaltvpn.extractors.Fastream
import com.velcuri.cobaltvpn.extractors.FeHD
import com.velcuri.cobaltvpn.extractors.Fembed9hd
import com.velcuri.cobaltvpn.extractors.FileMoon
import com.velcuri.cobaltvpn.extractors.FileMoonIn
import com.velcuri.cobaltvpn.extractors.FileMoonSx
import com.velcuri.cobaltvpn.extractors.FilemoonV2
import com.velcuri.cobaltvpn.extractors.Filesim
import com.velcuri.cobaltvpn.extractors.Multimoviesshg
import com.velcuri.cobaltvpn.extractors.FlaswishCom
import com.velcuri.cobaltvpn.extractors.FourCX
import com.velcuri.cobaltvpn.extractors.FourPichive
import com.velcuri.cobaltvpn.extractors.FourPlayRu
import com.velcuri.cobaltvpn.extractors.Fplayer
import com.velcuri.cobaltvpn.extractors.FsstOnline
import com.velcuri.cobaltvpn.extractors.GDMirrorbot
import com.velcuri.cobaltvpn.extractors.GUpload
import com.velcuri.cobaltvpn.extractors.GamoVideo
import com.velcuri.cobaltvpn.extractors.Gdriveplayer
import com.velcuri.cobaltvpn.extractors.Gdriveplayerapi
import com.velcuri.cobaltvpn.extractors.Gdriveplayerapp
import com.velcuri.cobaltvpn.extractors.Gdriveplayerbiz
import com.velcuri.cobaltvpn.extractors.Gdriveplayerco
import com.velcuri.cobaltvpn.extractors.Gdriveplayerfun
import com.velcuri.cobaltvpn.extractors.Gdriveplayerio
import com.velcuri.cobaltvpn.extractors.Gdriveplayerme
import com.velcuri.cobaltvpn.extractors.Gdriveplayerorg
import com.velcuri.cobaltvpn.extractors.Gdriveplayerus
import com.velcuri.cobaltvpn.extractors.Geodailymotion
import com.velcuri.cobaltvpn.extractors.Gofile
import com.velcuri.cobaltvpn.extractors.GoodstreamExtractor
import com.velcuri.cobaltvpn.extractors.Guccihide
import com.velcuri.cobaltvpn.extractors.Guxhag
import com.velcuri.cobaltvpn.extractors.HDMomPlayer
import com.velcuri.cobaltvpn.extractors.HDPlayerSystem
import com.velcuri.cobaltvpn.extractors.HDStreamAble
import com.velcuri.cobaltvpn.extractors.Habetar
import com.velcuri.cobaltvpn.extractors.Haxloppd
import com.velcuri.cobaltvpn.extractors.Hgcloudto
import com.velcuri.cobaltvpn.extractors.HglinkTo
import com.velcuri.cobaltvpn.extractors.HgplayCDN
import com.velcuri.cobaltvpn.extractors.Hotlinger
import com.velcuri.cobaltvpn.extractors.HubCloud
import com.velcuri.cobaltvpn.extractors.Hxfile
import com.velcuri.cobaltvpn.extractors.HlsWish
import com.velcuri.cobaltvpn.extractors.InternetArchive
import com.velcuri.cobaltvpn.extractors.JWPlayer
import com.velcuri.cobaltvpn.extractors.Jeniusplay
import com.velcuri.cobaltvpn.extractors.Jodwish
import com.velcuri.cobaltvpn.extractors.Keephealth
import com.velcuri.cobaltvpn.extractors.KotakAnimeid
import com.velcuri.cobaltvpn.extractors.Kotakajair
import com.velcuri.cobaltvpn.extractors.Krakenfiles
import com.velcuri.cobaltvpn.extractors.Kswplayer
import com.velcuri.cobaltvpn.extractors.LayarKaca
import com.velcuri.cobaltvpn.extractors.Linkbox
import com.velcuri.cobaltvpn.extractors.LuluStream
import com.velcuri.cobaltvpn.extractors.Lulustream1
import com.velcuri.cobaltvpn.extractors.Lulustream2
import com.velcuri.cobaltvpn.extractors.Luluvdoo
import com.velcuri.cobaltvpn.extractors.Luxubu
import com.velcuri.cobaltvpn.extractors.Lvturbo
import com.velcuri.cobaltvpn.extractors.MailRu
import com.velcuri.cobaltvpn.extractors.Maxstream
import com.velcuri.cobaltvpn.extractors.Mediafire
import com.velcuri.cobaltvpn.extractors.Megacloud
import com.velcuri.cobaltvpn.extractors.Meownime
import com.velcuri.cobaltvpn.extractors.MetaGnathTuggers
import com.velcuri.cobaltvpn.extractors.MixDrop
import com.velcuri.cobaltvpn.extractors.MixDropAg
import com.velcuri.cobaltvpn.extractors.MixDropBz
import com.velcuri.cobaltvpn.extractors.MixDropCh
import com.velcuri.cobaltvpn.extractors.MixDropTo
import com.velcuri.cobaltvpn.extractors.MixDropPs
import com.velcuri.cobaltvpn.extractors.Mdy
import com.velcuri.cobaltvpn.extractors.MixDropSi
import com.velcuri.cobaltvpn.extractors.MxDropTo
import com.velcuri.cobaltvpn.extractors.Movhide
import com.velcuri.cobaltvpn.extractors.Moviehab
import com.velcuri.cobaltvpn.extractors.MoviehabNet
import com.velcuri.cobaltvpn.extractors.Moviesm4u
import com.velcuri.cobaltvpn.extractors.Mp4Upload
import com.velcuri.cobaltvpn.extractors.Multimovies
import com.velcuri.cobaltvpn.extractors.Mvidoo
import com.velcuri.cobaltvpn.extractors.MyVidPlay
import com.velcuri.cobaltvpn.extractors.Mwish
import com.velcuri.cobaltvpn.extractors.NathanFromSubject
import com.velcuri.cobaltvpn.extractors.Nekostream
import com.velcuri.cobaltvpn.extractors.Nekowish
import com.velcuri.cobaltvpn.extractors.Neonime7n
import com.velcuri.cobaltvpn.extractors.Neonime8n
import com.velcuri.cobaltvpn.extractors.Obeywish
import com.velcuri.cobaltvpn.extractors.Odnoklassniki
import com.velcuri.cobaltvpn.extractors.OkRuHTTP
import com.velcuri.cobaltvpn.extractors.OkRuHTTPMobile
import com.velcuri.cobaltvpn.extractors.OkRuSSL
import com.velcuri.cobaltvpn.extractors.OkRuSSLMobile
import com.velcuri.cobaltvpn.extractors.PeaceMakerst
import com.velcuri.cobaltvpn.extractors.Peytonepre
import com.velcuri.cobaltvpn.extractors.Pichive
import com.velcuri.cobaltvpn.extractors.PixelDrain
import com.velcuri.cobaltvpn.extractors.PixelDrainDev
import com.velcuri.cobaltvpn.extractors.PlayLtXyz
import com.velcuri.cobaltvpn.extractors.PlayRu
import com.velcuri.cobaltvpn.extractors.PlayerVoxzer
import com.velcuri.cobaltvpn.extractors.Playerwish
import com.velcuri.cobaltvpn.extractors.Playmogo
import com.velcuri.cobaltvpn.extractors.Rabbitstream
import com.velcuri.cobaltvpn.extractors.RapidVid
import com.velcuri.cobaltvpn.extractors.Rasacintaku
import com.velcuri.cobaltvpn.extractors.SBfull
import com.velcuri.cobaltvpn.extractors.Sbasian
import com.velcuri.cobaltvpn.extractors.Sbface
import com.velcuri.cobaltvpn.extractors.Sbflix
import com.velcuri.cobaltvpn.extractors.Sblona
import com.velcuri.cobaltvpn.extractors.Sblongvu
import com.velcuri.cobaltvpn.extractors.Sbnet
import com.velcuri.cobaltvpn.extractors.Sbrapid
import com.velcuri.cobaltvpn.extractors.Sbsonic
import com.velcuri.cobaltvpn.extractors.Sbspeed
import com.velcuri.cobaltvpn.extractors.Sbthe
import com.velcuri.cobaltvpn.extractors.SecvideoOnline
import com.velcuri.cobaltvpn.extractors.Sendvid
import com.velcuri.cobaltvpn.extractors.Server1uns
import com.velcuri.cobaltvpn.extractors.SfastwishCom
import com.velcuri.cobaltvpn.extractors.ShaveTape
import com.velcuri.cobaltvpn.extractors.SibNet
import com.velcuri.cobaltvpn.extractors.Simpulumlamerop
import com.velcuri.cobaltvpn.extractors.Smoothpre
import com.velcuri.cobaltvpn.extractors.Sobreatsesuyp
import com.velcuri.cobaltvpn.extractors.Ssbstream
import com.velcuri.cobaltvpn.extractors.StreamEmbed
import com.velcuri.cobaltvpn.extractors.StreamHLS
import com.velcuri.cobaltvpn.extractors.StreamM4u
import com.velcuri.cobaltvpn.extractors.StreamSB
import com.velcuri.cobaltvpn.extractors.StreamSB1
import com.velcuri.cobaltvpn.extractors.StreamSB10
import com.velcuri.cobaltvpn.extractors.StreamSB11
import com.velcuri.cobaltvpn.extractors.StreamSB2
import com.velcuri.cobaltvpn.extractors.StreamSB3
import com.velcuri.cobaltvpn.extractors.StreamSB4
import com.velcuri.cobaltvpn.extractors.StreamSB5
import com.velcuri.cobaltvpn.extractors.StreamSB6
import com.velcuri.cobaltvpn.extractors.StreamSB7
import com.velcuri.cobaltvpn.extractors.StreamSB8
import com.velcuri.cobaltvpn.extractors.StreamSB9
import com.velcuri.cobaltvpn.extractors.StreamSilk
import com.velcuri.cobaltvpn.extractors.StreamTape
import com.velcuri.cobaltvpn.extractors.StreamTapeNet
import com.velcuri.cobaltvpn.extractors.StreamTapeXyz
import com.velcuri.cobaltvpn.extractors.Watchadsontape
import com.velcuri.cobaltvpn.extractors.StreamWishExtractor
import com.velcuri.cobaltvpn.extractors.StreamhideCom
import com.velcuri.cobaltvpn.extractors.StreamhideTo
import com.velcuri.cobaltvpn.extractors.Streamhub2
import com.velcuri.cobaltvpn.extractors.Streamix
import com.velcuri.cobaltvpn.extractors.Streamlare
import com.velcuri.cobaltvpn.extractors.StreamoUpload
import com.velcuri.cobaltvpn.extractors.Streamplay
import com.velcuri.cobaltvpn.extractors.Streamsss
import com.velcuri.cobaltvpn.extractors.Streamup
import com.velcuri.cobaltvpn.extractors.Streamwish2
import com.velcuri.cobaltvpn.extractors.Strwish
import com.velcuri.cobaltvpn.extractors.Strwish2
import com.velcuri.cobaltvpn.extractors.Supervideo
import com.velcuri.cobaltvpn.extractors.Swdyu
import com.velcuri.cobaltvpn.extractors.Swhoi
import com.velcuri.cobaltvpn.extractors.TRsTX
import com.velcuri.cobaltvpn.extractors.Tantifilm
import com.velcuri.cobaltvpn.extractors.TauVideo
import com.velcuri.cobaltvpn.extractors.Techinmind
import com.velcuri.cobaltvpn.extractors.Tubeless
import com.velcuri.cobaltvpn.extractors.Uasopt
import com.velcuri.cobaltvpn.extractors.Up4FunTop
import com.velcuri.cobaltvpn.extractors.Up4Stream
import com.velcuri.cobaltvpn.extractors.Upstream
import com.velcuri.cobaltvpn.extractors.UpstreamExtractor
import com.velcuri.cobaltvpn.extractors.Uqload
import com.velcuri.cobaltvpn.extractors.Uqload1
import com.velcuri.cobaltvpn.extractors.Uqload2
import com.velcuri.cobaltvpn.extractors.Uqloadcx
import com.velcuri.cobaltvpn.extractors.Uqloadbz
import com.velcuri.cobaltvpn.extractors.UqloadsXyz
import com.velcuri.cobaltvpn.extractors.Urochsunloath
import com.velcuri.cobaltvpn.extractors.Userload
import com.velcuri.cobaltvpn.extractors.Userscloud
import com.velcuri.cobaltvpn.extractors.Uservideo
import com.velcuri.cobaltvpn.extractors.Videa
import com.velcuri.cobaltvpn.extractors.Vicloud
import com.velcuri.cobaltvpn.extractors.VidHidePro
import com.velcuri.cobaltvpn.extractors.VidHidePro1
import com.velcuri.cobaltvpn.extractors.VidHidePro2
import com.velcuri.cobaltvpn.extractors.VidHidePro3
import com.velcuri.cobaltvpn.extractors.VidHidePro4
import com.velcuri.cobaltvpn.extractors.VidHidePro5
import com.velcuri.cobaltvpn.extractors.VidHidePro6
import com.velcuri.cobaltvpn.extractors.VidHideHub
import com.velcuri.cobaltvpn.extractors.Ryderjet
import com.velcuri.cobaltvpn.extractors.VidMoxy
import com.velcuri.cobaltvpn.extractors.VidStack
import com.velcuri.cobaltvpn.extractors.VideoSeyred
import com.velcuri.cobaltvpn.extractors.Videzz
import com.velcuri.cobaltvpn.extractors.Vidgomunime
import com.velcuri.cobaltvpn.extractors.Vidgomunimesb
import com.velcuri.cobaltvpn.extractors.VidhideExtractor
import com.velcuri.cobaltvpn.extractors.Vidmoly
import com.velcuri.cobaltvpn.extractors.Vidmolyme
import com.velcuri.cobaltvpn.extractors.Vidmolyto
import com.velcuri.cobaltvpn.extractors.Vidmolybiz
import com.velcuri.cobaltvpn.extractors.Vido
import com.velcuri.cobaltvpn.extractors.Vidoza
import com.velcuri.cobaltvpn.extractors.VinovoSi
import com.velcuri.cobaltvpn.extractors.VinovoTo
import com.velcuri.cobaltvpn.extractors.VidNest
import com.velcuri.cobaltvpn.extractors.Vidara
import com.velcuri.cobaltvpn.extractors.Vide0Net
import com.velcuri.cobaltvpn.extractors.Vidsonic
import com.velcuri.cobaltvpn.extractors.VkExtractor
import com.velcuri.cobaltvpn.extractors.Voe
import com.velcuri.cobaltvpn.extractors.Voe1
import com.velcuri.cobaltvpn.extractors.Voe2
import com.velcuri.cobaltvpn.extractors.Vtbe
import com.velcuri.cobaltvpn.extractors.Wibufile
import com.velcuri.cobaltvpn.extractors.WishembedPro
import com.velcuri.cobaltvpn.extractors.Wishfast
import com.velcuri.cobaltvpn.extractors.Wishonly
import com.velcuri.cobaltvpn.extractors.XStreamCdn
import com.velcuri.cobaltvpn.extractors.Xenolyzb
import com.velcuri.cobaltvpn.extractors.Yipsu
import com.velcuri.cobaltvpn.extractors.YourUpload
import com.velcuri.cobaltvpn.extractors.YoutubeExtractor
import com.velcuri.cobaltvpn.extractors.YoutubeMobileExtractor
import com.velcuri.cobaltvpn.extractors.YoutubeNoCookieExtractor
import com.velcuri.cobaltvpn.extractors.YoutubeShortLinkExtractor
import com.velcuri.cobaltvpn.extractors.Yufiles
import com.velcuri.cobaltvpn.extractors.Yuguaab
import com.velcuri.cobaltvpn.extractors.Zplayer
import com.velcuri.cobaltvpn.extractors.ZplayerV2
import com.velcuri.cobaltvpn.extractors.Ztreamhub
import com.velcuri.cobaltvpn.mvvm.logError
import com.velcuri.cobaltvpn.utils.Coroutines.atomicListOf
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
