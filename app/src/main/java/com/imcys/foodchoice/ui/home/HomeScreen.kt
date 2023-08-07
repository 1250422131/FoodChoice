package com.imcys.foodchoice.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imcys.core.ui.HomeFunctionCard

@OptIn(ExperimentalMaterial3Api::class)
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
    LazyColumn(
        modifier = modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
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
