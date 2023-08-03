package skeleton.presentation.core.deeplink

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import skeleton.model.base.BaseAppsError
import skeleton.model.base.network.BaseAppsApiException
import skeleton.presentation.base.BaseAppsUseCaseErrorMapper

class BaseAppsUseCaseErrorMapperImpl : BaseAppsUseCaseErrorMapper {
    private val _errorEvents = MutableSharedFlow<BaseAppsError>()
    override val errorEvents: SharedFlow<BaseAppsError> = _errorEvents

    override fun sendException(scope: CoroutineScope, t: Throwable) {
        scope.launch {
            when (t) {
                is BaseAppsApiException.SystemError -> _errorEvents.emit(BaseAppsError.SystemError)
                BaseAppsApiException.NetworkError -> _errorEvents.emit(BaseAppsError.NetworkError)
                is BaseAppsApiException.Unauthorized -> _errorEvents.emit(
                    BaseAppsError.SessionExpired(
                        t.genericErrorModel,
                    ),
                )

                is BaseAppsApiException.GenericException -> _errorEvents.emit(
                    BaseAppsError.GenericError(
                        t.genericErrorModel,
                    ),
                )

                is BaseAppsApiException.SecurityException -> _errorEvents.emit(
                    BaseAppsError.SecurityError,
                )
                else -> _errorEvents.emit(BaseAppsError.GeneralError)
            }
        }
    }
}
