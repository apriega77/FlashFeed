package skeleton.data.core.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import skeleton.model.core.ServerConfig
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitBuilder @Inject constructor(
    private val serverConfig: ServerConfig,
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    private val chuckerInterceptor: ChuckerInterceptor,
//    private val securedRequestInterceptor: SecuredRequestInterceptor,
//    private val securedResponseInterceptor: SecuredResponseInterceptor,
//    @SecurityFlag
//    private val isSecurityEnabled: Boolean
) {
    private val timeOut = 60L

    fun createRetrofit(vararg interceptor: Interceptor): Retrofit {
        val client = getClientBuilder().apply {
            interceptor.forEach {
                addInterceptor(it)
            }
            if (serverConfig.allowDebug) {
                addInterceptor(chuckerInterceptor)
                addInterceptor(httpLoggingInterceptor)
            }
//            if (isSecurityEnabled) {
//                addInterceptor(securedResponseInterceptor)
//                addInterceptor(securedRequestInterceptor)
//            }
        }.build()
        return getRetrofit(client)
    }

    private fun getClientBuilder() = run {
        OkHttpClient.Builder().apply {
//            certificatePinner(
//                CertificatePinner.Builder().apply {
//                    add(serverConfig.urlPattern, serverConfig.sslPinning)
//                }.build()
//            )
            connectTimeout(timeOut, TimeUnit.SECONDS)
            readTimeout(timeOut, TimeUnit.SECONDS)
        }
    }

    private fun getRetrofit(okHttpClient: OkHttpClient) = run {
        Retrofit.Builder().apply {
            baseUrl(serverConfig.baseUrl)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }
}
