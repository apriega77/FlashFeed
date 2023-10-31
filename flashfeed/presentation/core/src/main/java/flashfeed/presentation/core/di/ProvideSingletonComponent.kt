package flashfeed.presentation.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import flashfeed.presentation.base.BaseAppsUseCaseErrorMapper
import flashfeed.presentation.core.deeplink.BaseAppsUseCaseErrorMapperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideSingletonComponent {

    @Singleton
    @Provides
    fun provideErrorMapper() = BaseAppsUseCaseErrorMapperImpl() as BaseAppsUseCaseErrorMapper
}
