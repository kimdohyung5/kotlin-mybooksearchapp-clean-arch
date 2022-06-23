package com.kimdo.mybooksearchapp.data.repository

import androidx.lifecycle.LiveData
import com.kimdo.mybooksearchapp.data.api.RetrofitInstance.api
import com.kimdo.mybooksearchapp.data.db.BookSearchDao
import com.kimdo.mybooksearchapp.data.db.BookSearchDatabase
import com.kimdo.mybooksearchapp.data.model.Book
import com.kimdo.mybooksearchapp.data.model.SearchResponse
import retrofit2.Response

class BookSearchRepositoryImpl (private val db: BookSearchDatabase): BookSearchRepository {
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
    override fun getFavoriteBooks(): LiveData<List<Book>> {
        return db.getDao().getFavoriteBooks()
    }
}