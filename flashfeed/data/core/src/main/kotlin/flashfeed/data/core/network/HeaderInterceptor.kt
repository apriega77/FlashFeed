package flashfeed.data.core.network

import flashfeed.data.base.KEY_MODEL
import flashfeed.data.base.KEY_OS
import flashfeed.data.base.KEY_TIMESTAMP
import flashfeed.data.base.KEY_UUID
import flashfeed.data.base.KEY_VERSION
import flashfeed.model.base.NoArgs
import flashfeed.usecase.core.GetChannelUseCase
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val getChannelUseCase: GetChannelUseCase,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val rebuildRequest = chain.request().newBuilder()
        val channel = getChannelUseCase(NoArgs())
        rebuildRequest.apply {
            addHeader(KEY_UUID, channel.channelDeviceUUID)
            addHeader("Content-Type", "application/json")
            addHeader(KEY_VERSION, channel.channelVersion)
            addHeader(KEY_MODEL, channel.channelDeviceModel)
            addHeader(KEY_OS, channel.channelDetailOS)
            addHeader(KEY_TIMESTAMP, System.currentTimeMillis().toString())
        }
        return chain.proceed(rebuildRequest.build())
    }
}
