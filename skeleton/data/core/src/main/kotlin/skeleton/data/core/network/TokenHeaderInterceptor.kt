package skeleton.data.core.network

import base.model.NoArgs
import okhttp3.Interceptor
import okhttp3.Response
import skeleton.data.base.KEY_TOKEN
import skeleton.usecase.core.GetTokenUseCase
import javax.inject.Inject

class TokenHeaderInterceptor @Inject constructor(
    private val getToken: GetTokenUseCase,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val rebuildRequest = chain.request().newBuilder()
        val token = getToken(NoArgs())
        rebuildRequest.apply {
            addHeader(KEY_TOKEN, "Bearer $token")
        }
        return chain.proceed(rebuildRequest.build())
    }
}
