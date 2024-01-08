package com.tc.gamegallery.di

import android.content.Context
import android.content.SharedPreferences
import com.apollographql.apollo3.ApolloClient
import com.tc.gamegallery.data.ApolloGameClient
import com.tc.gamegallery.domain.GameClient
import com.tc.gamegallery.domain.GetFavoriteGameIdsUseCase
import com.tc.gamegallery.domain.GetFavoriteGamesUseCase
import com.tc.gamegallery.domain.GetGameCatalogUseCase
import com.tc.gamegallery.domain.GetGameDetailsUseCase
import com.tc.gamegallery.domain.GetGameSeriesUseCase
import com.tc.gamegallery.domain.GetGenresCatalogUseCase
import com.tc.gamegallery.domain.GetTagsCatalogUseCase
import com.tc.gamegallery.domain.SaveFavoriteGameIdsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("FavoriteGameIds", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl("https://ev9a1s6nwa.execute-api.us-east-1.amazonaws.com/dev/graphql")
            .build()
    }

    @Provides
    @Singleton
    fun provideGameClient(apolloClient: ApolloClient): GameClient {
        return ApolloGameClient(apolloClient)
    }

    @Provides
    @Singleton
    fun provideGetGameCatalogUseCase(gameClient: GameClient): GetGameCatalogUseCase {
        return GetGameCatalogUseCase(gameClient)
    }

    @Provides
    @Singleton
    fun provideGetGameDetailsUseCase(gameClient: GameClient): GetGameDetailsUseCase {
        return GetGameDetailsUseCase(gameClient)
    }

    @Provides
    @Singleton
    fun provideGetGenresCatalogUseCase(gameClient: GameClient): GetGenresCatalogUseCase {
        return GetGenresCatalogUseCase(gameClient)
    }

    @Provides
    @Singleton
    fun provideGetTagsCatalogUseCase(gameClient: GameClient): GetTagsCatalogUseCase {
        return GetTagsCatalogUseCase(gameClient)
    }

    @Provides
    @Singleton
    fun provideGetGameSeriesUseCase(gameClient: GameClient): GetGameSeriesUseCase {
        return GetGameSeriesUseCase(gameClient)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteGamesUseCase(gameClient: GameClient): GetFavoriteGamesUseCase {
        return GetFavoriteGamesUseCase(gameClient)
    }

    @Provides
    @Singleton
    fun provideSaveFavoriteGameIdsUseCase(sharedPreferences: SharedPreferences): SaveFavoriteGameIdsUseCase {
        return SaveFavoriteGameIdsUseCase(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteGameIdsUseCase(sharedPreferences: SharedPreferences): GetFavoriteGameIdsUseCase {
        return GetFavoriteGameIdsUseCase(sharedPreferences)
    }


}