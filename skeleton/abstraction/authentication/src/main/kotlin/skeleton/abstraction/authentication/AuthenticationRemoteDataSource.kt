package skeleton.abstraction.authentication

import skeleton.model.authentication.LoginDto
import skeleton.model.authentication.LoginResult

interface AuthenticationRemoteDataSource {
    suspend fun login(loginDto: LoginDto): LoginResult
}
