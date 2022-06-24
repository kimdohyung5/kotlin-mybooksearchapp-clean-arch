package com.kimdo.mybooksearchapp.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.work.*
import com.kimdo.mybooksearchapp.data.model.Book
import com.kimdo.mybooksearchapp.data.model.SearchResponse
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepository
import com.kimdo.mybooksearchapp.util.Constants
import com.kimdo.mybooksearchapp.worker.CacheDeleteWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

//@HiltViewModel
//class BookSearchViewModel @Inject constructor(
//    private val repository: BookSearchRepository
//    ,private val workManager: WorkManager
//  , private val savedStateHandle: SavedStateHandle
//): ViewModel() {
//
//    private var _searchResult = MutableLiveData<SearchResponse>()
//    val searchResult: LiveData<SearchResponse> get() = _searchResult
//
//    fun searchBooks(query: String) = viewModelScope.launch {
//        val response = repository.searchBooks(query , getSortMode(), 1, 15)
//        if( response.isSuccessful) {
//            response.body()?.let { body ->
//                _searchResult.postValue(body)
//            }
//        }
//    }
//
//    fun saveBook(book: Book) = viewModelScope.launch( Dispatchers.IO) {
//        repository.insertBook(book)
//    }
//
//    fun deleteBook(book: Book) = viewModelScope.launch( Dispatchers.IO ) {
//        repository.deleteBook(book)
//    }
//
//    val favoriteBooks: StateFlow<List<Book>> = repository.getFavoriteBooks()
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf() )
//
//    var query = String()
//    set(value) {
//        field = value
//        savedStateHandle.set( SAVE_STATE_KEY, value)
//    }
//
//    init {
//        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
//    }
//
//    companion object {
//        private const val SAVE_STATE_KEY = "query"
//        private const val WORKER_KEY = "cache_worker"
//    }
//
//    fun saveSortMode(value: String) = viewModelScope.launch {
//        repository.saveSortMode(value)
//    }
//
//    suspend fun getSortMode() = withContext(Dispatchers.IO) {
//        repository.getSortMode().first()
//    }
//
//    fun saveCacheDeleteMode(value: Boolean) = viewModelScope.launch {
//        repository.saveCacheDeleteMode(value)
//    }
//
//    suspend fun getCacheDeleteMode() = withContext(Dispatchers.IO) {
//        repository.getCacheDeleteMode().first()
//    }
//
//    val favoritePagingBooks : StateFlow<PagingData<Book>> =
//        repository.getFavoritePagingBooks()
//            .cachedIn(viewModelScope)
//            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
//
//    private val _searchPagingResult = MutableStateFlow<PagingData<Book>>( PagingData.empty())
//    val searchPagingResult: StateFlow<PagingData<Book>> = _searchPagingResult.asStateFlow()
//
//    fun searchBooksPaging(query: String) {
//        viewModelScope.launch {
//            repository.searchBooksPaging(query, getSortMode())
//                .cachedIn(viewModelScope)
//                .collect {
//                    _searchPagingResult.value = it
//                }
//        }
//    }
//
//    fun setWork() {
//        val constrants = Constraints.Builder()
//            .setRequiresCharging(true)
//            .setRequiresBatteryNotLow(true)
//            .build()
//
//        val workRequest = PeriodicWorkRequestBuilder<CacheDeleteWorker>( 15, TimeUnit.MINUTES)
//            .setConstraints(constrants)
//            .build()
//
//        workManager.enqueueUniquePeriodicWork(
//            WORKER_KEY, ExistingPeriodicWorkPolicy.REPLACE, workRequest
//        )
//    }
//
//    fun deleteWork() = workManager.cancelUniqueWork(WORKER_KEY)
//    fun getWorkStatus() : LiveData<MutableList<WorkInfo>> =
//        workManager.getWorkInfosForUniqueWorkLiveData(WORKER_KEY)
//
//}