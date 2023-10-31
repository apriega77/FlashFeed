package skeleton.data.core.datasource

import skeleton.model.core.PreferenceKey
import skeleton.model.core.Token

object CorePrefKey {
    object TokenKey : PreferenceKey<Token>("token_auth")
    object BiometricStatusKey : PreferenceKey<Boolean>("biometric_status_key")
}
