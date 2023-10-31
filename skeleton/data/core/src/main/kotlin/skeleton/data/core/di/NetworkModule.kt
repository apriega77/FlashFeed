package skeleton.data.core.di

import android.content.Context
import skeleton.model.base.BuildFlavor
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import skeleton.data.base.RetrofitNoToken
import skeleton.data.core.datasource.ServerDataSource
import skeleton.data.core.network.HeaderInterceptor
import skeleton.data.core.network.RetrofitBuilder
import skeleton.data.core.network.TokenHeaderInterceptor
import skeleton.model.core.ServerConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @Provides
    fun provideServerConfig(flavor: BuildFlavor): ServerConfig =
        ServerDataSource().getServerConfig(flavor)

    @Provides
    @RetrofitNoToken
    fun provideRetrofitNoToken(
        retrofitBuilder: RetrofitBuilder,
        headerInterceptor: HeaderInterceptor,
//        responseInterceptor: ResponseInterceptor,
    ): Retrofit {
        return retrofitBuilder.createRetrofit(headerInterceptor)
//        return retrofitBuilder.createRetrofit(headerInterceptor, responseInterceptor)
    }

    @Provides
    fun provideRetrofit(
        retrofitBuilder: RetrofitBuilder,
        headerInterceptor: HeaderInterceptor,
        tokenHeaderInterceptor: TokenHeaderInterceptor,
//        responseInterceptor: ResponseInterceptor,
    ): Retrofit {
        return retrofitBuilder.createRetrofit(
            headerInterceptor,
            tokenHeaderInterceptor,
//            responseInterceptor,
        )
    }

    @Provides
    @Singleton
    fun provideHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            // Toggles visibility of the push notification
            showNotification = true,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR,
        )

        return ChuckerInterceptor.Builder(context)
            // The previously created Collector
            .collector(chuckerCollector)
            // The max body content length in bytes, after this responses will be truncated.
            .maxContentLength(250_000L)
            // List of headers to replace with ** in the Chucker UI
            .redactHeaders("Auth-Token", "Bearer")
            // Read the whole response body even when the client does not consume the response completely.
            // This is useful in case of parsing errors or when the response body
            // is closed before being read like in Retrofit with Void and Unit types.
            .alwaysReadResponseBody(true)
            .build()
    }
}
