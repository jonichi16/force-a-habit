package com.jonichi.database.di

import android.app.Application
import androidx.room.Room
import com.jonichi.database.HabitDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideHabitDatabase(app: Application) : HabitDatabase {
        return Room.databaseBuilder(
            app,
            HabitDatabase::class.java,
            "habit_db",
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}