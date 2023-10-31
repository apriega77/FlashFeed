package base

import base.BaseContract

interface RouterHandler {
    fun onNavigated(contract: BaseContract<*, *>)
    fun onRouteNotFound(className: String)
}
