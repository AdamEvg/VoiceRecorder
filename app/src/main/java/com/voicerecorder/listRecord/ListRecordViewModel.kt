package com.voicerecorder.listRecord

import androidx.lifecycle.ViewModel
import com.voicerecorder.database.dao.RecordsDao

class ListRecordViewModel (
     dataSource: RecordsDao
) : ViewModel() {
     val database = dataSource
     val records = database.getAllRecords()
}