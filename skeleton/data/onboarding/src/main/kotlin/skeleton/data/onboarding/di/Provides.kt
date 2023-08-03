package skeleton.data.onboarding.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import skeleton.abstraction.onboarding.OnBoardingRepository
import skeleton.data.base.RetrofitNoToken
import skeleton.data.onboarding.network.OnBoardingService
import skeleton.usecase.onboarding.GetWelcomePageUseCase

@Module
@InstallIn(ViewModelComponent::class)
object Provides {

    @Provides
    @ViewModelScoped
    fun provideGetWelcomePage(onboardingRepository: OnBoardingRepository) =
        GetWelcomePageUseCase(onboardingRepository)

    @Provides
    @ViewModelScoped
    fun provideOnBoardingService(@RetrofitNoToken retrofit: Retrofit): OnBoardingService =
        retrofit.create(OnBoardingService::class.java)
}
