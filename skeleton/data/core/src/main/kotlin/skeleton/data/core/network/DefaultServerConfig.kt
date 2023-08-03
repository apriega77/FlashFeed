package skeleton.data.core.network

import skeleton.model.core.ServerConfig

private const val NOBUBANK_COM_WILDCARD_SSL_PINNING =
    "sha256/oGBVbESkbPUTtp1Eq3i0bp/0rMb+W+uUk4aJ0SywLVg="

object DefaultServerConfig {
    val devEnv = ServerConfig(
        "http://34.101.88.163:32004/",
        "gateway-api-dev.nobubank.com",
        NOBUBANK_COM_WILDCARD_SSL_PINNING,
        true,
    )

    val sitEnv = ServerConfig(
        "https://gateway-api-sit.nobubank.com/external/v1/",
        "gateway-api-sit.nobubank.com",
        NOBUBANK_COM_WILDCARD_SSL_PINNING,
        true,
    )

    val uatEnv = ServerConfig(
        "https://gateway-api-uat.nobubank.com/external/v1/",
        "gateway-api-uat.nobubank.com",
        NOBUBANK_COM_WILDCARD_SSL_PINNING,
        true,
    )

    val prodEnv = ServerConfig(
        "https://superapp-api.nobubank.com/external/v1/",
        "superapp-api.nobubank.com",
        NOBUBANK_COM_WILDCARD_SSL_PINNING,
        false,
    )
}
