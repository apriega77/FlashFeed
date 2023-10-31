package skeleton.model.base

sealed class BaseAppsError {
    data object GeneralError : BaseAppsError()
    data object SystemError : BaseAppsError()
    data object NetworkError : BaseAppsError()
    data class SessionExpired(val model: GenericErrorModel) : BaseAppsError()
    data class GenericError(val model: GenericErrorModel) : BaseAppsError()

    data object SecurityError : BaseAppsError()
}
