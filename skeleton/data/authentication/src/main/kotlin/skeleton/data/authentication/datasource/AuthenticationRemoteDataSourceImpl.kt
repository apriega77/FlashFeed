package skeleton.data.authentication.datasource

import android.util.Log
import skeleton.abstraction.authentication.AuthenticationRemoteDataSource
import skeleton.data.authentication.network.AuthenticationService
import skeleton.data.authentication.network.request.LoginRequestBody
import skeleton.model.authentication.LoginDto
import skeleton.model.authentication.LoginResult
import javax.inject.Inject

class AuthenticationRemoteDataSourceImpl @Inject constructor(
    private val authenticationService: AuthenticationService,
) : AuthenticationRemoteDataSource {
    override suspend fun login(loginDto: LoginDto): LoginResult {
        with(loginDto) {
            val response = authenticationService.login(
                LoginRequestBody(email = email, password = password),
            )
            Log.d("STATE_STATUS", response.data.toString())
        }
        return LoginResult.Success
    }
}
