package flashfeed.data.core.network

import flashfeed.data.base.KEY_TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenHeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val rebuildRequest = chain.request().newBuilder()
        rebuildRequest.apply {
            addHeader(KEY_TOKEN, "Bearer $apiKey")
        }
        return chain.proceed(rebuildRequest.build())
    }
}
