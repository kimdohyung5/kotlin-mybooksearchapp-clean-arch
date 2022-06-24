package com.kimdo.mybooksearchapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.kimdo.mybooksearchapp.data.model.Book
import com.kimdo.mybooksearchapp.data.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BookSearchRepository {
    suspend fun searchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ) : Response<SearchResponse>


    suspend fun insertBook( book: Book)
    suspend fun deleteBook(book: Book)
    fun getFavoriteBooks(): Flow<List<Book>>

    suspend fun saveSortMode(mode: String)
    suspend fun getSortMode(): Flow<String>

    suspend fun saveCacheDeleteMode(mode: Boolean)

    suspend fun getCacheDeleteMode(): Flow<Boolean>

    fun getFavoritePagingBooks(): Flow<PagingData<Book>>
    fun searchBooksPaging(query: String, sort: String): Flow<PagingData<Book>>

}