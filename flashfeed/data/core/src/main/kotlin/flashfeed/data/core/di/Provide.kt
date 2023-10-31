package flashfeed.data.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import flashfeed.abstraction.core.CoreRepository
import flashfeed.abstraction.core.LocationRepository
import flashfeed.usecase.core.GetChannelUseCase
import flashfeed.usecase.core.GetLastLocationUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Provide {

    @Provides
    @Singleton
    fun provideGetChannelUseCase(coreRepository: CoreRepository) =
        GetChannelUseCase(coreRepository)


    @Provides
    @Singleton
    fun provideGetLastLocationUseCase(
        locationRepository: LocationRepository,
    ): GetLastLocationUseCase = GetLastLocationUseCase(locationRepository)
}
