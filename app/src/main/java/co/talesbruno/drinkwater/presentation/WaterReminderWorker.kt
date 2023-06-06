package co.talesbruno.drinkwater.presentation

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import co.talesbruno.drinkwater.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class WaterReminderWorker @Inject constructor(
    private val context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        showWaterReminderNotification()
        return Result.success()
    }

    private fun showWaterReminderNotification() {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Lembrete de Água")
            .setContentText("Não se esqueça de beber água!")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    companion object {
        private const val CHANNEL_ID = "water_reminder_channel"
        private const val NOTIFICATION_ID = 1
    }
}