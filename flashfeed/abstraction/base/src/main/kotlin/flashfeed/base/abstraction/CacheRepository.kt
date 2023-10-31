package flashfeed.base.abstraction

import flashfeed.model.base.CacheKey

interface CacheRepository {
    fun <T : Any>set(key: CacheKey<T>, value: T)
    fun <T : Any>get(key: CacheKey<T>, isClear: Boolean = false): T?
}
