package skeleton.data.onboarding.datasource

import base.model.Image
import skeleton.abstraction.onboarding.OnBoardingRemoteDataSource
import skeleton.data.onboarding.network.OnBoardingService
import skeleton.model.onboarding.WelcomePageModel
import javax.inject.Inject

class OnBoardingRemoteDataSourceImpl @Inject constructor(
    private val onBoardingService: OnBoardingService,
) : OnBoardingRemoteDataSource {
    override suspend fun getWelcomePages(): List<WelcomePageModel> {
        val resp = onBoardingService.getWelcomePageData()
        val data = resp.data ?: throw Exception()
        return data.map {
            WelcomePageModel(
                it.title.orEmpty(),
                it.description.orEmpty(),
                Image.Url(it.image.orEmpty()),
            )
        }
    }
}
