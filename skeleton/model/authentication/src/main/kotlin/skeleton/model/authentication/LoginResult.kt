package skeleton.model.authentication

sealed class LoginResult {
    data object Success : LoginResult()
    data object Failed : LoginResult()
}
