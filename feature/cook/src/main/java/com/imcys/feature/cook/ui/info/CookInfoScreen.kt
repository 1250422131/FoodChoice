package com.imcys.feature.cook.ui.info

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.imcys.core.ui.PageContentColumn

@Composable
fun CookInfoRoute(
    bvId: String,
    modifier: Modifier = Modifier,
    viewModel: CookInfoViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    LaunchedEffect(Unit) {
        viewModel.sendIntent(CookInfoIntent.LoadFoodVideoInfo(bvId = bvId))
    }
    CookInfoScreen(
        modifier = modifier,
        viewModel = viewModel,
        viewStates = viewModel.viewStates,
        navController = navController,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookInfoScreen(
    modifier: Modifier,
    viewModel: CookInfoViewModel,
    viewStates: CookInfoState,
    navController: NavHostController,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                AnimatedVisibility(
                    viewStates.isShowBottomBar,
                ) {
                    LargeTopAppBar(
                        scrollBehavior = scrollBehavior,
                        title = {
                            Text(
                                fontWeight = FontWeight.W900,
                                text = "详细信息",
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowBack,
                                    contentDescription = null,
                                )
                            }
                        },
                    )
                }
            }
        },
    ) {
        PageContentColumn(Modifier.padding(it)) {
            Column(
                Modifier.fillMaxSize().padding(16.dp, 0.dp, 16.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            }
        }
    }
}
