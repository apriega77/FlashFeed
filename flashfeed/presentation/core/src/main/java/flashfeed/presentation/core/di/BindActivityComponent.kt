package flashfeed.presentation.core.di

import base.presentation.deeplink.DeepLinkDelegator
import base.presentation.deeplink.DeepLinkRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import flashfeed.presentation.core.deeplink.DeepLinkDelegatorImpl
import flashfeed.presentation.core.deeplink.DeepLinkRouterImpl

@Module
@InstallIn(ActivityComponent::class)
abstract class BindActivityComponent {
    @Binds
    @ActivityScoped
    abstract fun bindDelegator(deepLinkDelegatorImpl: DeepLinkDelegatorImpl): DeepLinkDelegator

    @Binds
    @ActivityScoped
    abstract fun bindRouter(deepLinkRouterImpl: DeepLinkRouterImpl): DeepLinkRouter
}
