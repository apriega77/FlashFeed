package flashfeed.model.core

data class Channel(
    val channelDeviceUUID: String,
    val channelVersion: String,
    val channelDeviceModel: String = "-",
    val channelDetailOS: String = "-",
    val channelDeviceName: String = "ANDROID",
)
