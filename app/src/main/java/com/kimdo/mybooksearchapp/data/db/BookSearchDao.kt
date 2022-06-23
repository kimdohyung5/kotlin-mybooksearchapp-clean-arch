package com.kimdo.mybooksearchapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kimdo.mybooksearchapp.data.model.Book


@Dao
interface BookSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook( book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("select * from books")
    fun getFavoriteBooks(): LiveData<List<Book>>
}

