package skeleton.presentation.authentication

import AuthenticationContract
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
object AuthenticationInjection {

    @Provides
    @Singleton
    @IntoSet
    fun provideAuthentication(): Route = AuthenticationContract(AuthenticationActivity::class)

    @Provides
    @IntoSet
    @Singleton
    fun provideAuthenticationDeeplink(): DeepLinkItem {
        val path = "/authentication"
        return DeepLinkItem(path, AuthenticationContract::class, null)
    }
}
