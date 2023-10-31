package flashfeed.presentation.home.dashboard

import flashfeed.model.home.Article

sealed interface DashboardState {
    data object Loading : DashboardState
    data class Loaded(val headlines: List<Article>, val recommendation: List<Article>) :
        DashboardState
}

sealed interface DashboardEvent {
    data object GetArticles : DashboardEvent
    data class ArticleClicked(val title: String, val link: String) : DashboardEvent
}

sealed interface DashboardEffect {
    data class GoToWebView(val title: String, val link: String) : DashboardEffect
}

