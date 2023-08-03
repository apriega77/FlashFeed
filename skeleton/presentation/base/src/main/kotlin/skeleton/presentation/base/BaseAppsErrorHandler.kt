package skeleton.presentation.base

import base.presentation.DefaultErrorHandler
import skeleton.model.base.BaseAppsError

interface BaseAppsErrorHandler : DefaultErrorHandler<BaseAppsError> {
    fun listenEvent(callback: (Unit) -> Unit)
}
