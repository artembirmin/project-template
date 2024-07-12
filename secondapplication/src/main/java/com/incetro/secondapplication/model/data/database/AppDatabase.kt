/*
 * ProjectTemplate
 *
 * Created by artembirmin on 15/11/2023.
 */

package com.incetro.secondapplication.model.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.incetro.secondapplication.BuildConfig
import com.incetro.secondapplication.model.data.database.demo.DemoDao
import com.incetro.secondapplication.model.data.database.demo.DemoDto

@Database(
    entities = [
        DemoDto::class,
    ],
    version = AppDatabase.VERSION
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = BuildConfig.DB_NAME
        const val VERSION = 1
    }

    abstract fun demoDao(): DemoDao
}