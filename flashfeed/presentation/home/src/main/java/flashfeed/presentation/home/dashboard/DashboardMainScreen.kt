package flashfeed.presentation.home.dashboard

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import flashfeed.model.base.Callback
import flashfeed.model.core.WebViewProperties
import flashfeed.presentation.base.R
import flashfeed.presentation.base.component.loading.WidgetOverlayLoading
import flashfeed.presentation.home.HomeActivity
import flashfeed.presentation.home.dashboard.article.ArticleItemView
import flashfeed.presentation.home.dashboard.headlines.HeadlinesView
import kotlinx.coroutines.flow.collect

@Composable
fun DashboardMainScreen() {
    val stateMachine: DashboardStateMachine = hiltViewModel()
    val state by stateMachine.state.collectAsState()
    val context = LocalContext.current as HomeActivity

    LaunchedEffect(key1 = Unit) {
        stateMachine.sendEvent(DashboardEvent.GetArticles)
        stateMachine.effect.collect {
            when (it) {
                is DashboardEffect.GoToWebView -> {
                    context.router.goToWebView(WebViewProperties(url = it.link, title = it.title))
                }
            }
        }
    }

    Crossfade(targetState = state, label = "dashboard animation") { dashboardState ->
        when (dashboardState) {
            DashboardState.Loading -> {
                WidgetOverlayLoading(isVisible = true)
            }

            is DashboardState.Loaded -> {
                DashboardScreen(state = dashboardState) {
                    stateMachine.sendEvent(it)
                }
            }
        }
    }

}

@Composable
private fun DashboardScreen(state: DashboardState.Loaded, event: Callback<DashboardEvent, Unit>) {
    val callback by rememberUpdatedState(newValue = event)

    LazyColumn {
        item {
            HeadlinesView(articles = state.headlines) {
                callback(DashboardEvent.ArticleClicked(it.title, it.url))
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Recommendation",
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp)
                        .align(Alignment.CenterVertically),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Start,
                    ),
                )


            }
        }

        items(state.recommendation.size) { it ->
            ArticleItemView(article = state.recommendation[it]) {
                callback(DashboardEvent.ArticleClicked(it.title, it.url))
            }
        }
    }

}