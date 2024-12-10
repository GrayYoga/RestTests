package utils

import dto.Todo

object TestData {
    private var seriesId: ULong = 0u

    fun initId(id: ULong) {
        if (id >= seriesId) { seriesId = id + 1u }
    }

    fun nextId(): ULong {
        return seriesId++
    }

    fun todo(): Todo {
        val id = nextId()
        return Todo(
            id = id,
            text = "Todo $id",
            completed = false
        )
    }
}
