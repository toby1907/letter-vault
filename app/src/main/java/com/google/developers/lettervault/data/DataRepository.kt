package com.google.developers.lettervault.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.developers.lettervault.notification.NotificationWorker
import com.google.developers.lettervault.util.LETTER_ID
import com.google.developers.lettervault.util.LetterLock
import com.google.developers.lettervault.util.executeThread
import java.util.concurrent.TimeUnit

/**
 * Handles data sources and execute on the correct threads.
 */
class DataRepository(private val letterDao: LetterDao) {

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(context: Context): DataRepository? {
            return instance ?: synchronized(DataRepository::class.java) {
                if (instance == null) {
                    val database = LetterDatabase.getInstance(context)
                    instance = DataRepository(database.letterDao())
                }
                return instance
            }
        }
    }

    /**
     * Get letters with a filtered state for paging.
     */
    fun getLetters(filter: LetterState): LiveData<PagedList<Letter>> {
/*val letter = letterDao.getLetters(getFilteredQuery(filter)).toLiveData(

)*/

       throw NotImplementedError("needs implementation")
    }

    fun getLetter(id: Long): LiveData<Letter> {
      return  letterDao.getLetter(id)
       // throw NotImplementedError("needs implementation")

    }

    fun delete(letter: Letter) {
    }

    /**
     * Add a letter to database and schedule a notification on
     * when the letter vault can be opened.
     */
    fun save(letter: Letter) = executeThread {
    }

    /**
     * Update database with a decode letter content and update the opened timestamp.
     */
    fun openLetter(letter: Letter) = executeThread {
        val letterCopy = letter.copy(
            subject = LetterLock.retrieveMessage(letter.subject),
            content = LetterLock.retrieveMessage(letter.content),
            opened = System.currentTimeMillis()
        )
        letterDao.update(letterCopy)
    }

    /**
     * Create a raw query at runtime for filtering the letters.
     */
    private fun getFilteredQuery(filter: LetterState): SimpleSQLiteQuery {
        val now = System.currentTimeMillis()
        val simpleQuery = StringBuilder()
            .append("SELECT * FROM `letter.db` ")

        if (filter == LetterState.FUTURE) {
            simpleQuery.append("WHERE expires >= $now OR expires <= $now AND opened IS 0")
        }
        if (filter == LetterState.OPENED) {
            simpleQuery.append("WHERE opened IS NOT 0")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}
