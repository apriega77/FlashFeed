package skeleton.data.core.datasource

import base.model.BuildFlavor
import skeleton.data.core.network.DefaultServerConfig
import skeleton.model.core.ServerConfig

class ServerDataSource {
    fun getServerConfig(flavor: BuildFlavor): ServerConfig {
        return when (flavor) {
            BuildFlavor.DEV -> DefaultServerConfig.devEnv
            BuildFlavor.SIT -> DefaultServerConfig.sitEnv
            BuildFlavor.UAT -> DefaultServerConfig.uatEnv
            BuildFlavor.PROD -> DefaultServerConfig.prodEnv
        }
    }
}
