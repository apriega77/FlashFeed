package skeleton.model.base.deeplink

import skeleton.model.base.Route
import kotlin.reflect.KClass

data class DeepLinkItem(
    val path: String,
    val routeClass: KClass<out Route>,
    val queryTransformer: QueryTransformer? = null,
    val properties: DeepLinkProperty = DeepLinkProperty(),
)
