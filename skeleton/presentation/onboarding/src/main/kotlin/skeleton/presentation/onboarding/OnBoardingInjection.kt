package skeleton.presentation.onboarding

import OnBoardingContract
import base.model.Route
import base.model.deeplink.DeepLinkItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnBoardingInjection {

    @Provides
    @Singleton
    @IntoSet
    fun provideOnBoarding(): Route = OnBoardingContract(OnBoardingActivity::class)

    @Provides
    @IntoSet
    @Singleton
    fun provideResetPinDeeplink(): DeepLinkItem {
        val path = "/onboarding"
        return DeepLinkItem(path, OnBoardingContract::class, null)
    }
}
