package base.presentation.deeplink

import skeleton.model.base.deeplink.DeepLinkData
import skeleton.model.base.deeplink.DeepLinkItem

interface DeepLinkDelegator {
    fun processDeepLink(deepLinkItem: DeepLinkItem)
    fun parseUrl(url: String): DeepLinkData
    fun getItem(path: String): DeepLinkItem?
}
