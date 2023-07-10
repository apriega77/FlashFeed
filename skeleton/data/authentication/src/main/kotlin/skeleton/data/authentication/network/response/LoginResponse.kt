package skeleton.data.authentication.network.response

data class LoginResponse(
    val accessToken: String?,
    val accessTokenExpires: String?,
    val refreshToken: String?,
    val refreshTokenExpires: String?,
)
