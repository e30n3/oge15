package ru.involta.composesample3.model

enum class ConditionType(
    val title: String,
    val condition: String,
    val needEditText: Boolean = true
) {
    ALIQUOT("Кратность", "(new_number %% %d == 0)"),
    LAST_DIGIT("Последняя цифра числа", "(new_number %% 10 == %d)"),
    LAST_TWO_DIGIT("Две последних цифры числа", "(new_number %% 100 == %d)"),
    EVEN("Четное", "(new_number %% 2 == 0)", false),
    ODD("Нечетное", "(new_number %% 2 == 1)", false);

    companion object {
        fun get(title: String) = ConditionType.values().find { it.title == title } ?: ALIQUOT
        val titles get() = ConditionType.values().map { it.title }
    }
}