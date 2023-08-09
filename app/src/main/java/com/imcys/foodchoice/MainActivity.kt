package com.imcys.foodchoice

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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

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
