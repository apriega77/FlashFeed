package skeleton.model.core

import skeleton.model.base.GenericErrorModel

sealed class PinCreationValidationResult {
    abstract val pin: String

    data class PinValid(override val pin: String) : PinCreationValidationResult()
    data class PinInvalid(override val pin: String, val error: GenericErrorModel) :
        PinCreationValidationResult()
}
