package flashfeed.data.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import flashfeed.abstraction.core.CoreRepository
import flashfeed.usecase.core.GetChannelUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Provide {

    @Provides
    @Singleton
    fun provideGetChannelUseCase(coreRepository: CoreRepository) =
        GetChannelUseCase(coreRepository)
}
