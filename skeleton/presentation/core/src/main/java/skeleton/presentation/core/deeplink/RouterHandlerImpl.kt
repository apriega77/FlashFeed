package skeleton.presentation.core.deeplink

import androidx.fragment.app.FragmentActivity
import base.BaseContract
import base.RouterHandler

class RouterHandlerImpl(private val activity: FragmentActivity) : RouterHandler {
    override fun onNavigated(contract: BaseContract<*, *>) {
        // For logging or future purpose
    }

    @Suppress("UnusedPrivateProperty")
    private val resource by lazy { activity.resources }

    override fun onRouteNotFound(className: String) {
        // handle error

    }
}
