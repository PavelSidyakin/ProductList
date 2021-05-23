package com.productlist.app_database.impl.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.productlist.app_database.impl.room.AppDatabase
import com.productlist.app_database.impl.room.ProductDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class AppDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    fun provideRoomDatabase(appDatabase: AppDatabase): RoomDatabase {
        return appDatabase
    }

    @Provides
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }

    companion object {
        private const val DATABASE_NAME = "productlist_db"
    }
}