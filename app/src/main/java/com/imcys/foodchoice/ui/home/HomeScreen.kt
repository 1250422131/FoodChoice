package com.imcys.foodchoice.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imcys.core.ui.HomeFunctionCard
import com.imcys.core.ui.base.getWidthSizeClass

private val LocalViewModel = compositionLocalOf<HomeViewModel> { error("No init!") }
private val LocalViewState = compositionLocalOf<HomeState> { error("No init!") }
private val LocalNavController = compositionLocalOf<NavHostController> { error("No init!") }

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    CompositionLocalProvider(
        LocalViewModel provides viewModel,
        LocalViewState provides viewModel.viewStates,
        LocalNavController provides navController,
    ) {
        HomeScreen(modifier)
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val navController = LocalNavController.current
    val viewState = LocalViewState.current

    LazyVerticalGrid(

        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp, 0.dp, 16.dp, 0.dp)
            .fillMaxHeight(),
        contentPadding = PaddingValues(10.dp),
        columns = GridCells.Fixed(
            when (getWidthSizeClass()) {
                WindowWidthSizeClass.Compact -> 1
                WindowWidthSizeClass.Medium -> 2
                WindowWidthSizeClass.Expanded -> 3
                else -> 3
            },
        ),
        reverseLayout = false,
    ) {
        items(viewState.homeItems) {
            HomeFunctionCard(
                it.title,
                it.content,
                it.faceUrl,
                Modifier
                    .clickable {
                        navController.navigate(it.route)
                    },
            )
        }
    }
}
