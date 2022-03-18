package ru.involta.composesample3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


enum class ConditionType(
    val title: String,
    val condition: String,
    val needEditText: Boolean = true
)  {
    ALIQUOT("Кратность", "(new_number %% %d == 0)"),
    NOT_ALIQUOT("Некратность", "(new_number %% %d != 0)"),
    LAST_DIGIT("Последняя цифра числа", "(new_number %% 10 == %d)"),
    LAST_TWO_DIGIT("Две последних цифры числа", "(new_number %% 100 == %d)"),
    DIGITS_NUMBER("Количество знаков в числе", "(len(str(new_number)) == %d)"),
    EVEN("Четное", "(new_number %% 2 == 0)", false),
    ODD("Нечетное", "(new_number %% 2 == 1)", false);

    companion object {
        fun get(title: String) = values().find { it.title == title } ?: ALIQUOT
        val titles get() = values().map { it.title }
    }
}