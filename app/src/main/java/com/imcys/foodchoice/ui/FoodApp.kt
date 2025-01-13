package com.imcys.foodchoice.ui

import android.content.Context
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.imcys.core.common.utils.VibrationUtils
import com.imcys.core.ui.WaifuBoostAlertDialog
import com.imcys.core.ui.base.getWidthSizeClass
import com.imcys.foodchoice.MainActivityIntent
import com.imcys.foodchoice.MainActivityState
import com.imcys.foodchoice.MainActivityViewModel
import com.imcys.foodchoice.R
import com.imcys.foodchoice.navigation.FCNavHost
import com.imcys.foodchoice.weight.Konfetti
import com.imcys.foodchoice.weight.rememberKonfettiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val LocalViewModel = compositionLocalOf<MainActivityViewModel> { error("No init!") }
private val LocalViewState = compositionLocalOf<MainActivityState> { error("No init!") }
private val LocalNavController = compositionLocalOf<NavHostController> { error("No init!") }

@Composable
fun FoodApp(
    mainActivityViewModel: MainActivityViewModel,
) {
    val viewStates = mainActivityViewModel.viewStates

    // 全局路由
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalViewModel provides mainActivityViewModel,
        LocalViewState provides viewStates,
        LocalNavController provides navController,
    ) {
        FoodAppScreen()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun FoodAppScreen() {
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
    ) { 2 }

    val navController = LocalNavController.current
    val viewModel = LocalViewModel.current
    val viewStates = LocalViewState.current
    val konfettiState = rememberKonfettiState(false)

    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.route) {
            "app_index" -> {
                viewModel.sendIntent(MainActivityIntent.SetShowBottomBar(true))
            }

            else -> viewModel.sendIntent(MainActivityIntent.SetShowBottomBar(false))
        }
    }

    val context = LocalContext.current

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    FullScreenScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBar(viewStates, scrollBehavior, context)
        },
        bottomBar = {
            AppBottomBar(viewStates, viewModel, scope, pageState)
        },

        ) {
        Row(modifier = Modifier.padding(it)) {
            AppNavigationRail(viewStates, viewModel, scope, pageState)
            if (getWidthSizeClass() == WindowWidthSizeClass.Expanded) {
                AppPermanentNavigationDrawer(viewStates, viewModel, scope, pageState, navController)
            } else {
                AppContent(navController, pageState)
            }
        }
    }

    PrivacyPolicyDialog(
        viewStates.privacyPolicyState == -1,
        onClickConfirm = {
            viewModel.sendIntent(MainActivityIntent.SetPrivacyPolicyState(1))
            konfettiState.value = true
        },
        onClickDismiss = {
            viewModel.sendIntent(MainActivityIntent.SetPrivacyPolicyState(0))
        })

    Konfetti(
        state = konfettiState,
        Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppContent(
    navController: NavHostController,
    pageState: PagerState,
) {
    Column {
        Spacer(modifier = Modifier.width(10.dp))

        FCNavHost(
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            pageState = pageState,
        )
    }
}

@Composable
fun AppPermanentNavigationDrawer(
    viewStates: MainActivityState,
    mainActivityViewModel: MainActivityViewModel,
    scope: CoroutineScope,
    pageState: PagerState,
    navController: NavHostController,
) {
    PermanentNavigationDrawer(
        modifier = Modifier
            .fillMaxHeight(),
        drawerContent = {
            Row {
                AnimatedVisibility(
                    viewStates.isShowBottomBar && getWidthSizeClass() == WindowWidthSizeClass.Expanded,
                ) {
                    PermanentDrawerSheet {
                        viewStates.navItems.forEachIndexed { index, navItem ->
                            NavigationDrawerItem(
                                modifier = Modifier.padding(10.dp),
                                icon = {
                                    Icon(
                                        imageVector =
                                        if (viewStates.navItemIndex == index) navItem.checked else navItem.unchecked,
                                        contentDescription = null,
                                    )
                                },
                                label = {
                                    Text(text = navItem.label)
                                },
                                selected = viewStates.navItemIndex == index,
                                onClick = {
                                    mainActivityViewModel.sendIntent(
                                        MainActivityIntent.SelectNavItem(
                                            index,
                                        ),
                                    )
                                    scope.launch { pageState.scrollToPage(index) }
                                },
                            )
                        }
                    }
                }
            }
        },
        content = {
            AppContent(navController, pageState)
        },
    )
}

@Composable
private fun AppNavigationRail(
    viewStates: MainActivityState,
    mainActivityViewModel: MainActivityViewModel,
    scope: CoroutineScope,
    pageState: PagerState,
) {
    Row {
        AnimatedVisibility(
            viewStates.isShowBottomBar && getWidthSizeClass() == WindowWidthSizeClass.Medium,
        ) {
            NavigationRail {
                viewStates.navItems.forEachIndexed { index, navItem ->
                    NavigationRailItem(
                        icon = {
                            Icon(
                                imageVector =
                                if (viewStates.navItemIndex == index) navItem.checked else navItem.unchecked,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(text = navItem.label)
                        },
                        selected = viewStates.navItemIndex == index,
                        onClick = {
                            mainActivityViewModel.sendIntent(
                                MainActivityIntent.SelectNavItem(
                                    index,
                                ),
                            )
                            scope.launch {
                                pageState.scrollToPage(index)
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AppTopBar(
    viewStates: MainActivityState,
    scrollBehavior: TopAppBarScrollBehavior,
    context: Context,
) {
    Column {
        AnimatedVisibility(
            viewStates.isShowBottomBar,
        ) {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        overflow = TextOverflow.Ellipsis,
                        text = viewStates.run { navItems[navItemIndex].label },
                    )
                },
                actions = {
                    IconButton(onClick = {
                        VibrationUtils.performHapticFeedback(context)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = null,
                        )
                    }
                },
            )
        }
    }
}

@Composable
private fun AppBottomBar(
    viewStates: MainActivityState,
    mainActivityViewModel: MainActivityViewModel,
    scope: CoroutineScope,
    pageState: PagerState,
) {
    Column {
        AnimatedVisibility(
            viewStates.isShowBottomBar && getWidthSizeClass() == WindowWidthSizeClass.Compact,
        ) {
            NavigationBar {
                viewStates.navItems.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = viewStates.navItemIndex == index,
                        onClick = {
                            mainActivityViewModel.sendIntent(
                                MainActivityIntent.SelectNavItem(
                                    index,
                                ),
                            )
                            scope.launch { pageState.scrollToPage(index) }
                        },
                        icon = {
                            Icon(
                                imageVector =
                                if (viewStates.navItemIndex == index) navItem.checked else navItem.unchecked,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(text = navItem.label)
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun FullScreenScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable (PaddingValues) -> Unit,
) {
    TransparentSystemBars()

    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        content = content,
    )
}

@Composable
fun TransparentSystemBars() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    val mColor = Color.Transparent

    SideEffect {
        systemUiController.setStatusBarColor(
            color = mColor,
            darkIcons = useDarkIcons,
        )
    }
}

@Composable
fun PrivacyPolicyDialog(
    agreePrivacyPolicy: Boolean,
    onClickConfirm: () -> Unit,
    onClickDismiss: () -> Unit,
) {
    WaifuBoostAlertDialog(
        showState = agreePrivacyPolicy,
        title = { Text(text = "隐私政策") },
        icon = {
            Icon(
                imageVector = Icons.Outlined.PrivacyTip,
                contentDescription = "隐私政策"
            )
        },
        text = {
            val textColor = MaterialTheme.colorScheme.onSurface.toArgb()

            AndroidView(
                factory = { TextView(it) },
                update = {
                    val tip =
                        "食选目前并未接入自己的独立后台，因此需要借助第三方平台实现更新通知，目前使用的是微软的appcenter，它会匿名的上传APP使用数据，如地区、手机型号等，如果你拒绝这个数据收集，可能会导致APP无法收到更新，拒绝后你可以在B站关注我持续获得更新通知。".trimIndent()
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
                Text(text = "接受")
            }
        },
        dismissButton = {
            TextButton(onClick = onClickDismiss) {
                Text(text = "拒绝")
            }
        }
    )
}

