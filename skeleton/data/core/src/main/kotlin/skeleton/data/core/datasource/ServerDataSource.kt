package skeleton.data.core.datasource

import skeleton.model.base.BuildFlavor
import skeleton.data.core.network.DefaultServerConfig
import skeleton.model.core.ServerConfig

class ServerDataSource {
    fun getServerConfig(flavor: BuildFlavor): ServerConfig {
        return when (flavor) {
            BuildFlavor.DEV -> DefaultServerConfig.devEnv
            BuildFlavor.PROD -> DefaultServerConfig.prodEnv
        }
    }
}
