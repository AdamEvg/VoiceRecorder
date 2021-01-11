package com.voicerecorder.listRecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voicerecorder.database.dao.RecordsDao
import java.lang.IllegalArgumentException

class ListRecordViewModelFactory (
    private val databaseDao: RecordsDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListRecordViewModel::class.java)) {
            return ListRecordViewModel(databaseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}