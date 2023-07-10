package skeleton.data.onboarding.repository

import skeleton.abstraction.onboarding.OnBoardingRemoteDataSource
import skeleton.abstraction.onboarding.OnBoardingRepository
import skeleton.model.onboarding.WelcomePageModel
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    private val remote: OnBoardingRemoteDataSource,
) : OnBoardingRepository {
    override suspend fun getWelcomePage(): List<WelcomePageModel> {
        return remote.getWelcomePages()
    }
}
