package skeleton.presentation.authentication

import skeleton.model.authentication.LoginDto

sealed class AuthenticationState {
    data object Initial : AuthenticationState()
    data object Loaded : AuthenticationState()
}

sealed class AuthenticationEvent {
    data class Login(val loginDto: LoginDto) : AuthenticationEvent()
}
