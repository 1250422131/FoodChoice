package com.imcys.feature.cook.ui.info

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.imcys.core.common.viewmodel.ComposeBaseViewModel
import com.imcys.core.data.repository.cook.CookFoodInfoRepository
import com.imcys.core.network.retrofit.RetrofitAppNetwork.bilibiliApi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class CookInfoViewModel @Inject constructor(
    private val cookFoodInfoRepository: CookFoodInfoRepository,
    @ApplicationContext private val context: Context,

) : ComposeBaseViewModel<CookInfoState, CookInfoIntent>(CookInfoState()) {
    override fun handleEvent(event: CookInfoIntent, state: CookInfoState) {
        when (event) {
            is CookInfoIntent.LoadFoodVideoInfo -> {
                launchUI { loadFoodVideoInfo(event.bvId) }
            }

            is CookInfoIntent.ToBiliBiliPlay -> {
                launchUI { toBiliBiliPlay(event.bvId) }
            }
        }
    }

    /**
     * 跳转B站播放视频
     * @param bvId String
     */
    private fun toBiliBiliPlay(bvId: String) {
        val packName = "tv.danmaku.bili"
        context.packageManager.getLaunchIntentForPackage(packName)?.apply {
            // 组装跳转
            val url = "bilibili://video/$bvId"
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).also {
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(it)
            }

            Toast.makeText(context, "芜湖，前往B站", Toast.LENGTH_SHORT).show()
        } ?: apply {
            Toast.makeText(context, "呜哇，还没有装B站", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun loadFoodVideoInfo(bvId: String) {
        // 获取数据
        bilibiliApi.getVideoInfo(bvId).apply {
            viewStates.update { copy(foodVideoInfo = this@apply) }
        }

        cookFoodInfoRepository.getCookingFood(bvId)
    }

    init {
        // appbar动画
        launchUI {
            delay(200L)
            viewStates.update { copy(isShowBottomBar = true) }
        }
    }
}
