package com.google.developers.lettervault.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

/**
 * Room data object for all database interactions.
 *
 * @see Dao
 */
@Dao
interface LetterDao {
    @RawQuery(observedEntities = [Letter::class])
    fun getLetters(query: SupportSQLiteQuery): DataSource.Factory<Int, Letter>

    @Query("SELECT * FROM 'letter.db'  WHERE letter_id =:letterId")
    fun getLetter(letterId: Long): LiveData<Letter>
    @Query("SELECT * FROM 'letter.db' ORDER By letter_id ASC" )
    fun getRecentLetter(): LiveData<List<Letter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(letter: Letter):Long
@Insert
    fun insetAll(vararg letter: Letter)
   @Query("SELECT * FROM `letter.db` ORDER BY letter_created DESC LIMIT 1")
   fun getRecentLetterAdded(): LiveData<Letter>
@Update
    fun update(letter: Letter)
@Delete
    fun delete(letter: Letter)

}
