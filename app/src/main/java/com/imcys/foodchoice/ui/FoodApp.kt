package com.imcys.foodchoice.ui

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.imcys.core.common.utils.VibrationUtils
import com.imcys.foodchoice.MainActivityIntent
import com.imcys.foodchoice.MainActivityState
import com.imcys.foodchoice.MainActivityViewModel
import com.imcys.foodchoice.navigation.FCNavHost
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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
private fun FoodAppScreen() {
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
    ) { 2 }

    val navController = LocalNavController.current
    val viewModel = LocalViewModel.current
    val viewStates = LocalViewState.current

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
            appTopBar(viewStates, scrollBehavior, context)
        },
        bottomBar = {
            appBottomBar(viewStates, viewModel, scope, pageState)
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            Spacer(modifier = Modifier.width(10.dp))

            FCNavHost(
                navController = navController,
                modifier = Modifier.fillMaxSize(),
                pageState = pageState,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun appTopBar(
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
                    AnimatedVisibility(
                        visible = viewStates.titleState,
                        enter = slideInVertically(initialOffsetY = { -it }),
                        exit = slideOutVertically(targetOffsetY = { -it }),
                    ) {
                        Text(
                            overflow = TextOverflow.Ellipsis,
                            text = viewStates.run { navItems[navItemIndex].label },
                        )
                    }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun appBottomBar(
    viewStates: MainActivityState,
    mainActivityViewModel: MainActivityViewModel,
    scope: CoroutineScope,
    pageState: PagerState,
) {
    Column {
        AnimatedVisibility(
            viewStates.isShowBottomBar,
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
