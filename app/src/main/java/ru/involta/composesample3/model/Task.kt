package ru.involta.composesample3.model

data class Task(
    val inputType: InputType = InputType.get(InputType.titles.first()),
    val calculationType: CalculationType = CalculationType.get(CalculationType.titles.first()),
    val conditionType: ConditionType = ConditionType.get(ConditionType.titles.first()),
    val conditionValue: Int = 3
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
        code = code.replace("(тут вставить условие)", conditionType.condition.format(conditionValue))
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