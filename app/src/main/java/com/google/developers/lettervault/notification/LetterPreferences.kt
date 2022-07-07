package com.google.developers.lettervault.notification

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.work.impl.model.Preference
private const val NOTIFICATION_IS_ON = "notification is on"
object LetterPreferences{
    fun isOn (context: Context): Boolean{

        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(NOTIFICATION_IS_ON,false)
    }
    fun setNotifcationState(context: Context,isOn: Boolean){
PreferenceManager.getDefaultSharedPreferences(context) .edit {
    putBoolean(NOTIFICATION_IS_ON,isOn)
}
    }
}