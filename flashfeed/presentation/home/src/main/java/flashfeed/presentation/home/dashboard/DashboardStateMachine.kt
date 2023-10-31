package flashfeed.presentation.home.dashboard

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import flashfeed.model.home.ArticleRequest
import flashfeed.presentation.base.BaseAppsUseCaseErrorMapper
import flashfeed.presentation.base.BaseStateMachine
import flashfeed.usecase.home.GetArticlesUseCase
import javax.inject.Inject

@HiltViewModel
class DashboardStateMachine @Inject constructor(
    override var errorMapper: BaseAppsUseCaseErrorMapper,
    private val getArticlesUseCase: GetArticlesUseCase,
) :
    BaseStateMachine<DashboardState, DashboardEvent, DashboardEffect>() {
    override fun getInitialState(): DashboardState {
        return DashboardState.Loading
    }

    override fun mapEvent(event: DashboardEvent, lastState: DashboardState) {
        when (event) {
            is DashboardEvent.ArticleClicked -> {
                effect.setValue(DashboardEffect.GoToWebView(event.title, event.link))
            }

            is DashboardEvent.GetArticles -> {
                launcher.launch(getArticlesUseCase, ArticleRequest()) {
                    state.setValue(
                        DashboardState.Loaded(
                            headlines = it.slice(0..5),
                            recommendation = it.slice(6..it.lastIndex),
                        ),
                    )
                }
            }
        }
    }
}