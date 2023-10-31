package base.presentation.deeplink

import androidx.fragment.app.FragmentActivity
import base.NavigationData

interface DeepLinkRouter {
    val delegator: DeepLinkDelegator
    val activity: FragmentActivity
    val map: NavigationData
    fun launch(url: String)
}
