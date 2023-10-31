package skeleton.presentation.core.di

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
import skeleton.presentation.base.BaseAppsErrorHandler
import skeleton.presentation.base.BaseAppsUseCaseErrorMapper
import skeleton.presentation.core.BaseAppsErrorHandlerImpl
import skeleton.presentation.core.deeplink.RouterHandlerImpl

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
