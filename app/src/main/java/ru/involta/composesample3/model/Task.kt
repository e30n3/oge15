package ru.involta.composesample3.model

data class Task(
    val inputType: InputType = InputType.get(InputType.titles.first()),
    val calculationType: CalculationType = CalculationType.get(CalculationType.titles.first()),
) {
    fun generateCode(): String {
        var code = ""
        when (inputType) {
            InputType.FIRST_NUMBER_IS_COUNT -> {
                code = inputType.part1 +
                        calculationType.part1 +
                        inputType.part2 +
                        calculationType.part2 +
                        calculationType.part3
            }
            InputType.UP_TO_ZERO -> {
                code = inputType.part1 +
                        calculationType.part1 +
                        inputType.part2 +
                        calculationType.part2 +
                        inputType.part3 +
                        calculationType.part3
            }
        }
        return code
    }
}

fun main() {
    print(
        Task(
            InputType.FIRST_NUMBER_IS_COUNT,
            CalculationType.MAX
        ).generateCode()
    )
}