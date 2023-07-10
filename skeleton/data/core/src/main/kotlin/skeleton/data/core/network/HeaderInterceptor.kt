package skeleton.data.core.network

import base.model.NoArgs
import okhttp3.Interceptor
import okhttp3.Response
import skeleton.data.base.KEY_CHANNEL_NAME
import skeleton.data.base.KEY_LATITUDE
import skeleton.data.base.KEY_LONGITUDE
import skeleton.data.base.KEY_MODEL
import skeleton.data.base.KEY_OS
import skeleton.data.base.KEY_SIGNATURE
import skeleton.data.base.KEY_TIMESTAMP
import skeleton.data.base.KEY_UUID
import skeleton.data.base.KEY_VERSION
import skeleton.usecase.core.GetChannelUseCase
import skeleton.usecase.core.GetLastLocationUseCase
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val getChannelUseCase: GetChannelUseCase,
    private val getLastLocationUseCase: GetLastLocationUseCase,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val rebuildRequest = chain.request().newBuilder()
        val channel = getChannelUseCase(NoArgs())
        val location = getLastLocationUseCase(NoArgs())
        rebuildRequest.apply {
            addHeader(KEY_UUID, channel.channelDeviceUUID)
            addHeader("Content-Type", "application/json")
            addHeader(KEY_VERSION, channel.channelVersion)
            addHeader(KEY_MODEL, channel.channelDeviceModel)
            addHeader(KEY_OS, channel.channelDetailOS)
            addHeader(KEY_CHANNEL_NAME, "android")
            addHeader(KEY_TIMESTAMP, System.currentTimeMillis().toString())
            addHeader(KEY_SIGNATURE, System.currentTimeMillis().toString())
            addHeader(KEY_LATITUDE, location.first.takeIf { it.isNotBlank() } ?: "1.0")
            addHeader(KEY_LONGITUDE, location.second.takeIf { it.isNotBlank() } ?: "1.0")
        }
        return chain.proceed(rebuildRequest.build())
    }
}
