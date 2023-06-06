package co.talesbruno.drinkwater.domain.di

import android.app.Application
import android.content.Context
import androidx.work.WorkerParameters
import co.talesbruno.drinkwater.data.repository.WaterReminderSchedulerImpl
import co.talesbruno.drinkwater.domain.repository.WaterReminderScheduler
import co.talesbruno.drinkwater.presentation.WaterReminderWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideWaterReminderScheduler(context: Context): WaterReminderScheduler {
        return WaterReminderSchedulerImpl(context)
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideWaterReminderWorker(
        context: Context,
        params: WorkerParameters
    ): WaterReminderWorker {
        return WaterReminderWorker(context, params)
    }
}