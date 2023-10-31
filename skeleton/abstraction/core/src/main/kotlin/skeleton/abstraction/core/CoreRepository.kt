package skeleton.abstraction.core


import skeleton.model.core.Channel

interface CoreRepository {
    fun getChannel(): Channel
    fun getVersionName(): String
    fun getVersion(): String
}
