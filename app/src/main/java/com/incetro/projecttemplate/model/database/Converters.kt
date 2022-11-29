/*
 * Ruvpro
 *
 * Created by artembirmin on 21/6/2022.
 */

package com.incetro.projecttemplate.model.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

object Converters {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun toDateTime(value: Long?) = value?.let { DateTime(it, DateTimeZone.UTC) }

    @TypeConverter
    @JvmStatic
    fun fromDateTime(value: DateTime?) = value?.millis
}