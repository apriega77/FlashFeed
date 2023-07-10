package skeleton.abstraction.onboarding

import skeleton.model.onboarding.WelcomePageModel

interface OnBoardingRemoteDataSource {
    suspend fun getWelcomePages(): List<WelcomePageModel>
}
