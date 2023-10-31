package co.id.apriega77.flashfeed

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import flashfeed.data.base.AppVersion
import flashfeed.data.base.AppVersionCode
import flashfeed.model.base.BuildFlavor
import flashfeed.model.base.Route
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

    @Singleton
    @Provides
    fun provideBuildFlavor() = when (BuildConfig.FLAVOR) {
        "prod" -> BuildFlavor.PROD
        else -> BuildFlavor.DEV
    }

    @Singleton
    @Provides
    @AppVersionCode
    fun provideAppVersionCode() = BuildConfig.VERSION_CODE

    @Singleton
    @Provides
    @AppVersion
    fun provideAppId() = BuildConfig.VERSION_NAME
}
