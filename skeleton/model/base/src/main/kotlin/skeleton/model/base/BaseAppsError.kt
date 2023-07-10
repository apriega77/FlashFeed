package skeleton.model.base

sealed class BaseAppsError {
    object GeneralError : BaseAppsError()
    object SystemError : BaseAppsError()
    object NetworkError : BaseAppsError()
    data class SessionExpired(val model: GenericErrorModel) : BaseAppsError()
    data class GenericError(val model: GenericErrorModel) : BaseAppsError()

    object SecurityError : BaseAppsError()
}
