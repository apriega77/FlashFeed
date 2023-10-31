package flashfeed.presentation.core.deeplink

import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import base.BaseContract
import base.NavigationData
import base.RouterHandler
import flashfeed.model.base.deeplink.DeepLinkData
import flashfeed.model.base.deeplink.DeepLinkItem
import flashfeed.model.base.deeplink.DeepLinkProperty
import base.presentation.deeplink.DeepLinkDelegator
import base.presentation.deeplink.DeepLinkRouter
import flashfeed.model.base.NoResult
import flashfeed.model.base.Route
import javax.inject.Inject
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class DeepLinkRouterImpl @Inject constructor(
    override val delegator: DeepLinkDelegator,
    override val activity: FragmentActivity,
    override val map: NavigationData,
    private val routerHandler: RouterHandler,
) : DeepLinkRouter {
    override fun launch(url: String) {
        val deepLinkData = DeepLinkData.from(url)
        if (deepLinkData.path.isEmpty()) {
            return
        }

        val data = delegator.getItem(deepLinkData.path)
        if (data == null) {
            routerHandler.onRouteNotFound(url)
            return
        }

        val route = data.routeClass
        val contract = map[route] as? BaseContract<*, *>
        if (contract == null) {
            routerHandler.onRouteNotFound(url)
            return
        }

        val properties = data.properties

        navigateToContract(contract, route, deepLinkData, data, properties)
    }

    private fun navigateToContract(
        contract: BaseContract<*, *>,
        route: KClass<out Route>,
        deepLinkData: DeepLinkData,
        data: DeepLinkItem,
        properties: DeepLinkProperty,
    ) {
        when (contract.type) {
            BaseContract.Type.NOARGS_NORESULT -> {
                startContract<NoResult, NoResult>(route, null, properties)
            }

            BaseContract.Type.ARGS_NORESULT -> {
                val args = data.queryTransformer?.transformToArgs(deepLinkData.queries)
                startContract<Parcelable, NoResult>(route, args, properties)
            }

            BaseContract.Type.NOARGS_RESULT, BaseContract.Type.ARGS_RESULT -> {
                routerHandler.onRouteNotFound("Contract not found in this type")
            }
        }
    }

    private inline fun <reified Args : Parcelable, reified Result : Parcelable> startContract(
        route: KClass<out Route>,
        args: Args?,
        properties: DeepLinkProperty,
    ) {
        val contract = map[route] as? BaseContract<Args, Result>
        if (contract == null) {
            routerHandler.onRouteNotFound("Contract not found in the map for route $route")
            return
        }

        val intent = contract.createIntent(activity, args)
        activity.startActivity(intent)

        if (properties.removeBackStack) {
            activity.finishAffinity()
        }
    }
}
