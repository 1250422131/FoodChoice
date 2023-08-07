package com.imcys.core.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.imcys.core.common.extend.digitalConversion

@Preview
@Composable
fun CookInfoVideoCard(
    title: String = "食用手册",
    content: String = "不知道做什么菜？来这里看看",
    thumbnailUrl: String = "https://img1.imgtp.com/2023/07/08/z0tUwxLu.jpg",
    avatarUrl: String = "https://img1.imgtp.com/2023/07/08/z0tUwxLu.jpg",
    duration: Long = 0,
    view: Long = 0,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        // 产生上下结构
        Column(Modifier.fillMaxWidth().padding(10.dp)) {
            // 产生视频封面预览
            CookInfoVideoCardThumbnai(thumbnailUrl, duration)
            // 产生左右结构
            CookInfoVideoCardAvatar(avatarUrl, title, content, view)
        }
    }
}

@Composable
private fun CookInfoVideoCardThumbnai(thumbnailUrl: String, duration: Long) {
    Surface(Modifier.fillMaxWidth(), shape = CardDefaults.shape) {
        // 让视频前可以浮动一个视频时长
        Box(Modifier.fillMaxWidth().height(180.dp)) {
            SubcomposeAsyncImage(
                ImageRequest.Builder(LocalContext.current)
                    .data(thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.unnamed),
                        contentDescription = null,
                    )
                },
            )
            Column(
                Modifier.fillMaxSize().padding(10.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End,
            ) {
                Surface(shape = RoundedCornerShape(5.dp), color = Color.Black) {
                    Text(
                        text = run {
                            val minutes = duration / 60
                            val remainingSeconds = duration % 60
                            "%02d:%02d".format(minutes, remainingSeconds)
                        },
                        color = Color.White,
                        modifier = Modifier.padding(6.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun CookInfoVideoCardAvatar(avatarUrl: String, title: String, content: String, view: Long) {
    Spacer(Modifier.height(10.dp))
    Row(Modifier.fillMaxWidth().height(60.dp)) {
        Surface(
            shape = CircleShape,
        ) {
            SubcomposeAsyncImage(
                ImageRequest.Builder(LocalContext.current)
                    .data(avatarUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp),
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.unnamed),
                        contentDescription = null,
                    )
                },
            )
        }
        Spacer(Modifier.width(10.dp))

        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = title,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = content,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${view.digitalConversion()}  Views",
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    fontSize = 8.sp,
                )
            }
        }
    }
}
