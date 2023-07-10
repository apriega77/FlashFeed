package skeleton.data.onboarding.network

import retrofit2.http.GET
import skeleton.data.base.network.model.BaseResponse
import skeleton.data.onboarding.network.model.response.WelcomePageResponse

interface OnBoardingService {

    @GET("platform/config-collection/welcome-page")
    suspend fun getWelcomePageData(): BaseResponse<List<WelcomePageResponse>>
}
