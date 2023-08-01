package com.imcys.core.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Preview
@Composable
fun HomeFunctionCard(
    title: String = "食用手册",
    content: String = "不知道做什么菜？来这里看看",
    faceUrl: String = "https://img1.imgtp.com/2023/07/08/z0tUwxLu.jpg",
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
        .fillMaxWidth()
        .clickable { },
) {
    Card(
        modifier = modifier,
    ) {
        Column(Modifier.fillMaxWidth()) {
            Surface(Modifier.fillMaxWidth(), shape = CardDefaults.shape) {
                SubcomposeAsyncImage(
                    ImageRequest.Builder(LocalContext.current)
                        .data(faceUrl)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(124.dp),
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.unnamed),
                            contentDescription = null,
                        )
                    },
                )
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            ) {
                Text(
                    text = title, fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = content,
                    fontSize = 12.sp,
                )
            }
        }
    }
}
