package skeleton.data.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import skeleton.abstraction.core.CoreRepository
import skeleton.data.core.repository.CoreRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Binds {

    @Binds
    @Singleton
    abstract fun bindCoreRepo(
        coreRepositoryImpl: CoreRepositoryImpl,
    ): CoreRepository

}
