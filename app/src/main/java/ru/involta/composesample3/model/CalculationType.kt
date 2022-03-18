package ru.involta.composesample3.model

enum class CalculationType(
    val title: String,
    val part1: String,
    val part2: String,
    val part3: String
) {
    SUM(
        "Сумма чисел",
        "result = 0\n",
        "\tif (тут вставить условие):\n" +
                "\t\tresult = result + new_number\n",
        "print(result)\n"
    ),
    PRODUCT(
        "Произведение чисел",
        "result = 1\n",
        "\tif (тут вставить условие):\n" +
                "\t\tresult = result * new_number\n",
        "print(result)\n"
    ),
    COUNT(
        "Количество чисел",
        "result = 0\n",
        "\tif (тут вставить условие):\n" +
                "\t\tresult = result + 1\n",
        "print(result)\n"
    ),
    MAX(
        "Максимальное из чисел",

        "result = -99999999\n",
        "\tif (тут вставить условие):\n" +
                "\t\tif new_number > result:\n" +
                "\t\t\tresult = new_number\n",
        "print(result)\n"
    ),
    MIN(
        "Минимальное из чисел",

        "result = 99999999\n",
        "if (тут вставить условие):\n" +
                "\t\tif new_number < result:\n" +
                "\t\t\tresult = new_number\n",
        "print(result)\n"
    );


    companion object {
        fun get(title: String) = CalculationType.values().find { it.title == title }?: SUM
        val titles get() = CalculationType.values().map { it.title }
    }
}