package flashfeed.data.core.datasource

import flashfeed.model.base.BuildFlavor
import flashfeed.data.core.network.DefaultServerConfig
import flashfeed.model.core.ServerConfig

class ServerDataSource {
    fun getServerConfig(flavor: BuildFlavor): ServerConfig {
        return when (flavor) {
            BuildFlavor.DEV -> DefaultServerConfig.devEnv
            BuildFlavor.PROD -> DefaultServerConfig.prodEnv
        }
    }
}
