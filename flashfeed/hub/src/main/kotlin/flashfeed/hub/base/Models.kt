package flashfeed.hub.base

import android.net.Uri
import flashfeed.model.base.Route
import kotlin.reflect.KClass

typealias NavigationData = HashMap<KClass<out Route>, Route>

sealed class ContractDestination {
    data class Activity(val activityClass: KClass<out android.app.Activity>) : ContractDestination()
    data class IntentAction(val intentAction: String, val uri: Uri?) : ContractDestination()
}
