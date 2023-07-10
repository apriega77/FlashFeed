package skeleton.data.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import skeleton.abstraction.core.CoreRepository
import skeleton.abstraction.core.LocationRepository
import skeleton.abstraction.core.TokenLocalDataSource
import skeleton.abstraction.core.TokenRepository
import skeleton.data.core.datasource.TokenLocalDataSourceImpl
import skeleton.data.core.repository.CoreRepositoryImpl
import skeleton.data.core.repository.LocationRepositoryImpl
import skeleton.data.core.repository.TokenRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Binds {

    @Binds
    @Singleton
    abstract fun bindCoreRepo(
        coreRepositoryImpl: CoreRepositoryImpl,
    ): CoreRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl,
    ): LocationRepository

    @Binds
    @Singleton
    abstract fun bindTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository

    @Binds
    @Singleton
    abstract fun bindTokenLocalDS(
        tokenLocalDataSource: TokenLocalDataSourceImpl,
    ): TokenLocalDataSource
}
