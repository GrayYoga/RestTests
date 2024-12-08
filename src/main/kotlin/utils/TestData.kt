package utils

import dto.Todo
import kotlin.random.Random

object TestData {

    fun todo(): Todo {
        val id = Random.nextInt().toULong()
        return Todo(
            id = id,
            text = "Todo $id",
            completed = false
        )
    }
}
