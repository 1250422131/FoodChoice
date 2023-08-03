package com.imcys.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PageContentColumn(
    modifier: Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Row(modifier) {
        Spacer(modifier = Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            content.invoke(this)
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}
