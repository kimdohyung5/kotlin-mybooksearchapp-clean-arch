package com.kimdo.mybooksearchapp.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import androidx.work.WorkManager
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepository
import com.kimdo.mybooksearchapp.worker.CacheDeleteWorker
import java.lang.IllegalArgumentException

//@Suppress("UNCHECKED_CAST")
//class BookSearchViewModelProviderFactory (
//    private val repository: BookSearchRepository
//    , private val workManager: WorkManager
//    , owner: SavedStateRegistryOwner
//    , defaultArgs: Bundle? = null) :AbstractSavedStateViewModelFactory(owner, defaultArgs) {
//    override fun <T : ViewModel> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle
//    ): T {
//        if(modelClass.isAssignableFrom(BookSearchViewModel::class.java)) {
//            return BookSearchViewModel(repository, workManager, handle) as T
//        }
//        throw IllegalArgumentException("not found BookSearchViewModel")
//    }
//}
//override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//    if(modelClass.isAssignableFrom(BookSearchViewModel::class.java)) {
//        return BookSearchViewModel(repository) as T
//    }
//
//    throw IllegalArgumentException("not found BookSearchViewModel")
//}