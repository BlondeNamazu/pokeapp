package com.example.pokeapp.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.collect

/**
 * from https://qiita.com/hiesiea/items/56c350ee1c8a445146ee
 * リストの一番最後に到達した際、追加読み込みなどを行うための処理を発火する
 * @param onAppearLastItem リストの一番最後に到達した際に呼ばれる関数
 */
@Composable
fun LazyListState.OnAppearLastItem(onAppearLastItem: (Int) -> Unit) {
    val isReachedToListEnd by remember {
        derivedStateOf {
            // 追加読み込みを行う条件
            // 1. アイテム総数が画面に収まりきらないほどある
            // 2. リストの下端までスクロールされた
            layoutInfo.visibleItemsInfo.size < layoutInfo.totalItemsCount &&
                    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { isReachedToListEnd }
            .filter { it }
            .collect {
                onAppearLastItem(layoutInfo.totalItemsCount)
            }
    }
}