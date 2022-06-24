package com.kimdo.mybooksearchapp.di

import com.kimdo.mybooksearchapp.data.db.BookSearchDatabase
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepository
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindBookSearchRepository (
        bookSearchDatabaseImpl: BookSearchRepositoryImpl
    ) : BookSearchRepository
}