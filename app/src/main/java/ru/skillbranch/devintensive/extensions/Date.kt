package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

val Int.sec get() = this * SECOND
val Int.min get() = this * MINUTE
val Int.hour get() = this * HOUR
val Int.day get() = this * DAY

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = date.time - time
    return if (diff >= 0) {
        when (diff) {
            in 0.sec..1.sec -> "только что"
            in 1.sec..45.sec -> "несколько секунд назад"
            in 45.sec..75.sec -> "минуту назад"
            in 75.sec..45.min -> "${TimeUnits.MINUTE.plural(diff / MINUTE)} назад"
            in 45.min..75.min -> "час назад"
            in 75.min..22.hour -> "${TimeUnits.HOUR.plural(diff / HOUR)} назад"
            in 22.hour..26.hour -> "день назад"
            in 26.hour..360.day -> "${TimeUnits.DAY.plural(diff / DAY)} назад"
            else -> "более года назад"
        }
    } else {
        when (diff) {
            in (-1).sec..0.sec -> "прямо сейчас"
            in (-45).sec..(-1).sec -> "через несколько секунд назад"
            in (-75).sec..(-45).sec -> "через минуту"
            in (-45).min..(-75).sec -> "через ${TimeUnits.MINUTE.plural(Math.abs(diff / MINUTE))}"
            in (-75).min..(-45).min -> "через час"
            in (-22).hour..(-75).min -> "через ${TimeUnits.HOUR.plural(Math.abs(diff / HOUR))}"
            in (-26).hour..(-22).hour -> "через день"
            in (-360).day..(-26).hour -> "через ${TimeUnits.DAY.plural(Math.abs(diff / DAY))}"
            else -> "более чем через год"
        }
    }

}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Long) = "$value ${pluralStrings[value.asPlurals]}"
}

enum class Plurals {
    ONE,
    FEW,
    MANY
}

val Long.asPlurals
    get() = when {
        this % 100L in 5L..20L -> Plurals.MANY
        this % 10L == 1L -> Plurals.ONE
        this % 10L in 2L..4L -> Plurals.FEW
        else -> Plurals.MANY
    }


val TimeUnits.pluralStrings
    get() = when (this) {
        TimeUnits.SECOND -> mapOf(Plurals.ONE to "секунду", Plurals.FEW to "секунды", Plurals.MANY to "секунд")
        TimeUnits.MINUTE -> mapOf(Plurals.ONE to "минуту", Plurals.FEW to "минуты", Plurals.MANY to "минут")
        TimeUnits.HOUR -> mapOf(Plurals.ONE to "час", Plurals.FEW to "часа", Plurals.MANY to "часов")
        TimeUnits.DAY -> mapOf(Plurals.ONE to "день", Plurals.FEW to "дня", Plurals.MANY to "дней")
    }