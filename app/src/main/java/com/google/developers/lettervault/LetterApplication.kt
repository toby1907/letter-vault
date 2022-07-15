package com.google.developers.lettervault

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.developers.lettervault.data.DataRepository
import com.google.developers.lettervault.data.LetterState
import com.google.developers.lettervault.notification.NotificationWorker
import com.google.developers.lettervault.ui.list.LetterViewModel
import com.google.developers.lettervault.util.DataViewModelFactory
import com.google.developers.lettervault.util.NightMode
import com.google.developers.lettervault.util.WORK_NAME
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Base class for the application defined in AndroidManifest.
 * Add support to change qualified resources to use night mode or not.
 *
 * @see manifest
 */
class LetterApplication : Application() {


    override fun onCreate() {
        super.onCreate()


        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(
            getString(R.string.pref_key_night),
            getString(R.string.pref_night_auto)
        )?.apply {
            val mode = NightMode.valueOf(this.toUpperCase(Locale.US))
            AppCompatDelegate.setDefaultNightMode(mode.value)
        }



        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        val isOn = sharedPreferences.getBoolean("key_notification",false)
        if(isOn){
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()
            val repeatingRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
                15, TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .build()
            val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest

        )
        }

    }
}
