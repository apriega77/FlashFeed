package flashfeed.presentation.home

import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.scopes.ActivityScoped
import flashfeed.hub.HomeContract
import flashfeed.hub.WebViewContract
import flashfeed.hub.base.BaseRouter
import flashfeed.hub.base.NavigationData
import flashfeed.hub.base.RouterHandler
import flashfeed.model.base.NoResult
import flashfeed.model.core.WebViewProperties
import javax.inject.Inject

@ActivityScoped
class HomeRouter @Inject constructor(
    override val activity: FragmentActivity,
    override val map: NavigationData,
    override val routerHandler: RouterHandler,
) : BaseRouter() {

    fun goToWebView(properties: WebViewProperties) {
        launch<WebViewContract, WebViewProperties>(properties)
    }
}
