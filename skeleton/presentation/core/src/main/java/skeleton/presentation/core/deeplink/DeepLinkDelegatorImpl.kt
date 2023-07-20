package skeleton.presentation.core.deeplink

import base.model.deeplink.DeepLinkData
import base.model.deeplink.DeepLinkItem
import base.presentation.deeplink.DeepLinkDelegator
import javax.inject.Inject

class DeepLinkDelegatorImpl @Inject constructor(deepLinkItem: Set<DeepLinkItem>) :
    DeepLinkDelegator {

    private val pathMap = HashMap<String, DeepLinkItem>()

    init {
        deepLinkItem.forEach {
            processDeepLink(it)
        }
    }

    override fun processDeepLink(deepLinkItem: DeepLinkItem) {
        pathMap[deepLinkItem.path] = deepLinkItem
    }

    override fun parseUrl(url: String): DeepLinkData {
        return DeepLinkData.Companion.from(url)
    }

    override fun getItem(path: String): DeepLinkItem? {
        return pathMap[path]
    }
}
