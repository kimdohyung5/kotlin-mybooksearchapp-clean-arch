package com.kimdo.mybooksearchapp.ui.viewmodel

import androidx.lifecycle.*
import com.kimdo.mybooksearchapp.data.model.SearchResponse
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepository
import kotlinx.coroutines.launch

class BookSearchViewModel constructor( private val repository: BookSearchRepository
, private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private var _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> get() = _searchResult

    fun searchBooks(query: String) = viewModelScope.launch {
        val response = repository.searchBooks(query , "accuracy", 1, 15)
        if( response.isSuccessful) {
            response.body()?.let { body ->
                _searchResult.postValue(body)
            }
        }
    }

    var query = String()
    set(value) {
        field = value
        savedStateHandle.set( SAVE_STATE_KEY, value)
    }

    init {
        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
    }

    companion object {
        private const val SAVE_STATE_KEY = "query"
    }
}