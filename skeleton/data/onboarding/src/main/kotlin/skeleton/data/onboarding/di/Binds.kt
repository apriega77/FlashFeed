package skeleton.data.onboarding.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import skeleton.abstraction.onboarding.OnBoardingRemoteDataSource
import skeleton.abstraction.onboarding.OnBoardingRepository
import skeleton.data.onboarding.datasource.OnBoardingRemoteDataSourceImpl
import skeleton.data.onboarding.repository.OnBoardingRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class Binds {

    @Binds
    @ViewModelScoped
    abstract fun bindOnboardingRepository(
        onboardingRepositoryImpl: OnBoardingRepositoryImpl,
    ): OnBoardingRepository

    @Binds
    @ViewModelScoped
    abstract fun bindRemoteDatasource(
        onBoardingRemoteDataSourceImpl: OnBoardingRemoteDataSourceImpl,
    ): OnBoardingRemoteDataSource
}
