package com.imcys.foodchoice.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.imcys.foodchoice.MainActivityIntent
import com.imcys.foodchoice.MainActivityViewModel
import com.imcys.foodchoice.navigation.FCNavHost
import com.imcys.foodchoice.navigation.homeRoute
import com.imcys.foodchoice.navigation.navigateToHome
import com.imcys.foodchoice.navigation.navigateToSetting
import com.imcys.foodchoice.navigation.settingRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodApp(
    mainActivityViewModel: MainActivityViewModel,
    navController: NavHostController,
) {
    val viewStates = mainActivityViewModel.viewStates

    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.route) {
            homeRoute -> mainActivityViewModel.sendIntent(MainActivityIntent.SetShowBottomBar(true))
            settingRoute -> mainActivityViewModel.sendIntent(
                MainActivityIntent.SetShowBottomBar(
                    true,
                ),
            )

            else -> mainActivityViewModel.sendIntent(MainActivityIntent.SetShowBottomBar(false))
        }
    }

    FullScreenScaffold(
        topBar = {
            Column {
                AnimatedVisibility(
                    viewStates.isShowBottomBar,
                ) {
                    CenterAlignedTopAppBar(
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
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = null,
                                )
                            }
                        },

                    )
                }
            }
        },
        bottomBar = {
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
                                    when (index) {
                                        0 -> navController.navigateToHome()
                                        1 -> navController.navigateToSetting()
                                    }
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
        },
    ) {
        Spacer(modifier = Modifier.width(5.dp))
        Row(modifier = Modifier.padding(it)) {
            FCNavHost(navController = navController, modifier = Modifier.weight(1f))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
