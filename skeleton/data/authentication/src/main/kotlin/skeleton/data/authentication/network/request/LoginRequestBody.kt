package skeleton.data.authentication.network.request

data class LoginRequestBody(
    val email: String,
    val password: String,
)
