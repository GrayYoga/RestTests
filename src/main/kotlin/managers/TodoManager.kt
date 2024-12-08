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
                .last()
                .id!!
        )
    }

    fun createTodo(todo: Todo): Response {
        return ApiClient.post(Endpoints.todos(), todo)
    }

    fun getTodos(offset: Int? = 0, limit: Int? = 100): Response {
        return ApiClient.get(Endpoints.todos())
    }

    fun deleteTodo(id: ULong): Response {
        return ApiClient.delete(Endpoints.todos(id))
    }

    fun updateTodo(id: ULong, todo: Todo): Response {
        return ApiClient.put(Endpoints.todos(id), todo)
    }
}
