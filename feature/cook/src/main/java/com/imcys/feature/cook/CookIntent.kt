package com.imcys.feature.cook

import com.imcys.core.common.viewmodel.info.UiIntent
import com.imcys.feature.cook.menu.CookSearchType

open class CookIntent : UiIntent {
    data class SelectStuff(val stuff: String) : CookIntent()
    data class SelectTool(val tool: String) : CookIntent()
    data class InputSearchKeyword(val name: String) : CookIntent()
    data class ToBiliBili(val bvId: String) : CookIntent()
    data class UpdateSearchType(val type: CookSearchType) : CookIntent()
}
