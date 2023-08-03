package skeleton.model.core

data class ServerConfig(
    val baseUrl: String,
    val urlPattern: String,
    val sslPinning: String,
    val allowDebug: Boolean,
)
