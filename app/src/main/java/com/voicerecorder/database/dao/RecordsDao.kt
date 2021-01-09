package com.voicerecorder.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.voicerecorder.database.RecordingItem

@Dao
interface RecordsDao {
    @Insert
    fun insertRecord(recordItem: RecordingItem)

    @Update
    fun updateRecord(recordItem: RecordingItem)

    @Query("SELECT *FROM records WHERE recordId =:id")
    fun getRecordById(id: Long?): RecordingItem?

    @Query("DELETE FROM records")
    fun clearAllRecords()

    @Query("DELETE FROM records WHERE recordId=:id")
    fun removeRecord(id: Long?)

    @Query("SELECT * FROM records ORDER BY recordId DESC")
    fun getAllRecords():LiveData<MutableList<RecordingItem>>

    @Query("SELECT COUNT(*) FROM records")
    fun getCountOfRecordsInDatabase():Int

}