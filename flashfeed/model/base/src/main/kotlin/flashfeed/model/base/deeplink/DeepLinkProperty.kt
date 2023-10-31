package flashfeed.model.base.deeplink

data class DeepLinkProperty(
    val requireLogin: Boolean = true,
    val requireStartHomeFirst: Boolean = true,
    val removeBackStack: Boolean = false,
    val clearSession: Boolean = false,
    val closeApp: Boolean = false,
)
