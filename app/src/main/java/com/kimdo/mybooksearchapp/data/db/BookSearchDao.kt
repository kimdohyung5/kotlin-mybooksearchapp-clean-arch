package com.kimdo.mybooksearchapp.data.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.kimdo.mybooksearchapp.data.model.Book
import kotlinx.coroutines.flow.Flow


@Dao
interface BookSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook( book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("select * from books")
    fun getFavoriteBooks(): Flow<List<Book>>

    @Query("select * from books")
    fun getFavoritePagingBooks(): PagingSource<Int, Book>
}

