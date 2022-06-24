package com.kimdo.mybooksearchapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.kimdo.mybooksearchapp.data.repository.BookSearchRepository
import com.kimdo.mybooksearchapp.worker.CacheDeleteWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val bookSearchRepository: BookSearchRepository
                                            ,private val workManager: WorkManager
) : ViewModel(){


    fun saveSortMode(value: String) = viewModelScope.launch {
        bookSearchRepository.saveSortMode(value)
    }

    suspend fun getSortMode() = withContext(Dispatchers.IO) {
        bookSearchRepository.getSortMode().first()
    }

    fun saveCacheDeleteMode(value: Boolean) = viewModelScope.launch {
        bookSearchRepository.saveCacheDeleteMode(value)
    }

    suspend fun getCacheDeleteMode() = withContext(Dispatchers.IO) {
        bookSearchRepository.getCacheDeleteMode().first()
    }

    fun setWork() {
        val constrants = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<CacheDeleteWorker>( 15, TimeUnit.MINUTES)
            .setConstraints(constrants)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORKER_KEY, ExistingPeriodicWorkPolicy.REPLACE, workRequest
        )
    }

    fun deleteWork() = workManager.cancelUniqueWork(WORKER_KEY)
    fun getWorkStatus() : LiveData<MutableList<WorkInfo>> =
        workManager.getWorkInfosForUniqueWorkLiveData(WORKER_KEY)

    companion object {
        private const val WORKER_KEY = "cache_worker"
    }
}