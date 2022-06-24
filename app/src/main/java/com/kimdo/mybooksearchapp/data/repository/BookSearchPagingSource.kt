package com.kimdo.mybooksearchapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kimdo.mybooksearchapp.data.api.BookSearchApi
import com.kimdo.mybooksearchapp.data.model.Book
import com.kimdo.mybooksearchapp.util.Constants.PAGING_SIZE
import retrofit2.HttpException
import java.io.IOException

class BookSearchPagingSource(
    private val api: BookSearchApi,
    private val query: String,
    private val sort: String,
) : PagingSource<Int, Book>(){


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_INDEX

            val response = api.searchBooks(query, sort, pageNumber, params.loadSize)
            val endOfPaginationReached = response.body()?.meta?.isEnd!!

            val data = response.body()?.documents!!
            val prevKey = if( pageNumber == STARTING_PAGE_INDEX) null else pageNumber -1
            val nextKey = if( endOfPaginationReached) {
                null
            } else {
                pageNumber + ( params.loadSize / PAGING_SIZE)
            }
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch ( exception: IOException) {
            LoadResult.Error(exception)
        } catch( exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val  STARTING_PAGE_INDEX = 1
    }
}