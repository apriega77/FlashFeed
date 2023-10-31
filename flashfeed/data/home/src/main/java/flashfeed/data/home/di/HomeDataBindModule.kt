package flashfeed.data.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import flashfeed.abstraction.home.HomeRemoteDataSource
import flashfeed.abstraction.home.HomeRepository
import flashfeed.data.home.datasource.HomeRemoteDataSourceImpl
import flashfeed.data.home.repository.HomeRepositoryImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
internal abstract class HomeDataBindModule {
    @Binds
    @ActivityRetainedScoped
    abstract fun bindHomeRemoteDataSource(
        homeRemoteDataSourceImpl: HomeRemoteDataSourceImpl,
    ): HomeRemoteDataSource

    @Binds
    @ActivityRetainedScoped
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}
