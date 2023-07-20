package skeleton.presentation.core.deeplink

import android.content.Context
import androidx.fragment.app.FragmentActivity
import base.hub.RouterHandler
import base.presentation.deeplink.DeepLinkDelegator
import base.presentation.deeplink.DeepLinkRouter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object RouterHandler {
    @Provides
    @ActivityScoped
    fun provideRouterHandler(@ActivityContext context: Context): RouterHandler = RouterHandlerImpl(
        context as FragmentActivity,
    )
}

@Module
@InstallIn(ActivityComponent::class)
abstract class DeepLinkBinds {
    @Binds
    @ActivityScoped
    abstract fun bindDelegator(deepLinkDelegatorImpl: DeepLinkDelegatorImpl): DeepLinkDelegator

    @Binds
    @ActivityScoped
    abstract fun bindRouter(deepLinkRouterImpl: DeepLinkRouterImpl): DeepLinkRouter
}
