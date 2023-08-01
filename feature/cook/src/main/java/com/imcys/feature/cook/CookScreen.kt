package com.imcys.feature.cook

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SoupKitchen
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.imcys.core.data.repository.CookFoodInfoRepository
import com.imcys.core.data.repository.CookingIngredientRepository
import com.imcys.core.database.entity.CookingIngredientEntity
import com.imcys.core.ui.PageContentColumn
import com.imcys.feature.cook.menu.CookSearchType
import dagger.hilt.android.qualifiers.ApplicationContext

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "ElevatedFilterChip",
)
@Composable
fun CookElevatedFilterChipPreview() {
    ElevatedFilterChip(
        selected = false,
        label = { Text("菜品") },
        onClick = {
        },
        leadingIcon = {
            Text(text = "🔥")
        },
        trailingIcon = {
            AsyncImage(
                ImageRequest.Builder(LocalContext.current)
                    .data("https://img1.imgtp.com/2023/07/09/TilgZLzU.png")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(15.dp),
                colorFilter = ColorFilter.tint(LocalContentColor.current)
            )
        },
    )
}


@Composable
fun CookRoute(
    modifier: Modifier = Modifier,
    viewModel: CookViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    CookScreen(
        modifier = modifier,
        viewModel = viewModel,
        viewStates = viewModel.viewStates,
        navController = navController,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookScreen(
    modifier: Modifier,
    viewModel: CookViewModel,
    viewStates: CookState,
    navController: NavHostController,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                AnimatedVisibility(
                    viewStates.isShowBottomBar,
                ) {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                fontWeight = FontWeight.W900,
                                text = "烹饪指南",
                            )
                        },
                    )
                }
            }
        },
    ) {
        PageContentColumn(Modifier.padding(it)) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                CookingIngredientScreen(viewModel, viewStates)
            }
        }
    }
}

/**
 * 这个是当前页面的内容，事实上这里本来不需要采用LazyColumn。
 * 奈何FlowRow子项太多会导致界面卡顿，因此，这里被迫采用了LazyColumn。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CookingIngredientScreen(viewModel: CookViewModel, viewStates: CookState) {
    Spacer(modifier = Modifier.height(10.dp))
    LazyColumn(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            // 留白，这里后面是重置功能
            IconButton(
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Rounded.SoupKitchen,
                    modifier = Modifier.size(150.dp),
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "\uD83E\uDD58 挑选你的食材",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        item {
            StuffFlow(
                "\uD83E\uDD6C 菜菜们",
                viewModel,
                viewStates,
                CookingIngredientEntity.VEGETABLE,
            )

            StuffFlow("\uD83E\uDD69 肉肉们", viewModel, viewStates, CookingIngredientEntity.MEAT)

            StuffFlow(
                "\uD83C\uDF5A 主食也要一起下锅吗？",
                viewModel,
                viewStates,
                CookingIngredientEntity.STAPLE,
            )

            TollFlow(
                "\uD83C\uDF73 再选一下厨具",
                viewModel,
                viewStates,
                CookingIngredientEntity.TOOL,
            )
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "\uD83C\uDF72 来看看组合出的菜谱吧！",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )

            SearchTypeScreen(
                viewModel,
                viewStates,
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                singleLine = true, // 设置为单行模式
                value = viewStates.searchName ?: "",
                textStyle = TextStyle(fontSize = 15.sp), // 调整文字大小
                shape = CardDefaults.shape,
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                onValueChange = { viewModel.sendIntent(CookIntent.InputSearchKeyword(it)) },
                placeholder = { Text(text = "过滤一下", fontSize = 15.sp) },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = "搜索图标")
                },
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "看看可以做什么？",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        items(viewStates.searchResultList) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                ElevatedFilterChip(
                    selected = false,
                    label = { Text(it.name) },
                    onClick = {
                        viewModel.sendIntent(CookIntent.ToBiliBili(it.bv))
                    },
                    leadingIcon = {
                        Text(text = it.emoji)
                    },
                    trailingIcon = {
                        AsyncImage(
                            ImageRequest.Builder(LocalContext.current)
                                .data(it.image)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.size(15.dp),
                            colorFilter = ColorFilter.tint(LocalContentColor.current)
                        )
                    },
                )
            }
        }
    }
}


@Composable
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
private fun StuffFlow(
    title: String,
    viewModel: CookViewModel,
    viewStates: CookState,
    type: Int,
) {
    Column(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            viewStates.cookingIngredientsEntity.filter { it.type == type }
                .forEach {
                    var mSelected by remember { mutableStateOf(false) }

                    ElevatedFilterChip(
                        selected = it.name in viewStates.searchStuffs,
                        label = { Text(it.name) },
                        onClick = {
                            mSelected = !mSelected
                            viewModel.sendIntent(CookIntent.SelectStuff(it.name))
                        },
                        leadingIcon = {
                            if (it.image != null) {
                                AsyncImage(
                                    it.image,
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp),
                                )
                            } else {
                                Text(text = it.emoji)
                            }
                        },
                    )
                }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchTypeScreen(
    viewModel: CookViewModel,
    viewStates: CookState,
) {
    Row(horizontalArrangement = Arrangement.Center) {
        ElevatedFilterChip(
            modifier = Modifier.padding(5.dp, 10.dp),
            selected = viewStates.searchType == CookSearchType.FUZZY_MATCHING,
            label = { Text("模糊匹配") },
            onClick = {
                viewModel.sendIntent(CookIntent.UpdateSearchType(CookSearchType.FUZZY_MATCHING))
            },
        )

        ElevatedFilterChip(
            modifier = Modifier.padding(5.dp, 10.dp),
            selected = viewStates.searchType == CookSearchType.EXACT_MATCH,
            label = { Text("完全匹配") },
            onClick = {
                viewModel.sendIntent(CookIntent.UpdateSearchType(CookSearchType.EXACT_MATCH))
            },
        )
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
private fun TollFlow(
    title: String,
    viewModel: CookViewModel,
    viewStates: CookState,
    type: Int,
) {
    Column(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.height(10.dp))
        var mSelectedIndex by remember { mutableStateOf(-1) }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            viewStates.cookingIngredientsEntity.filter { it.type == type }
                .forEachIndexed { index, cookingIngredient ->

                    ElevatedFilterChip(
                        selected = cookingIngredient.name == viewStates.searchTool,
                        label = { Text(cookingIngredient.name) },
                        onClick = {
                            if (cookingIngredient.name == viewStates.searchTool) {
                                mSelectedIndex = -1
                                viewModel.sendIntent(CookIntent.SelectTool(""))
                            } else {
                                mSelectedIndex = index
                                viewModel.sendIntent(CookIntent.SelectTool(cookingIngredient.name))
                            }
                        },
                        leadingIcon = {
                            if (cookingIngredient.image != null) {
                                AsyncImage(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data(cookingIngredient.image)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp),
                                    colorFilter = ColorFilter.tint(LocalContentColor.current)
                                )
                            } else {
                                Text(text = cookingIngredient.emoji)
                            }
                        },
                    )
                }
        }
    }
}
