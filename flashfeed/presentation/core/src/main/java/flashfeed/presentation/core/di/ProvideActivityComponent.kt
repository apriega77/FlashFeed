package flashfeed.presentation.core.di

import android.content.Context
import androidx.fragment.app.FragmentActivity
import base.RouterHandler
import base.presentation.deeplink.DeepLinkRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import flashfeed.presentation.base.BaseAppsErrorHandler
import flashfeed.presentation.base.BaseAppsUseCaseErrorMapper
import flashfeed.presentation.core.BaseAppsErrorHandlerImpl
import flashfeed.presentation.core.deeplink.RouterHandlerImpl

@Module
@InstallIn(ActivityComponent::class)
object ProvideActivityComponent {

    @Provides
    @ActivityScoped
    fun provideDefaultErrorHandler(
        @ActivityContext context: Context,
        errorMapper: BaseAppsUseCaseErrorMapper,
        deeplinkRouter: DeepLinkRouter,
    ): BaseAppsErrorHandler =
        BaseAppsErrorHandlerImpl(context as FragmentActivity, errorMapper, deeplinkRouter)


    @Provides
    @ActivityScoped
    fun provideRouterHandler(@ActivityContext context: Context): RouterHandler = RouterHandlerImpl(
        context as FragmentActivity,
    )
}
