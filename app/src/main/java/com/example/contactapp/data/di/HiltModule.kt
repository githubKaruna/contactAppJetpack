package com.example.contactapp.data.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactapp.data.database.ConatctDatabse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    fun provideDatabase(application: Application): ConatctDatabse {
        return  Room.databaseBuilder(application.applicationContext,ConatctDatabse::class.java,"contact_database.db")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .fallbackToDestructiveMigration()
            .build()

    }
}