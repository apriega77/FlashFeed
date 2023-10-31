package skeleton.data.core.network

import skeleton.model.core.ServerConfig

const val apiKey = "be361d6be1ea4848a82b58bd480a08c7"

object DefaultServerConfig {
    val devEnv = ServerConfig(
        "https://newsapi.org/v2/",
        true,
    )

    val prodEnv = ServerConfig(
        "https://newsapi.org/v2/",
        false,
    )
}
