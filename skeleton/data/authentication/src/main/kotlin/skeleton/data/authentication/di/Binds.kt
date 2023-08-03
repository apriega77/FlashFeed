package skeleton.data.authentication.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import skeleton.abstraction.authentication.AuthenticationRemoteDataSource
import skeleton.abstraction.authentication.AuthenticationRepository
import skeleton.data.authentication.datasource.AuthenticationRemoteDataSourceImpl
import skeleton.data.authentication.repository.AuthenticationRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class Binds {

    @Binds
    @ViewModelScoped
    abstract fun bindAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl,
    ): AuthenticationRepository

    @Binds
    @ViewModelScoped
    abstract fun bindAuthenticationRemoteDatasource(
        authenticationRemoteDataSourceImpl: AuthenticationRemoteDataSourceImpl,
    ): AuthenticationRemoteDataSource
}
