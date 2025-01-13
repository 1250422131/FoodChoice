package com.imcys.foodchoice.ui.setting

import android.content.Intent
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.TripOrigin
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Web
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imcys.core.ui.BaseSettingsItem
import com.imcys.core.ui.CategorySettingsItem
import com.imcys.core.ui.WaifuBoostAlertDialog
import com.imcys.foodchoice.MainActivityIntent
import com.imcys.foodchoice.R
import com.imcys.foodchoice.ui.PrivacyPolicyDialog

@Composable
internal fun SettingRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    SettingScreen(
        modifier = modifier,
        viewModel = viewModel,
        viewState = viewModel.viewStates,
        navController = navController,
    )
}

@Composable
fun SettingScreen(
    modifier: Modifier,
    viewModel: SettingViewModel,
    viewState: SettingState,
    navController: NavHostController,
) {
    var showOriginalDialogState by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var showAgreePrivacyPolicyDialogState by remember { mutableStateOf(false) }

    LazyColumn(Modifier.fillMaxSize()) {
        item {
            CategorySettingsItem(
                text = "项目"
            )
        }
        item {
            BaseSettingsItem(
                painter = rememberVectorPainter(Icons.Default.TripOrigin),
                text = "原始项目",
                descriptionText = "本项目复刻自云游君的“cook”项目",
                onClick = {
                    showOriginalDialogState = true
                }
            )
        }
        item {
            BaseSettingsItem(
                painter = rememberVectorPainter(Icons.Default.Email),
                text = "立即投稿",
                descriptionText = "为本项目添加菜品！",
                onClick = {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://docs.qq.com/form/page/DWk9GWW9oTmlXZU9V")
                        )
                    )
                }
            )
        }
        item {
            BaseSettingsItem(
                painter = painterResource(id = R.drawable.ic_github_24),
                text = "Github",
                descriptionText = "本项持续开源维护，来点个Star？",
                onClick = {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://github.com/1250422131/FoodChoice")
                        )
                    )
                }
            )
        }
        item {
            CategorySettingsItem(
                text = "隐私"
            )
        }
        item {

            BaseSettingsItem(
                painter = rememberVectorPainter(Icons.Default.PrivacyTip),
                text = "隐私政策",
                descriptionText = "你可以随时撤销你授权的隐私政策。",
                onClick = {
                    showAgreePrivacyPolicyDialogState = true
                }
            )
        }
        item {
            CategorySettingsItem(
                text = "开发者"
            )
        }
        item {
            BaseSettingsItem(
                painter = painterResource(id = R.drawable.ic_lib_bilibili),
                text = "哔哩哔哩",
                descriptionText = "关注我，留意项目动态~",
                onClick = {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://space.bilibili.com/351201307/")
                        )
                    )
                }
            )
        }
    }


    OriginalDialog(agreePrivacyPolicy = showOriginalDialogState, onClickConfirm = {
        showOriginalDialogState = false
    })

    PrivacyPolicyDialog(
        showAgreePrivacyPolicyDialogState,
        onClickConfirm = {
            showAgreePrivacyPolicyDialogState = false
            viewModel.sendIntent(SettingIntent.SetPrivacyPolicyState(1))
        },
        onClickDismiss = {
            showAgreePrivacyPolicyDialogState = false
            viewModel.sendIntent(SettingIntent.SetPrivacyPolicyState(0))
        })
}


@Composable
fun OriginalDialog(
    agreePrivacyPolicy: Boolean,
    onClickConfirm: () -> Unit,
) {
    val dialogContent by remember {
        mutableStateOf(
            """
        该项目源自于云游君的开源项目<a href="https://github.com/YunYouJun/cook">Cook</a>，你也可以从这里使用<a href="https://cook.yunyoujun.cn/">原项目</a>，本APP目前不定期同步该项目数据资源。
    """.trimIndent()
        )
    }
    WaifuBoostAlertDialog(
        showState = agreePrivacyPolicy,
        title = { Text(text = "原始项目") },
        icon = {
            Icon(
                imageVector = Icons.Outlined.Web,
                contentDescription = null
            )
        },
        text = {
            val textColor = MaterialTheme.colorScheme.onSurface.toArgb()
            AndroidView(
                factory = { TextView(it) },
                update = {
                    val tip = dialogContent.trimIndent()
                    it.apply {
                        it.setTextColor(textColor)
                        text = HtmlCompat.fromHtml(tip, HtmlCompat.FROM_HTML_MODE_COMPACT)
                        movementMethod = LinkMovementMethod.getInstance()
                    }
                }
            )
        },
        confirmButton = {
            TextButton(onClick = onClickConfirm) {
                Text(text = "好的")
            }
        }
    )
}
