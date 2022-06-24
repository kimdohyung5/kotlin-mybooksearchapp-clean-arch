package com.kimdo.mybooksearchapp.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.lang.Exception

@HiltWorker
class CacheDeleteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val cachedDeleteResult: String
    ) : Worker(context, workerParams){

    override fun doWork(): Result {
        return try {
            Log.d(TAG, "doWork: Cache has deleted")
            Result.success()
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.failure()
        }
    }

    companion object {
        const val TAG = "CacheDeleteWorkers"
    }
}