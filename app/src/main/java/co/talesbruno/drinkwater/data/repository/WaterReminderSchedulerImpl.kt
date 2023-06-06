package co.talesbruno.drinkwater.data.repository

import android.content.Context
import androidx.work.*
import co.talesbruno.drinkwater.domain.repository.WaterReminderScheduler
import co.talesbruno.drinkwater.presentation.WaterReminderWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WaterReminderSchedulerImpl @Inject constructor(private val context: Context) :
    WaterReminderScheduler {

    override fun scheduleReminder(interval: Long) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val reminderRequest =
            PeriodicWorkRequestBuilder<WaterReminderWorker>(interval, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WATER_REMINDER_WORKER_TAG,
            ExistingPeriodicWorkPolicy.UPDATE,
            reminderRequest
        )
    }

    override fun cancelReminder() {
        WorkManager.getInstance(context).cancelUniqueWork(WATER_REMINDER_WORKER_TAG)
    }

    override fun isReminderScheduled(): Boolean {
        val workInfos =
            WorkManager.getInstance(context).getWorkInfosByTag(WATER_REMINDER_WORKER_TAG).get()
        return workInfos.isNotEmpty()
    }

    companion object {
        private const val WATER_REMINDER_WORKER_TAG = "water_reminder_worker"
    }
}