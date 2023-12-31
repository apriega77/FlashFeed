package flashfeed.model.base.network

import flashfeed.model.base.GenericErrorModel
import java.io.IOException

sealed class BaseAppsApiException : IOException() {
    data object SystemError : BaseAppsApiException()
    data object NetworkError : BaseAppsApiException()
    data class SecurityException(val type: Type) : BaseAppsApiException() {
        enum class Type {
            ENCRYPTION_FAILED,
            DECRYPTION_FAILED,
            SIGNATURE_FAILED,
            KEY_DERIVATION_FAILED,
            DIGITAL_SIGNATURE_NOT_MATCH,
            KEY_AGREEMENT_FAILED,
            TIMESTAMP_NOT_FOUND,
            DIGITAL_SIGNATURE_NOT_FOUND,
            BODY_NOT_FOUND,
        }
    }

    data class Unauthorized(val genericErrorModel: GenericErrorModel) : BaseAppsApiException()
    data class GenericException(val genericErrorModel: GenericErrorModel) : BaseAppsApiException()
}
