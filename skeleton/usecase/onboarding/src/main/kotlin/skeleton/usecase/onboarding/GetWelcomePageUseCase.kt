package skeleton.usecase.onboarding

import base.model.NoArgs
import base.usecase.BaseUseCaseAsync
import skeleton.abstraction.onboarding.OnBoardingRepository
import skeleton.model.onboarding.WelcomePageModel

class GetWelcomePageUseCase(private val onboardingRepository: OnBoardingRepository) :
    BaseUseCaseAsync<NoArgs, List<WelcomePageModel>>() {
    override suspend fun build(args: NoArgs): List<WelcomePageModel> {
        return onboardingRepository.getWelcomePage()
    }
}
