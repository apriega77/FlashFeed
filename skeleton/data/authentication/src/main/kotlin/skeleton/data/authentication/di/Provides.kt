package skeleton.data.authentication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import skeleton.abstraction.authentication.AuthenticationRepository
import skeleton.data.authentication.network.AuthenticationService
import skeleton.usecase.authentication.LoginUseCase

@Module
@InstallIn(ViewModelComponent::class)
object Provides {

    @Provides
    @ViewModelScoped
    fun provideLogin(authenticationRepository: AuthenticationRepository) =
        LoginUseCase(authenticationRepository)

    @Provides
    @ViewModelScoped
    fun provideAuthenticationService(retrofit: Retrofit): AuthenticationService =
        retrofit.create(AuthenticationService::class.java)
}
