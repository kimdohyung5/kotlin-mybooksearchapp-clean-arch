package com.kimdo.mybooksearchapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kimdo.mybooksearchapp.data.model.Book
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository,
): ViewModel(){
    fun saveBook(book: Book) = viewModelScope.launch( Dispatchers.IO) {
        bookSearchRepository.insertBook(book)
    }
}