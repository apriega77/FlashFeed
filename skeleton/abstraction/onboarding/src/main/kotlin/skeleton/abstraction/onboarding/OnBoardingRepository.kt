package skeleton.abstraction.onboarding

import skeleton.model.onboarding.WelcomePageModel

interface OnBoardingRepository {
    suspend fun getWelcomePage(): List<WelcomePageModel>
}
