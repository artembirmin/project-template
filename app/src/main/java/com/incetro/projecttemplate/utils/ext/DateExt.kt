/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.utils.ext

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.concurrent.TimeUnit

const val TIME_PATTERN = "HH:mm:ss"
const val HOURS_MINUTES_PATTERN = "HH:mm"
const val HOURS_PATTERN = "H"
const val HOURS_MINUTES_PM_AM_PATTERN = "HH:mm a"
const val DATE_PATTERN = "dd.MM.YYYY"
const val DATE_READABLE_PATTERN = "d MMM YYYY"
const val DATE_TIME_PATTERN = "HH:mm E. d MMMM YYYY"
const val DATE_TIME_LOG_PATTERN = "E MMM dd yyyy 'at' hh:mm:ss:SSS aaa"

const val secondsInMilli: Long = 1000
const val minutesInMilli = secondsInMilli * 60
const val hoursInMilli = minutesInMilli * 60
const val daysInMilli = hoursInMilli * 24

fun Long.toDateTime(): DateTime = DateTime(this, DateTimeZone.UTC)
fun Long.fromServerTimestamp(): DateTime = DateTime(this * 1000, DateTimeZone.UTC)
fun Double.fromServerTimestamp(): DateTime = DateTime((this * 1000).toLong(), DateTimeZone.UTC)
fun Long.toServerTimestamp() = this.div(1000)
fun DateTime.toServerTimestamp(): Long = this.millis.toServerTimestamp()

fun Long.toServerAccurateTimestamp(): Double {
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this)
    val milliseconds = this % 1000
    return "${seconds}.${milliseconds}".toDouble()
}

fun DateTime.toReadableDate(pattern: String): String = toString(pattern)
fun DateTime.toReadableTime(): String = toString(HOURS_MINUTES_PATTERN)
fun DateTime.toReadableHoursTime(): String = toString(HOURS_PATTERN)
fun DateTime.toReadableDate(): String = toString(DATE_READABLE_PATTERN)
fun DateTime.toRegularDate(): String = toString(DATE_PATTERN)
fun DateTime.toReadableDateTime(): String = toString(DATE_TIME_PATTERN)
fun DateTime.toReadableLogDateTime(): String = toString(DATE_TIME_LOG_PATTERN)