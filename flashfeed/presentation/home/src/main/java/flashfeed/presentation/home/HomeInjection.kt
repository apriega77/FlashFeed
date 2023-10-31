package flashfeed.presentation.home

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import flashfeed.hub.HomeContract
import flashfeed.model.base.Route
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeInjection {

    @Provides
    @Singleton
    @IntoSet
    fun provideHome(): Route = HomeContract(HomeActivity::class)
}