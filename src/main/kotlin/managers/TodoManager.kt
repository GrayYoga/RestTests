package managers

import api.ApiClient
import api.Endpoints
import dto.Todo
import khttp.responses.Response
import khttp.structures.authorization.Authorization
import khttp.structures.authorization.BasicAuthorization
import utils.TestData
import utils.checkSuccess
import utils.listOfEntity

class TodoManager {
    init {
        getTodos(limit = 10000)
            .checkSuccess()
            .listOfEntity(Todo::class.java)
            .map { it.id!! }
            .maxOrNull()
            ?.let { TestData.initId(it) }
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

    fun deleteTodo(id: ULong, auth: Authorization = BasicAuthorization("admin", "admin")): Response {
        return ApiClient.delete(Endpoints.todos(id), auth = auth)
    }

    fun updateTodo(id: ULong, todo: Todo): Response {
        return ApiClient.put(Endpoints.todos(id), todo)
    }
}
