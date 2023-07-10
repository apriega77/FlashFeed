package skeleton.abstraction.core

import kotlinx.coroutines.flow.Flow
import skeleton.model.core.Channel
import skeleton.model.core.PinCreationValidationResult

interface CoreRepository {
    fun getChannel(): Channel
    fun validatePin(pin: String): Flow<PinCreationValidationResult>
    fun isBiometricEnabled(): Boolean
    fun setEnableBiometric(isEnabled: Boolean)
    fun getVersionName(): String
    fun getVersion(): String
    fun isBiometricServiceAvailable(): Boolean
}
