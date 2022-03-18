package ru.involta.composesample3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class LogicOperationType(val title: String, val sign: String) {
    DISABLE("Нет", ""),
    AND("И", "and"),
    OR("Или", "or");

    companion object {
        fun get(title: String) = values().find { it.title == title } ?: DISABLE
        val titles get() = values().map { it.title }
    }
}