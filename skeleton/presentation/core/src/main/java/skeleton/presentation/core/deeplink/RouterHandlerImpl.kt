package skeleton.presentation.core.deeplink

import androidx.fragment.app.FragmentActivity
import base.hub.BaseContract
import base.hub.RouterHandler
import component.widget.bottomsheet.showAsBottomSheet

class RouterHandlerImpl(private val activity: FragmentActivity) : RouterHandler {
    override fun onNavigated(contract: BaseContract<*, *>) {
        // For logging or future purpose
    }

    private val resource by lazy { activity.resources }

    override fun onRouteNotFound(className: String) {
        // handle error
        activity.showAsBottomSheet {

        }
    }
}
