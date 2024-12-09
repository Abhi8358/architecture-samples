package com.example.android.architecture.blueprints.todoapp.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/10.png
//https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/11.png
//https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/12.png

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerCarousel(
    imageUrls: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { imageUrls.size })
    val isScrollingForword = remember {
        mutableStateOf(true)
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        while (true) {
            delay(2000)
            coroutineScope.launch {

                //println("next page before change ${pagerState.currentPage}   isScrollingForword $isScrollingForword")

                val nextPage = if (isScrollingForword.value) {
                    pagerState.currentPage + 1
                } else {
                    pagerState.currentPage - 1
                }

               // println("next page after change $nextPage  imageUrl size ${imageUrls.size}  isScrollingForword $isScrollingForword")
                pagerState.animateScrollToPage(nextPage)
                if (nextPage == imageUrls.size - 1) {
                    isScrollingForword.value = false
                } else if (nextPage == 0) {
                    isScrollingForword.value = true
                }
            }
        }
    }

    Column(modifier = modifier) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth().weight(1f)
        ) { page ->

            AsyncImage(
                model = imageUrls[page],
                contentDescription = "Banner $page",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .padding(top = 4.dp)
        ) {
            repeat(imageUrls.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 12.dp else 8.dp)
                        .background(
                            color = if (isSelected) Color.Black else Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Preview
@Composable
fun BannerCarouselPrev() {
    BannerCarousel(
        imageUrls = listOf(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/10.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/11.png",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/12.png"
        )
    )
}
