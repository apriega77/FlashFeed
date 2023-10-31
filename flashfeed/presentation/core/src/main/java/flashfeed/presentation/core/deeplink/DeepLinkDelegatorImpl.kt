package flashfeed.presentation.core.deeplink

import flashfeed.model.base.deeplink.DeepLinkData
import flashfeed.model.base.deeplink.DeepLinkItem
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
