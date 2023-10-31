package flashfeed.abstraction.core


import flashfeed.model.core.Channel

interface CoreRepository {
    fun getChannel(): Channel
    fun getVersionName(): String
    fun getVersion(): String
}
