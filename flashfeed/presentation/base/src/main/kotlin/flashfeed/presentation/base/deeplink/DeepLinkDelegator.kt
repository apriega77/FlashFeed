package base.presentation.deeplink

import flashfeed.model.base.deeplink.DeepLinkData
import flashfeed.model.base.deeplink.DeepLinkItem

interface DeepLinkDelegator {
    fun processDeepLink(deepLinkItem: DeepLinkItem)
    fun parseUrl(url: String): DeepLinkData
    fun getItem(path: String): DeepLinkItem?
}
