package skeleton.data.authentication.repository

import skeleton.abstraction.authentication.AuthenticationRemoteDataSource
import skeleton.abstraction.authentication.AuthenticationRepository
import skeleton.model.authentication.LoginDto
import skeleton.model.authentication.LoginResult
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource,
) : AuthenticationRepository {
    override suspend fun login(loginDto: LoginDto): LoginResult {
        return authenticationRemoteDataSource.login(loginDto = loginDto)
    }
}
