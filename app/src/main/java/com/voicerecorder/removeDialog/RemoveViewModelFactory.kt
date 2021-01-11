package com.voicerecorder.removeDialog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voicerecorder.database.dao.RecordsDao
import java.lang.IllegalArgumentException

class RemoveViewModelFactory(
    private val databaseDao: RecordsDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemoveViewModel::class.java)) {
            return RemoveViewModel(
                databaseDao,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}