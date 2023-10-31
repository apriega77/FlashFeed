package flashfeed.presentation.base

import base.presentation.DefaultErrorHandler
import flashfeed.model.base.BaseAppsError

interface BaseAppsErrorHandler : DefaultErrorHandler<BaseAppsError> {
    fun listenEvent(callback: (Unit) -> Unit)
}
