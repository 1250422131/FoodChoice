package com.imcys.feature.cook.ui.info

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.imcys.core.ui.CookInfoVideoCard
import com.imcys.core.ui.PageContentColumn

private val LocalViewModel = compositionLocalOf<CookInfoViewModel> { error("No init!") }
private val LocalViewState = compositionLocalOf<CookInfoState> { error("No init!") }
private val LocalNavController = compositionLocalOf<NavHostController> { error("No init!") }

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
    CompositionLocalProvider(
        LocalViewModel provides viewModel,
        LocalViewState provides viewModel.viewStates,
        LocalNavController provides navController,
    ) {
        CookInfoScreen(modifier = modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CookInfoScreen(
    modifier: Modifier,
) {
    val navController = LocalNavController.current
    val viewModel = LocalViewModel.current
    val viewStates = LocalViewState.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                            IconButton(onClick = {
                                navController.navigateUp()
                            }) {
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
        PageContentColumn(Modifier.padding(top = it.calculateTopPadding())) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                CookInfoContent()
            }
        }
    }
}

@Composable
private fun CookInfoContent() {
    val viewModel = LocalViewModel.current
    val viewStates = LocalViewState.current

    LazyColumn(Modifier.fillMaxWidth()) {
        viewStates.foodVideoInfo?.data?.apply {
            item {
                CookInfoVideoCard(
                    title = title,
                    content = desc,
                    thumbnailUrl = pic,
                    avatarUrl = owner.face,
                    duration = duration,
                    view = stat.view,
                    modifier = Modifier.clickable {
                        viewModel.sendIntent(CookInfoIntent.ToBiliBiliPlay(bvid))
                    },
                )
            }
        }
    }
}
