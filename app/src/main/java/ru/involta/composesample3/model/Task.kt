package ru.involta.composesample3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val inputType: InputType = InputType.get(InputType.titles.first()),
    val calculationType: CalculationType = CalculationType.get(CalculationType.titles.first()),
    val conditionType: ConditionType = ConditionType.get(ConditionType.titles.first()),
    val conditionValue: Int = 3,
    val conditionOperationType: LogicOperationType = LogicOperationType.get(LogicOperationType.titles.first()),
    val conditionType2: ConditionType = ConditionType.get(ConditionType.titles.first()),
    val conditionValue2: Int = 3,
) : Parcelable {
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

        val summaryCondition = if (conditionOperationType == LogicOperationType.DISABLE)
            conditionType.condition.format(conditionValue)
        else
            conditionType.condition.format(conditionValue) + " " +
                    conditionOperationType.sign + " " +
                    conditionType2.condition.format(conditionValue2)


        code =
            code.replace("(тут вставить условие)", summaryCondition)
        return code
    }

    companion object {


        fun pack(task: Task): String = task.run {
            inputType.title + "#" +
                    calculationType.title + "#" +
                    conditionType.title + "#" +
                    conditionValue.toString() + "#" +
                    conditionOperationType.title + "#" +
                    conditionType2 + "#" +
                    conditionValue2
        }


        fun unpack(packed: String): Task {
            var i = 0
            val data = packed.split("#")
            return Task(
                inputType = InputType.get(data[i++]),
                calculationType = CalculationType.get(data[i++]),
                conditionType = ConditionType.get(data[i++]),
                conditionValue = data[i++].toInt(),
                conditionOperationType = LogicOperationType.get(data[i++]),
                conditionType2 = ConditionType.get(data[i++]),
                conditionValue2 = data[i].toInt()
            )
        }
    }
}