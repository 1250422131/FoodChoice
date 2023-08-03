package com.imcys.core.model.cook

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CookFoodVideoInfo(
    @SerializedName("code")
    val code: Long,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("ttl")
    val ttl: Long,
) : Serializable {
    data class Data(
        @SerializedName("aid")
        val aid: Long,
        @SerializedName("bvid")
        val bvid: String,
        @SerializedName("cid")
        val cid: Long,
        @SerializedName("copyright")
        val copyright: Long,
        @SerializedName("ctime")
        val ctime: Long,
        @SerializedName("desc")
        val desc: String,
        @SerializedName("desc_v2")
        val descV2: List<DescV2>,
        @SerializedName("dimension")
        val dimension: Dimension,
        @SerializedName("disable_show_up_info")
        val disableShowUpInfo: Boolean,
        @SerializedName("duration")
        val duration: Long,
        @SerializedName("dynamic")
        val `dynamic`: String,
        @SerializedName("enable_vt")
        val enableVt: Long,
        @SerializedName("honor_reply")
        val honorReply: HonorReply,
        @SerializedName("is_chargeable_season")
        val isChargeableSeason: Boolean,
        @SerializedName("is_season_display")
        val isSeasonDisplay: Boolean,
        @SerializedName("is_story")
        val isStory: Boolean,
        @SerializedName("is_upower_exclusive")
        val isUpowerExclusive: Boolean,
        @SerializedName("is_upower_play")
        val isUpowerPlay: Boolean,
        @SerializedName("like_icon")
        val likeIcon: String,
        @SerializedName("need_jump_bv")
        val needJumpBv: Boolean,
        @SerializedName("no_cache")
        val noCache: Boolean,
        @SerializedName("owner")
        val owner: Owner,
        @SerializedName("pages")
        val pages: List<Page>,
        @SerializedName("pic")
        val pic: String,
        @SerializedName("premiere")
        val premiere: Any,
        @SerializedName("pubdate")
        val pubdate: Long,
        @SerializedName("rights")
        val rights: Rights,
        @SerializedName("stat")
        val stat: Stat,
        @SerializedName("state")
        val state: Long,
        @SerializedName("subtitle")
        val subtitle: Subtitle,
        @SerializedName("teenage_mode")
        val teenageMode: Long,
        @SerializedName("tid")
        val tid: Long,
        @SerializedName("title")
        val title: String,
        @SerializedName("tname")
        val tname: String,
        @SerializedName("user_garb")
        val userGarb: UserGarb,
        @SerializedName("videos")
        val videos: Long,
    ) {
        data class DescV2(
            @SerializedName("biz_id")
            val bizId: Long,
            @SerializedName("raw_text")
            val rawText: String,
            @SerializedName("type")
            val type: Long,
        ) : Serializable

        data class Dimension(
            @SerializedName("height")
            val height: Long,
            @SerializedName("rotate")
            val rotate: Long,
            @SerializedName("width")
            val width: Long,
        ) : Serializable

        class HonorReply : Serializable

        data class Owner(
            @SerializedName("face")
            val face: String,
            @SerializedName("mid")
            val mid: Long,
            @SerializedName("name")
            val name: String,
        ) : Serializable

        data class Page(
            @SerializedName("cid")
            val cid: Long,
            @SerializedName("dimension")
            val dimension: Dimension,
            @SerializedName("duration")
            val duration: Long,
            @SerializedName("first_frame")
            val firstFrame: String,
            @SerializedName("from")
            val from: String,
            @SerializedName("page")
            val page: Long,
            @SerializedName("part")
            val part: String,
            @SerializedName("vid")
            val vid: String,
            @SerializedName("weblink")
            val weblink: String,
        ) : Serializable {
            data class Dimension(
                @SerializedName("height")
                val height: Long,
                @SerializedName("rotate")
                val rotate: Long,
                @SerializedName("width")
                val width: Long,
            ) : Serializable
        }

        data class Rights(
            @SerializedName("arc_pay")
            val arcPay: Long,
            @SerializedName("autoplay")
            val autoplay: Long,
            @SerializedName("bp")
            val bp: Long,
            @SerializedName("clean_mode")
            val cleanMode: Long,
            @SerializedName("download")
            val download: Long,
            @SerializedName("elec")
            val elec: Long,
            @SerializedName("free_watch")
            val freeWatch: Long,
            @SerializedName("hd5")
            val hd5: Long,
            @SerializedName("is_360")
            val is360: Long,
            @SerializedName("is_cooperation")
            val isCooperation: Long,
            @SerializedName("is_stein_gate")
            val isSteinGate: Long,
            @SerializedName("movie")
            val movie: Long,
            @SerializedName("no_background")
            val noBackground: Long,
            @SerializedName("no_reprint")
            val noReprint: Long,
            @SerializedName("no_share")
            val noShare: Long,
            @SerializedName("pay")
            val pay: Long,
            @SerializedName("ugc_pay")
            val ugcPay: Long,
            @SerializedName("ugc_pay_preview")
            val ugcPayPreview: Long,
        ) : Serializable

        data class Stat(
            @SerializedName("aid")
            val aid: Long,
            @SerializedName("argue_msg")
            val argueMsg: String,
            @SerializedName("coin")
            val coin: Long,
            @SerializedName("danmaku")
            val danmaku: Long,
            @SerializedName("dislike")
            val dislike: Long,
            @SerializedName("evaluation")
            val evaluation: String,
            @SerializedName("favorite")
            val favorite: Long,
            @SerializedName("his_rank")
            val hisRank: Long,
            @SerializedName("like")
            val like: Long,
            @SerializedName("now_rank")
            val nowRank: Long,
            @SerializedName("reply")
            val reply: Long,
            @SerializedName("share")
            val share: Long,
            @SerializedName("view")
            val view: Long,
            @SerializedName("vt")
            val vt: Long,
        ) : Serializable

        data class Subtitle(
            @SerializedName("allow_submit")
            val allowSubmit: Boolean,
            @SerializedName("list")
            val list: List<Any>,
        ) : Serializable

        data class UserGarb(
            @SerializedName("url_image_ani_cut")
            val urlImageAniCut: String,
        ) : Serializable
    }
}
