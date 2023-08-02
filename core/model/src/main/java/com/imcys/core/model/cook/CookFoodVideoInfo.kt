package com.imcys.core.model.cook

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CookFoodVideoInfo(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("ttl")
    val ttl: Int,
) : Serializable {
    data class Data(
        @SerializedName("aid")
        val aid: Int,
        @SerializedName("bvid")
        val bvid: String,
        @SerializedName("cid")
        val cid: Int,
        @SerializedName("copyright")
        val copyright: Int,
        @SerializedName("ctime")
        val ctime: Int,
        @SerializedName("desc")
        val desc: String,
        @SerializedName("desc_v2")
        val descV2: List<DescV2>,
        @SerializedName("dimension")
        val dimension: Dimension,
        @SerializedName("disable_show_up_info")
        val disableShowUpInfo: Boolean,
        @SerializedName("duration")
        val duration: Int,
        @SerializedName("dynamic")
        val `dynamic`: String,
        @SerializedName("enable_vt")
        val enableVt: Int,
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
        val pubdate: Int,
        @SerializedName("rights")
        val rights: Rights,
        @SerializedName("stat")
        val stat: Stat,
        @SerializedName("state")
        val state: Int,
        @SerializedName("subtitle")
        val subtitle: Subtitle,
        @SerializedName("teenage_mode")
        val teenageMode: Int,
        @SerializedName("tid")
        val tid: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("tname")
        val tname: String,
        @SerializedName("user_garb")
        val userGarb: UserGarb,
        @SerializedName("videos")
        val videos: Int,
    ) {
        data class DescV2(
            @SerializedName("biz_id")
            val bizId: Int,
            @SerializedName("raw_text")
            val rawText: String,
            @SerializedName("type")
            val type: Int,
        ) : Serializable

        data class Dimension(
            @SerializedName("height")
            val height: Int,
            @SerializedName("rotate")
            val rotate: Int,
            @SerializedName("width")
            val width: Int,
        ) : Serializable

        class HonorReply : Serializable

        data class Owner(
            @SerializedName("face")
            val face: String,
            @SerializedName("mid")
            val mid: Int,
            @SerializedName("name")
            val name: String,
        ) : Serializable

        data class Page(
            @SerializedName("cid")
            val cid: Int,
            @SerializedName("dimension")
            val dimension: Dimension,
            @SerializedName("duration")
            val duration: Int,
            @SerializedName("first_frame")
            val firstFrame: String,
            @SerializedName("from")
            val from: String,
            @SerializedName("page")
            val page: Int,
            @SerializedName("part")
            val part: String,
            @SerializedName("vid")
            val vid: String,
            @SerializedName("weblink")
            val weblink: String,
        ) : Serializable {
            data class Dimension(
                @SerializedName("height")
                val height: Int,
                @SerializedName("rotate")
                val rotate: Int,
                @SerializedName("width")
                val width: Int,
            ) : Serializable
        }

        data class Rights(
            @SerializedName("arc_pay")
            val arcPay: Int,
            @SerializedName("autoplay")
            val autoplay: Int,
            @SerializedName("bp")
            val bp: Int,
            @SerializedName("clean_mode")
            val cleanMode: Int,
            @SerializedName("download")
            val download: Int,
            @SerializedName("elec")
            val elec: Int,
            @SerializedName("free_watch")
            val freeWatch: Int,
            @SerializedName("hd5")
            val hd5: Int,
            @SerializedName("is_360")
            val is360: Int,
            @SerializedName("is_cooperation")
            val isCooperation: Int,
            @SerializedName("is_stein_gate")
            val isSteinGate: Int,
            @SerializedName("movie")
            val movie: Int,
            @SerializedName("no_background")
            val noBackground: Int,
            @SerializedName("no_reprint")
            val noReprint: Int,
            @SerializedName("no_share")
            val noShare: Int,
            @SerializedName("pay")
            val pay: Int,
            @SerializedName("ugc_pay")
            val ugcPay: Int,
            @SerializedName("ugc_pay_preview")
            val ugcPayPreview: Int,
        ) : Serializable

        data class Stat(
            @SerializedName("aid")
            val aid: Int,
            @SerializedName("argue_msg")
            val argueMsg: String,
            @SerializedName("coin")
            val coin: Int,
            @SerializedName("danmaku")
            val danmaku: Int,
            @SerializedName("dislike")
            val dislike: Int,
            @SerializedName("evaluation")
            val evaluation: String,
            @SerializedName("favorite")
            val favorite: Int,
            @SerializedName("his_rank")
            val hisRank: Int,
            @SerializedName("like")
            val like: Int,
            @SerializedName("now_rank")
            val nowRank: Int,
            @SerializedName("reply")
            val reply: Int,
            @SerializedName("share")
            val share: Int,
            @SerializedName("view")
            val view: Int,
            @SerializedName("vt")
            val vt: Int,
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
