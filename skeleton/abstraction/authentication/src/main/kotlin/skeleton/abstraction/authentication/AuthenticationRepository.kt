package skeleton.abstraction.authentication

import skeleton.model.authentication.LoginDto
import skeleton.model.authentication.LoginResult

interface AuthenticationRepository {
    suspend fun login(loginDto: LoginDto): LoginResult
}
