/*
 * Ruvpro
 *
 * Created by artembirmin on 21/6/2022.
 */

package com.incetro.projecttemplate.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.incetro.projecttemplate.BuildConfig
import com.incetro.projecttemplate.model.database.demo.DemoDao
import com.incetro.projecttemplate.model.database.demo.DemoDto

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
        const val VERSION = 31
    }

    abstract fun demoDao(): DemoDao
}