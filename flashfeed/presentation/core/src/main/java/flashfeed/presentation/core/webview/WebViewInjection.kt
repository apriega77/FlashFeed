package flashfeed.presentation.core.webview

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import flashfeed.hub.WebViewContract
import flashfeed.model.base.Route
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebViewInjection {
    @Provides
    @Singleton
    @IntoSet
    fun provideWebView(): Route =
        WebViewContract(WebViewActivity::class)
}
