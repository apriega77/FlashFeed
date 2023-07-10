package skeleton.data.core.datasource

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import base.data.BasePreference
import skeleton.abstraction.core.TokenLocalDataSource
import skeleton.model.base.network.BaseAppsApiException
import skeleton.model.core.Token
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class TokenLocalDataSourceImpl @Inject constructor(
    private val preference: BasePreference,
) : TokenLocalDataSource {

    override fun setToken(token: Token) {
        try {
            var biometricKey = token.biometricKey
            if (biometricKey.isNotBlank()) {
                val cipher = getCipher()
                val secretKey = getSecretKey()
                cipher.init(Cipher.ENCRYPT_MODE, secretKey)
                val inputIv = Base64.encodeToString(cipher.iv, Base64.NO_WRAP)
                val encBiometricKey =
                    cipher.doFinal(token.biometricKey.toByteArray(Charset.defaultCharset()))
                biometricKey =
                    inputIv + "$" + Base64.encodeToString(encBiometricKey, Base64.NO_WRAP)
            }
            preference.putData(
                CorePrefKey.TokenKey,
                token.copy(
                    biometricKey = biometricKey,
                ),
            )
        } catch (_: Exception) {
        }
    }

    override fun getBiometricCipher(): Cipher {
        val token = preference.getData(CorePrefKey.TokenKey, Token.Init)
        if (token.biometricKey.isBlank()) throw BaseAppsApiException.SystemError
        val inputIv =
            token.biometricKey.split("$").firstOrNull() ?: throw BaseAppsApiException.SystemError
        return getCipher().apply {
            val secretKey = getSecretKey()
            init(
                Cipher.DECRYPT_MODE,
                secretKey,
                IvParameterSpec(Base64.decode(inputIv, Base64.NO_WRAP)),
            )
        }
    }

    override fun getSessionToken(): String {
        return preference.getData(CorePrefKey.TokenKey)?.accessToken.orEmpty()
    }

    override fun getToken(): Token {
        return preference.getData(CorePrefKey.TokenKey, Token.Init)
    }

    override fun getToken(cipher: Cipher): Token {
        val token = preference.getData(CorePrefKey.TokenKey, Token.Init)
        return if (token.biometricKey.isNotBlank()) {
            val biometricKeyEnc = token.biometricKey.split("$").getOrNull(1) ?: return Token.Init
            val byte = Base64.decode(biometricKeyEnc.toByteArray(), Base64.NO_WRAP)
            val biometricKey = try {
                String(cipher.doFinal(byte))
            } catch (e: Exception) {
                biometricKeyEnc
            }
            return token.copy(biometricKey = biometricKey)
        } else {
            token
        }
    }

    override fun getIsBiometricKeyAvailable(): Boolean {
        return preference.getData(CorePrefKey.TokenKey)?.biometricKey.orEmpty() != ""
    }

    private fun generateSecretKey(): SecretKey {
        val spec = KeyGenParameterSpec.Builder(
            TOKEN_KEY,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setInvalidatedByBiometricEnrollment(false)
                }
            }
            .build()

        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore",
        )
        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        return if (!keyStore.containsAlias(TOKEN_KEY)) {
            generateSecretKey()
        } else {
            keyStore.getKey(TOKEN_KEY, null) as SecretKey
        }
    }

    override fun clearToken() {
        preference.removeCache(CorePrefKey.TokenKey.key)
    }

    private fun getCipher(): Cipher {
        return Cipher.getInstance(
            KeyProperties.KEY_ALGORITHM_AES + "/" +
                KeyProperties.BLOCK_MODE_CBC + "/" +
                KeyProperties.ENCRYPTION_PADDING_PKCS7,
        )
    }

    companion object {
        const val TOKEN_KEY = "token_secret"
    }
}
