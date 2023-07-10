package skeleton.data.core.repository

import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.biometric.BiometricManager
import base.data.BasePreference
import base.model.Image
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import skeleton.abstraction.core.CoreRepository
import skeleton.data.base.AppVersion
import skeleton.data.base.AppVersionCode
import skeleton.data.core.datasource.CorePrefKey
import skeleton.model.base.GenericErrorModel
import skeleton.model.core.Channel
import skeleton.model.core.PinCreationValidationResult
import javax.inject.Inject

class CoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @AppVersion private val appVersion: String,
    @AppVersionCode private val appVersionCode: Int,
    private val pref: BasePreference,
) : CoreRepository {
    override fun getChannel(): Channel {
        val uuid = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        val version = appVersion
        val model = "${Build.MANUFACTURER} ${Build.MODEL}"
        val operatingSystem = Build.VERSION.SDK_INT.toString()
        return Channel(
            uuid,
            version,
            model,
            operatingSystem,
        )
    }

    override fun isBiometricEnabled(): Boolean {
        return pref.getData(CorePrefKey.BiometricStatusKey, false)
    }

    override fun setEnableBiometric(isEnabled: Boolean) {
        pref.putData(CorePrefKey.BiometricStatusKey, isEnabled)
    }

    private val biometricManager: BiometricManager by lazy {
        BiometricManager.from(
            context,
        )
    }

    override fun isBiometricServiceAvailable(): Boolean {
        return biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG,
        ) == BiometricManager.BIOMETRIC_SUCCESS
    }

    override fun validatePin(pin: String): Flow<PinCreationValidationResult> {
        val error = GenericErrorModel(
            title = "Terjadi Kesalahan",
            description = "PIN baru tidak boleh angka yang berurutan dan " +
                "mengandung angka yang sama (Cth: 123456, 111111)",
            icon = Image.Init,
            primaryText = "OK",
            secondaryText = "",
            primaryLink = "",
            secondaryLink = "",
        )
        return flow {
            when {
                !checkRepetition(pin) -> this.emit(
                    PinCreationValidationResult.PinInvalid(
                        pin,
                        error,
                    ),
                )

                !checkSequential(pin) -> this.emit(
                    PinCreationValidationResult.PinInvalid(
                        pin,
                        error,
                    ),
                )

                else -> this.emit(PinCreationValidationResult.PinValid(pin))
            }
        }
    }

    private fun checkRepetition(string: String): Boolean {
        string.forEachIndexed { index, c ->
            val int1 = c.code
            val int2 = (string.getOrNull(index + 1) ?: return true).code
            val int3 = (string.getOrNull(index + 2) ?: return true).code
            val int4 = (string.getOrNull(index + 3) ?: return true).code

            if (int1 == int2 && int1 == int3 && int1 == int4) return false
        }
        return true
    }

    private fun checkSequential(string: String): Boolean {
        string.forEachIndexed { index, c ->
            val int1 = c.code
            val int2 = (string.getOrNull(index + 1) ?: return true).code - 1
            val int3 = (string.getOrNull(index + 2) ?: return true).code - 2
            val int4 = (string.getOrNull(index + 3) ?: return true).code - 3

            if (int1 == int2 && int1 == int3 && int1 == int4) return false

            val int5 = c.code
            val int6 = (string.getOrNull(index + 1) ?: return true).code + 1
            val int7 = (string.getOrNull(index + 2) ?: return true).code + 2
            val int8 = (string.getOrNull(index + 3) ?: return true).code + 3

            if (int5 == int6 && int5 == int7 && int5 == int8) return false
        }
        return true
    }

    override fun getVersionName(): String {
        return appVersion
    }

    override fun getVersion(): String {
        return "v $appVersion ($appVersionCode)"
    }
}
