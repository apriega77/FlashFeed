package flashfeed.presentation.core.deeplink

import flashfeed.model.base.BaseAppsError
import flashfeed.model.base.network.BaseAppsApiException
import flashfeed.presentation.base.BaseAppsUseCaseErrorMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

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
