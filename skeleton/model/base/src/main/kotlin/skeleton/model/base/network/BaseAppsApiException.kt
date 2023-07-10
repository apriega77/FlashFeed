package skeleton.model.base.network

import skeleton.model.base.GenericErrorModel
import java.io.IOException

sealed class BaseAppsApiException : IOException() {
    object SystemError : BaseAppsApiException()
    object NetworkError : BaseAppsApiException()
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
