package skeleton.abstraction.core

import skeleton.model.core.Token
import javax.crypto.Cipher

interface TokenLocalDataSource {
    fun setToken(token: Token)
    fun getSessionToken(): String
    fun getIsBiometricKeyAvailable(): Boolean
    fun getToken(): Token
    fun getToken(cipher: Cipher): Token
    fun clearToken()
    fun getBiometricCipher(): Cipher
}
