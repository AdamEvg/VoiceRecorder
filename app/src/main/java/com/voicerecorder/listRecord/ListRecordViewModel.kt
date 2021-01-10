package com.voicerecorder.listRecord

import androidx.lifecycle.ViewModel
import com.voicerecorder.database.dao.RecordsDao

class ListRecordViewModel(recordsDao: RecordsDao) : ViewModel() {
     val records = recordsDao.getAllRecords()
}