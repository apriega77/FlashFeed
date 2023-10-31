package flashfeed.data.core.di

import android.content.Context
import flashfeed.data.base.BasePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class LocalModule {

    @Provides
    @Singleton
    fun provideBasePreference(@ApplicationContext context: Context) = BasePreference(context, "app")
}
