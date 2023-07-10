package skeleton.presentation.onboarding

import skeleton.model.onboarding.WelcomePageModel

sealed class OnBoardingState {
    abstract val welcomePages: List<WelcomePageModel>

    data class Loaded(override val welcomePages: List<WelcomePageModel>) : OnBoardingState()
}

sealed class OnBoardingEvent {
    data object GetWelcomePages : OnBoardingEvent()
}
