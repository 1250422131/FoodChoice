package com.imcys.foodchoice

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.imcys.core.common.viewmodel.ui.BaseComponentActivity
import com.imcys.core.designsystem.theme.FoodChoiceTheme
import com.imcys.foodchoice.ui.FoodApp
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseComponentActivity<MainActivity>() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        // 统计接入
        val shardedPreferences: SharedPreferences =
            getSharedPreferences("app_config", Context.MODE_PRIVATE)
        val privacyPolicyState = shardedPreferences.getInt("privacy_policy_state", -1)
        if (privacyPolicyState == 1) {
            AppCenter.start(
                application,
                "0391335a-2bae-4bef-ae0a-c23f592a7613",
                Analytics::class.java,
                Crashes::class.java,
                Distribute::class.java
            )
        } else {
            AppCenter.start(
                application,
                "0391335a-2bae-4bef-ae0a-c23f592a7613",
                Distribute::class.java
            )
        }

        setContent {
            FoodChoiceTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),

                    ) {
                    FoodApp(viewModel)
                }
            }
        }
    }
}
