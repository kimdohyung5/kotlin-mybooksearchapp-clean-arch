package com.kimdo.mybooksearchapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kimdo.mybooksearchapp.data.model.Book
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val bookSearchRepository: BookSearchRepository, )
    : ViewModel(){

    val favoriteBooks: StateFlow<List<Book>> = bookSearchRepository.getFavoriteBooks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf() )


    fun saveBook(book: Book) = viewModelScope.launch( Dispatchers.IO) {
        bookSearchRepository.insertBook(book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch( Dispatchers.IO ) {
        bookSearchRepository.deleteBook(book)
    }
}