package flashfeed.data.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import flashfeed.abstraction.home.HomeRepository
import flashfeed.data.home.network.HomeApi
import flashfeed.usecase.home.GetArticlesUseCase
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
internal class HomeDataProvideModule {

    @Provides
    @ViewModelScoped
    fun provideGetArticleUseCase(homeRepository: HomeRepository) =
        GetArticlesUseCase(homeRepository)
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object ProvideActivityRetained {

    @Provides
    @ActivityRetainedScoped
    fun provideHomeApi(retrofit: Retrofit) = retrofit.create(HomeApi::class.java)
}
