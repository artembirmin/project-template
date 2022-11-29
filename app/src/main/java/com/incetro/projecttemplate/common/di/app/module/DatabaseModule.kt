/*
 * Ruvpro
 *
 * Created by artembirmin on 21/6/2022.
 */

package com.incetro.projecttemplate.common.di.app.module

import android.content.Context
import androidx.room.Room
import com.incetro.projecttemplate.model.database.AppDatabase
import com.incetro.projecttemplate.model.database.demo.DemoDao
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DB_NAME
            )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Reusable
    fun provideDemoDao(database: AppDatabase): DemoDao = database.demoDao()
}