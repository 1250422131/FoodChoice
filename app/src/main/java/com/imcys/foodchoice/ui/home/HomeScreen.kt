package com.imcys.foodchoice.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imcys.core.ui.HomeFunctionCard

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

    LazyColumn(
        modifier = modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        items(viewState.homeItems) {
            HomeFunctionCard(
                it.title,
                it.content,
                it.faceUrl,
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(it.route)
                    },
            )
        }
    }
}
