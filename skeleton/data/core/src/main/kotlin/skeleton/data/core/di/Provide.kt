package skeleton.data.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import skeleton.abstraction.core.CoreRepository
import skeleton.abstraction.core.LocationRepository
import skeleton.abstraction.core.TokenRepository
import skeleton.usecase.core.GetChannelUseCase
import skeleton.usecase.core.GetLastLocationUseCase
import skeleton.usecase.core.GetTokenUseCase
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
    fun provideGetToken(tokenRepository: TokenRepository) =
        GetTokenUseCase(tokenRepository)

    @Provides
    @Singleton
    fun provideGetLastLocationUseCase(
        locationRepository: LocationRepository,
    ): GetLastLocationUseCase = GetLastLocationUseCase(locationRepository)
}
