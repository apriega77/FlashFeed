package co.id.apriega77.skeleton

import skeleton.model.base.BuildFlavor
import base.model.Route
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import skeleton.data.base.AppVersion
import skeleton.data.base.AppVersionCode
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
        "sit" -> BuildFlavor.SIT
        "uat" -> BuildFlavor.UAT
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
