package co.talesbruno.drinkwater.domain.repository

interface WaterReminderScheduler {
    fun scheduleReminder(interval: Long)
    fun cancelReminder()
    fun isReminderScheduled(): Boolean
}