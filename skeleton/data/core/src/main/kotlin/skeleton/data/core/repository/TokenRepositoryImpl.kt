package skeleton.data.core.repository

import skeleton.abstraction.core.TokenLocalDataSource
import skeleton.abstraction.core.TokenRepository
import skeleton.model.core.Token
import javax.crypto.Cipher
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
) : TokenRepository {
    override fun setToken(token: Token) {
        tokenLocalDataSource.setToken(token)
    }

    override fun getSessionToken(): String {
        return tokenLocalDataSource.getSessionToken()
    }

    override fun getIsBiometricKeyAvailable(): Boolean {
        return tokenLocalDataSource.getIsBiometricKeyAvailable()
    }

    override fun getToken(): Token {
        return tokenLocalDataSource.getToken()
    }

    override fun getToken(cipher: Cipher): Token {
        return tokenLocalDataSource.getToken(cipher)
    }

    override fun getBiometricCipher(): Cipher {
        return tokenLocalDataSource.getBiometricCipher()
    }

    override fun clearToken() {
        tokenLocalDataSource.clearToken()
    }
}
