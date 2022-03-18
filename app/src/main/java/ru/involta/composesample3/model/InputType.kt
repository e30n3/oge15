package ru.involta.composesample3.model

import java.lang.reflect.Type

enum class InputType(
    val title: String,
    val part1: String,
    val part2: String,
    val part3: String = ""
) {
    FIRST_NUMBER_IS_COUNT(
        "Ввод количества чисел",
        "numbers_count = int(input())\n",
        "for i in range(numbers_count):\n" +
                "\tnew_number = int(input())\n"
    ),
    UP_TO_ZERO(
        "Ввод до нуля",
        "new_number = int(input())\n",
        "while new_number != 0:\n",
        "\tnew_number = int(input())\n"
    );

    companion object {
        fun get(title: String) = InputType.values().find { it.title == title } ?: FIRST_NUMBER_IS_COUNT
        val titles get() = InputType.values().map { it.title }
    }
}

