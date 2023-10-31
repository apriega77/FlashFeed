package flashfeed.data.base


import flashfeed.base.abstraction.CacheRepository
import flashfeed.model.base.CacheKey

open class BaseCacheRepositoryImpl : CacheRepository {
    private val map = hashMapOf<CacheKey<*>, Any>()
    override fun <T : Any> set(key: CacheKey<T>, value: T) {
        map[key] = value
    }
    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(key: CacheKey<T>, isClear: Boolean): T? = try {
        (
            if (isClear) {
                map.remove(key)
            } else {
                map[key]
            }
            ) as? T
    } catch (ex: Exception) {
        null
    }
}
