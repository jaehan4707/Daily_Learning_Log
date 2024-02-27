```kotlin
package com.jaehan.praticecompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaehan.praticecompose.model.Cat
import com.jaehan.praticecompose.model.Datasource
import com.jaehan.praticecompose.ui.theme.PraticeComposeTheme
import com.jaehan.praticecompose.ui.theme.Typography

@Composable
fun RecyclerScreen(modifier: Modifier = Modifier) {
    val cats = remember { Datasource.cats }
    val scrollState = rememberLazyListState()
    Scaffold(topBar = { topBar() }) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
        ) {
            LazyColumn(
                state = scrollState,
            ) {
                items(
                    items = cats,
                    itemContent = { CatsRow(cat = it) },
                )
            }
        }
    }
}

@Composable
fun CatsRow(cat: Cat) {
    Row {
        CatsThumbnail(thumbnail = cat.image)
        Column {
            Text(text = cat.name, style = Typography.headlineLarge)
            Text(text = cat.content, style = Typography.bodyMedium)
        }
    }
}

@Composable
fun topBar() {
    Row(
        modifier = Modifier.padding(5.dp).fillMaxWidth()
            .background(color = Color.Gray),
    ) {
        Text(
            text = "topBar",
            style = Typography.headlineLarge,
        )
    }
}

@Composable
fun CatsThumbnail(thumbnail: Int) {
    Image(
        painter = painterResource(id = thumbnail),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.padding(8.dp).size(100.dp).clip(CircleShape),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecycleViewPreview() {
    val cats = Datasource.cats
    PraticeComposeTheme {
        RecyclerScreen()
    }
}

```

