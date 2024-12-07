package utils

import dto.Todo
import kotlin.random.Random
import kotlin.random.nextULong

object TestData {

    fun todo(): Todo {
        val id = Random.nextULong()
        return Todo(
            id = id,
            text = "Todo $id",
            completed = false
        )
    }
}
