package skeleton.data.authentication.network

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import skeleton.data.authentication.network.request.LoginRequestBody
import skeleton.data.authentication.network.response.LoginResponse
import skeleton.data.base.REQUEST_NAME
import skeleton.data.base.network.model.BaseResponse

interface AuthenticationService {

    @POST("auth/login")
    @Headers("$REQUEST_NAME:$LOGIN_REQUEST_HEADER")
    suspend fun login(@Body requestBody: LoginRequestBody): BaseResponse<LoginResponse>
}
