package com.tc.gamegallery.di

import com.apollographql.apollo3.ApolloClient
import com.tc.gamegallery.data.ApolloGameClient
import com.tc.gamegallery.domain.GameClient
import com.tc.gamegallery.domain.GetGameCatalogUseCase
import com.tc.gamegallery.domain.GetGameDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


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


}