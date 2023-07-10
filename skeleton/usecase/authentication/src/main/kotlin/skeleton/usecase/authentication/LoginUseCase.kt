package skeleton.usecase.authentication

import android.util.Log
import base.usecase.BaseUseCaseAsync
import skeleton.abstraction.authentication.AuthenticationRepository
import skeleton.model.authentication.LoginDto
import skeleton.model.authentication.LoginResult

class LoginUseCase(private val authenticationRepository: AuthenticationRepository) :
    BaseUseCaseAsync<LoginDto, LoginResult>() {

    override suspend fun build(args: LoginDto): LoginResult {
        return try {
            authenticationRepository.login(args)
        } catch (e: Exception) {
            Log.d("STATE_STATUS", e.toString())
            return LoginResult.Failed
        }
    }
}
