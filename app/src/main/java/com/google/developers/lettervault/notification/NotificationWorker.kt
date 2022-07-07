package com.google.developers.lettervault.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.preference.PreferenceManager
import android.provider.SyncStateContract
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.developers.lettervault.R
import com.google.developers.lettervault.data.DataRepository
import com.google.developers.lettervault.data.LetterState
import com.google.developers.lettervault.ui.detail.LetterDetailActivity
import com.google.developers.lettervault.util.LETTER_ID
import com.google.developers.lettervault.util.NOTIFICATION_CHANNEL_ID
import com.google.developers.lettervault.util.NOTIFICATION_ID

/**
 * Run a work to show a notification on a background thread by the {@link WorkManger}.
 */
class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val letterId: Long = inputData.getLong(LETTER_ID, 0)

    /**
     * Create an intent with extended data to the letter.
     */
    private fun getContentIntent(): PendingIntent? {
        val intent = Intent(applicationContext, LetterDetailActivity::class.java).apply {
            putExtra(LETTER_ID, letterId)
        }
        return TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            return@run getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    override fun doWork(): Result {
        val prefManager = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val shouldNotify = prefManager.getBoolean(
            applicationContext.getString(R.string.pref_key_notify),
            false
        )
        if(shouldNotify){

/* val dataRepository = DataRepository.getInstance(applicationContext)

            val letterState = LetterState.ALL
           val letters = dataRepository?.getLetters(letterState)*/
createNotificationChannel()
val resources = applicationContext.resources
            val notification = NotificationCompat
                .Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                .setTicker(resources.getString(R.string.ready_letter_title))
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle(resources.getString(R.string.ready_letter_title))
                .setContentText(resources.getString(R.string.ready_letter_title))
                .setContentIntent(getContentIntent())
                .setAutoCancel(true)
                .build()

            val notificationManager = NotificationManagerCompat.from(applicationContext)
            notificationManager.notify(0, notification)
        }

        return Result.success()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.channel_name)
            val descriptionText = applicationContext.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            NotificationManagerCompat.from(applicationContext).createNotificationChannel(channel)
        }
    }

}

