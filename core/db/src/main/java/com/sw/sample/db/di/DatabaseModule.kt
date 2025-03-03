package com.sw.sample.db.di

import android.content.Context
import androidx.room.Room
import com.sw.sample.common.DB_NAME
import com.sw.sample.db.PotterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): PotterDatabase {
        return Room.databaseBuilder(context, PotterDatabase::class.java, DB_NAME).build()
    }
}