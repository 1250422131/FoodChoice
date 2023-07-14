package com.imcys.foodchoice.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imcys.core.ui.HomeFunctionCard

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    HomeScreen(
        modifier = modifier,
        viewModel = viewModel,
        viewState = viewModel.viewStates,
        navController = navController,
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    viewState: HomeState,
    navController: NavHostController,
) {
    Column(modifier = modifier) {
        val lazyListState = rememberLazyListState()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            state = lazyListState,
        ) {
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
}
