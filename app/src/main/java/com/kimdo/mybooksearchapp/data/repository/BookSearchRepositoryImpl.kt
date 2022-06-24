package com.kimdo.mybooksearchapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kimdo.mybooksearchapp.data.api.BookSearchApi
import com.kimdo.mybooksearchapp.data.db.BookSearchDatabase
import com.kimdo.mybooksearchapp.data.model.Book
import com.kimdo.mybooksearchapp.data.model.SearchResponse
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepositoryImpl.PreferencesKeys.SORT_MODE
import com.kimdo.mybooksearchapp.util.Constants.PAGING_SIZE
import com.kimdo.mybooksearchapp.util.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class BookSearchRepositoryImpl @Inject constructor (
    private val db: BookSearchDatabase
  , private val dataStore: DataStore<Preferences>
  , private val api: BookSearchApi
): BookSearchRepository {
    override suspend fun searchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Response<SearchResponse> {
        return api.searchBooks(query, sort, page, size)
    }
    override suspend fun insertBook( book: Book) {
        db.getDao().insertBook(book )

    }
    override suspend fun deleteBook(book: Book) {
        db.getDao().deleteBook(book)
    }
    override fun getFavoriteBooks(): Flow<List<Book>> {
        return db.getDao().getFavoriteBooks()
    }

    private object PreferencesKeys {
        val SORT_MODE = stringPreferencesKey("sort_mode")
        val CACHE_DELETE_MODE = booleanPreferencesKey("cache_delete_mode")
    }

    override suspend fun saveSortMode(mode: String) {
        dataStore.edit { prefs ->
            prefs[ SORT_MODE ] = mode
        }

    }

    override suspend fun getSortMode(): Flow<String> {
        return dataStore.data
            .catch{ exception ->
                if(exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[SORT_MODE] ?: Sort.ACCURACY.value
            }
    }

    override suspend fun saveCacheDeleteMode(mode: Boolean) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.CACHE_DELETE_MODE] = mode
        }
    }

    override suspend fun getCacheDeleteMode(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if(exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs[PreferencesKeys.CACHE_DELETE_MODE] ?: false
            }
    }

    override fun getFavoritePagingBooks(): Flow<PagingData<Book>> {
        val pagingSourceFactory = { db.getDao().getFavoritePagingBooks() }

        return Pager (
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false,
                maxSize = PAGING_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
                ).flow

    }
    override fun searchBooksPaging(query: String, sort: String): Flow<PagingData<Book>>{
        val pagingSourceFactory = { BookSearchPagingSource(api, query, sort)}

        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false,
                maxSize = PAGING_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}