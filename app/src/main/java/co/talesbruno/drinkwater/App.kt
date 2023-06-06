package co.talesbruno.drinkwater

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Verificar a versão do Android para garantir a compatibilidade com os canais de notificação
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "water_reminder_channel"
            val channelName = "Lembretes de Água"
            val channelDescription = "Canal para exibir lembretes de água"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}