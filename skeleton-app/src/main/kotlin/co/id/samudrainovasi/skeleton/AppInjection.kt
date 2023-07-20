package co.id.samudrainovasi.skeleton

import base.model.Route
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
@InstallIn(SingletonComponent::class)
object AppInjection {
    @Singleton
    @Provides
    fun createMap(set: Set<@JvmSuppressWildcards Route>): HashMap<KClass<out Route>, Route> {
        val map = HashMap<KClass<out Route>, Route>()
        set.forEach {
            val kClass = it::class
            map[kClass] = it
        }
        return map
    }
}
