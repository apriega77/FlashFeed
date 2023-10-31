package flashfeed.presentation.home.dashboard.headlines

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import flashfeed.model.home.Article
import flashfeed.presentation.base.R
import flashfeed.presentation.base.component.image.WidgetImage
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeadlinesView(articles: List<Article>, onArticleClicked: (Article) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp),
    ) {
        val state = rememberPagerState()
        if (articles.isNotEmpty()){
            LaunchedEffect(Unit) {
                while (true) {
                    Thread.yield()
                    delay(1000)
                    tween<Float>(1000)
                    state.animateScrollToPage(
                        page = (state.currentPage + 1),
                    )
                }
            }
        }


        HorizontalPager(
            pageCount = articles.size,
            modifier = Modifier.aspectRatio(1f / 1f),
            state = state,
            pageSpacing = 20.dp
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxSize()
                    .clickable {
                        onArticleClicked(articles[it])
                    }
                    .graphicsLayer {
                        val pageOffset = (
                            (state.currentPage - it) + state
                                .currentPageOffsetFraction
                            ).absoluteValue

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f),
                        )
                    },
            ) {
                val (authorRef, titleRef) = createRefs()


                WidgetImage(
                    image = articles[it].urlToImage,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )


                Text(
                    text = articles[it].author,
                    modifier = Modifier.constrainAs(authorRef) {
                        bottom.linkTo(titleRef.top, 16.dp)
                        start.linkTo(parent.start, 20.dp)
                        end.linkTo(parent.end, 20.dp)
                        width = Dimension.fillToConstraints
                    },
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Start,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = articles[it].title,
                    modifier = Modifier.constrainAs(titleRef) {
                        bottom.linkTo(parent.bottom, 20.dp)
                        start.linkTo(parent.start, 20.dp)
                        end.linkTo(parent.end, 20.dp)
                        width = Dimension.fillToConstraints
                    },
                    style = TextStyle(
                        fontSize = 28.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Start,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

            }

        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(articles.size) { iteration ->
                val color = if (state.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp),
                )
            }
        }
    }


}
