package flashfeed.data.core.datasource

import flashfeed.model.core.PreferenceKey
import flashfeed.model.core.Token

object CorePrefKey {
    object TokenKey : PreferenceKey<Token>("token_auth")
    object BiometricStatusKey : PreferenceKey<Boolean>("biometric_status_key")
}
