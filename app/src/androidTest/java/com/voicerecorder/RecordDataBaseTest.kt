package com.voicerecorder

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.voicerecorder.database.RecordDatabase
import com.voicerecorder.database.RecordingItem
import com.voicerecorder.database.dao.RecordsDao
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class RecordDataBaseTest {
    private lateinit var recordsDao: RecordsDao
    private lateinit var database:RecordDatabase

    @Before
    fun createDB(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context,RecordDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        recordsDao = database.recordDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        database.close()
    }

    @Test
    @Throws(IOException::class)
    fun testDatabase(){
        recordsDao.insertRecord(RecordingItem())
        val countOfRecordsInDatabase = recordsDao.getCountOfRecordsInDatabase()
        assertEquals(countOfRecordsInDatabase,1)
    }

}