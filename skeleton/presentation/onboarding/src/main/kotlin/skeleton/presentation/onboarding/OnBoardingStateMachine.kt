package skeleton.presentation.onboarding

import base.model.NoArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import skeleton.presentation.base.BaseAppsUseCaseErrorMapper
import skeleton.presentation.base.BaseStateMachine
import skeleton.usecase.onboarding.GetWelcomePageUseCase
import javax.inject.Inject

@HiltViewModel
class OnBoardingStateMachine @Inject constructor(
    override var errorMapper: BaseAppsUseCaseErrorMapper,
    private val getWelcomePageUseCase: GetWelcomePageUseCase,
) : BaseStateMachine<OnBoardingState, OnBoardingEvent, NoArgs>() {

    override fun getInitialState(): OnBoardingState {
        return OnBoardingState.Loaded(listOf())
    }

    override fun mapEvent(event: OnBoardingEvent, lastState: OnBoardingState) {
        when (event) {
            OnBoardingEvent.GetWelcomePages -> {
                launcher.launch(getWelcomePageUseCase) {
                    state.setValue(OnBoardingState.Loaded(it))
                }
            }
        }
    }
}
