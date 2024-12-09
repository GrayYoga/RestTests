package managers

import api.ApiClient
import api.Endpoints
import dto.Todo
import khttp.responses.Response
import utils.TestData
import utils.checkSuccess
import utils.listOfEntity

class TodoManager {
    init {
        TestData.initId(
            getTodos()
                .checkSuccess()
                .listOfEntity(Todo::class.java)
                .lastOrNull()
                ?.id ?: 1u
        )
    }

    fun createTodo(todo: Todo): Response {
        return ApiClient.post(Endpoints.todos(), todo)
    }

    fun getTodos(offset: Any? = 0, limit: Any? = 100): Response {
        val params = mapOf(
            "offset" to "$offset",
            "limit" to "$limit"
        )
        return ApiClient.get(Endpoints.todos(), params)
    }

    fun deleteTodo(id: ULong): Response {
        return ApiClient.delete(Endpoints.todos(id))
    }

    fun updateTodo(id: ULong, todo: Todo): Response {
        return ApiClient.put(Endpoints.todos(id), todo)
    }
}
