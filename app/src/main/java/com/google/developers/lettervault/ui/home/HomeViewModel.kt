package com.google.developers.lettervault.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.google.developers.lettervault.data.DataRepository
import com.google.developers.lettervault.data.Letter
import com.google.developers.lettervault.data.LetterDao
import com.google.developers.lettervault.data.LetterState

/**
 * ViewMode for the HomeActivity only holds recent letter.
 */
class HomeViewModel(private val dataRepository: DataRepository) : ViewModel() {
val recentLetter : LiveData<List<Letter>>
    get() = recentLetter()

  private fun recentLetter() :LiveData<List<Letter>>{
     return  dataRepository.getRecentLetter()
   }

}
